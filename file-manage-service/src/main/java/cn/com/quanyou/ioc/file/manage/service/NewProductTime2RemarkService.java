package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.common.component.util.DateTimeUtils;
import cn.com.quanyou.ioc.common.component.util.StringUtils;
import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.DateUtil;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.INewProductTime2RemarkDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ImportNewProductTime2RemarkTi;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bxq
 * @desc
 * @date 2020/1/2 15:36
 */
@Service
@Slf4j
public class NewProductTime2RemarkService extends ExcelImportService implements IExcelImportService<ImportNewProductTime2RemarkTi> {

    @Autowired
    private INewProductTime2RemarkDao importDao;
    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<ImportNewProductTime2RemarkTi> excelUtils = new ExcelUtilsMap<>(ImportNewProductTime2RemarkTi.class, this);
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
        BeanUtils<ImportNewProductTime2RemarkTi> beanUtils = new BeanUtils<>(ImportNewProductTime2RemarkTi.class);
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
    public ResultInfo<PageInfo<ImportNewProductTime2RemarkTi>, String> search(SearchParamVo searchParamVo, PageInfo<ImportNewProductTime2RemarkTi> pageInfo) {
        return null;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<ImportNewProductTime2RemarkTi> list = entityList;

        for (ImportNewProductTime2RemarkTi entity : list) {
            //校验上新时间和备注[可两个同时为空。但如果其中一个填值另一个就必填。]
            if (StringUtils.isBlank(entity.getAddTime()) && StringUtils.isNotBlank(entity.getAddRemark())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("上新时间");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("上新备注不为空时，上新时间不能为空！");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }
            if (StringUtils.isNotBlank(entity.getAddTime()) && StringUtils.isBlank(entity.getAddRemark())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("上新备注");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("上新时间不为空时，上新备注不能为空！");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            // 校验日期格式
            if(StringUtils.isNotBlank(entity.getAddTime()) && !DateTimeUtils.checkDate(entity.getAddTime())){
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("上新时间");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("上新时间[" + entity.getAddTime() + "]格式不正确！[eg:20200324]");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            // 校验：SPU编码：必填，校验系统中是否存在，不存在则导入不成功
            int existCount = importDao.existCheck(entity.getSpuCode());
            if(existCount == 0){
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("SPU编码");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("不存在的SPU编码！");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }
        }

        return errorList;
    }

}
