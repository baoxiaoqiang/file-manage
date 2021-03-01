package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.ICdsWardrobeTiDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.CdsWardrobeComponentTi;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author bxq
 * @desc 定制橱柜组件service
 * @date 2020/4/21 10:19
 */
@Service
@Slf4j
public class CdsWardrobeService extends ExcelImportService implements IExcelImportService<CdsWardrobeComponentTi> {
    @Autowired
    private ICdsWardrobeTiDao iCdsWardrobeTiDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<CdsWardrobeComponentTi> excelUtils = new ExcelUtilsMap<>(CdsWardrobeComponentTi.class, this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(), fileInfo.getRemoteFileName()), excelModel, task);
    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        iCdsWardrobeTiDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
//查询对应taskId 批次导入后校验成功的数据
        List<Map<String, Object>> dataList = iCdsWardrobeTiDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<CdsWardrobeComponentTi> beanUtils = new BeanUtils<>(CdsWardrobeComponentTi.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId, beanUtils, dataList, validList, unvalidList, loginUser);

        if (!validList.isEmpty()) {
            //更新验证结果
            iCdsWardrobeTiDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if (!unvalidList.isEmpty()) {
            //更新验证结果
            iCdsWardrobeTiDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {
        ResultInfo<String, String> resultInfo = super.checkAnalysisStatus(taskId);
        if (resultInfo.isFailed()) {
            return resultInfo;
        }
        int count = iCdsWardrobeTiDao.queryMistakeAndUncheckedCount(taskId);
        if (count > 0) {
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task=" + taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
        /** 执行存储过程更新业务表数据*/
        iCdsWardrobeTiDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<CdsWardrobeComponentTi>, String> search(SearchParamVo searchParamVo, PageInfo<CdsWardrobeComponentTi> pageInfo) {
        List<CdsWardrobeComponentTi> dataList = iCdsWardrobeTiDao.search(searchParamVo, pageInfo.getStartRow(), pageInfo.getEndRow());
        pageInfo.setDataList(dataList);
        pageInfo.setTotal(iCdsWardrobeTiDao.querySearchTotal(searchParamVo, pageInfo.getStartRow(), pageInfo.getEndRow()));

        ResultInfo<PageInfo<CdsWardrobeComponentTi>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        return Collections.emptyList();
    }
}
