package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.common.component.util.DateTimeUtils;
import cn.com.quanyou.ioc.common.component.util.StringUtils;
import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.INewProductBasicDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ImportNewProductBasicTi;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PutAwayTiForShootBatch;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bxq
 * @desc
 * @date 2020/1/2 15:36
 */
@Service
@Slf4j
public class NewProductBasicService extends ExcelImportService implements IExcelImportService<ImportNewProductBasicTi> {

    @Autowired
    private INewProductBasicDao importDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<ImportNewProductBasicTi> excelUtils = new ExcelUtilsMap<>(ImportNewProductBasicTi.class, this);
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
        BeanUtils<ImportNewProductBasicTi> beanUtils = new BeanUtils<>(ImportNewProductBasicTi.class);
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
    public ResultInfo<PageInfo<ImportNewProductBasicTi>, String> search(SearchParamVo searchParamVo, PageInfo<ImportNewProductBasicTi> pageInfo) {
        return null;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<ImportNewProductBasicTi> list = entityList;
        for (ImportNewProductBasicTi entity : list) {

            if (entity.getProductBatchNo() == null || entity.getSkuCode() == null
                    || entity.getSkuName() == null || entity.getSpuFlag() == null
                    || entity.getSeries() == null) {
                continue;
            }

            //校验新品批次
            if (!StringUtils.isEmpty(entity.getProductBatchNo()) && !DateTimeUtils.checkDate(entity.getProductBatchNo())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("新品批次");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("新品批次（" + entity.getProductBatchNo() + "）格式不正确[eg:20190808]");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

//            long flagCount = list.stream().filter(e -> entity.getSpuFlag().equals(e.getSpuFlag())).count();
            long seriesCount = list.stream().filter(e -> entity.getSpuFlag().equals(e.getSpuFlag()))
                    .map(ImportNewProductBasicTi::getSeries).distinct()
                    .collect(Collectors.toList()).size();
            if (seriesCount>1){
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("系列");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("同一个spu标识的系列必须一样");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }
        }
        return errorList;
    }
}
