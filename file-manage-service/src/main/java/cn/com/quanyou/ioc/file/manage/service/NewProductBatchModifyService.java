package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.common.component.util.DateTimeUtils;
import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.INewProductBatchModifyDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.*;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PutAwayTiForTaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bxq
 * @desc
 * @date 2019/12/18 13:38
 */
@Service
@Slf4j
public class NewProductBatchModifyService extends ExcelImportService implements IExcelImportService<PutAwayTiForTaskStatus> {

    @Autowired
    private INewProductBatchModifyDao importDao;
    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<PutAwayTiForTaskStatus> excelUtils = new ExcelUtilsMap<>(PutAwayTiForTaskStatus.class, this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(), fileInfo.getRemoteFileName()), excelModel, task);
    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        importDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
        List<Map<String, Object>> dataList = importDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<PutAwayTiForTaskStatus> beanUtils = new BeanUtils<>(PutAwayTiForTaskStatus.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId, beanUtils, dataList, validList, unvalidList, loginUser);

        if (!validList.isEmpty()) {
            //更新验证结果
            importDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if (!unvalidList.isEmpty()) {
            importDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {
        ResultInfo<String, String> resultInfo = super.checkAnalysisStatus(taskId);
        if (resultInfo.isFailed()) {
            return resultInfo;
        }
        int count = importDao.queryMistakeAndUncheckedCount(taskId);
        if (count > 0) {
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task=" + taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }

        importDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);
        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<PutAwayTiForTaskStatus>, String> search(SearchParamVo searchParamVo, PageInfo<PutAwayTiForTaskStatus> pageInfo) {
        return null;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<PutAwayTiForTaskStatus> list = entityList;

        List<NewNodeStatusVo> newNodeStatusVos = importDao.queryNodeStatus(list.get(0).getFactoryCode());
        for (PutAwayTiForTaskStatus entity : list) {

            if (entity.getSpuCode() == null || entity.getPhotoBatchDateCode() == null) {
                continue;
            }
            //当节点为拍摄时间时，对拍摄时间进行非空校验
            if ("PLAN_PHOTO".equals(list.get(0).getTaskCode())){
                if (StringUtils.isBlank(entity.getProductShootDate())){
                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("拍摄计划时间");
                    errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                    errorInfo.setErrorInfo("拍摄计划时间不能为空！");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }
            }

            // 1校验拍摄批次

            if (!DateTimeUtils.checkDate(entity.getPhotoBatchDateCode())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("拍摄批次");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("拍摄批次（" + entity.getPhotoBatchDateCode() + "）格式不正确[eg:20190808]");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            //2校验拍摄计划时间
            if(!StringUtils.isEmpty(entity.getProductShootDate())&&!DateTimeUtils.checkDate(entity.getProductShootDate())){
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("拍摄计划时间");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("拍摄计划时间（" + entity.getProductShootDate() + "）格式不正确[eg:20190808]");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            //校验流程节点对应的状态是否在进行中或者异常状态

            List<NewNodeStatusVo> list1 = newNodeStatusVos.stream().filter(e -> e.getSpuCode().equals(entity.getSpuCode())
                    && e.getPhotoBatchCode().equals(entity.getPhotoBatchDateCode())).collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(list1)){
                Integer nodeStatus = list1.stream().max(Comparator.comparing(NewNodeStatusVo::getTaskStatus)).get().getTaskStatus();
                if(!(nodeStatus.equals(2)||nodeStatus.equals(4))){//2.进行中，4异常
                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("流程节点状态不正确");
                    errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                    errorInfo.setErrorInfo("【" + entity.getSpuCode() +"】【"
                            + entity.getPhotoBatchDateCode()+"】不在该流程节点的进行中或异常状态");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }
            }else{
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("流程节点不正确");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("【" + entity.getSpuCode() +"】【"
                        + entity.getPhotoBatchDateCode()+"】不在该流程节点");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }
        }

        return errorList;
    }
}
