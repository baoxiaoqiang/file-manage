package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.IB2CProductDimTiDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.B2CProductDimTi;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.IocemallProductDim;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bxq
 * @desc
 * @date 2019/11/27 8:58
 */
@Slf4j
@Service
public class B2CProductDimService extends ExcelImportService implements IExcelImportService<B2CProductDimTi> {

    @Autowired
    private IB2CProductDimTiDao ib2CProductDimTiDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<B2CProductDimTi> excelUtils = new ExcelUtilsMap<>(B2CProductDimTi.class, this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(), fileInfo.getRemoteFileName()), excelModel, task);

    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        ib2CProductDimTiDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
        List<Map<String, Object>> dataList = ib2CProductDimTiDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<B2CProductDimTi> beanUtils = new BeanUtils<>(B2CProductDimTi.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId, beanUtils, dataList, validList, unvalidList, loginUser);

        if (!validList.isEmpty()) {
            //更新验证结果
            ib2CProductDimTiDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if (!unvalidList.isEmpty()) {
            //更新验证结果
            ib2CProductDimTiDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {

        ResultInfo<String, String> resultInfo = super.checkAnalysisStatus(taskId);
        if (resultInfo.isFailed()) {
            return resultInfo;
        }
        int count = ib2CProductDimTiDao.queryMistakeAndUncheckedCount(taskId);
        if (count > 0) {
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task=" + taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
//        importDao.deleteByTaskId(taskId);
        ib2CProductDimTiDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<B2CProductDimTi>, String> search(SearchParamVo searchParamVo, PageInfo<B2CProductDimTi> pageInfo) {
        return null;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();
        List<B2CProductDimTi> list = entityList;

        List<IocemallProductDim> productDimList = ib2CProductDimTiDao.getProductDimList();

        List<IocemallProductDim> styleDimList = ib2CProductDimTiDao.getStyleDimList();
        List<IocemallProductDim> spaceDimList = ib2CProductDimTiDao.getSpaceDimList();
        List<IocemallProductDim> materialDimList = ib2CProductDimTiDao.getMaterialDimList();
        List<IocemallProductDim> ageDimList = ib2CProductDimTiDao.getAgeDimList();
        List<IocemallProductDim> functionDimList = ib2CProductDimTiDao.getFunctionDimList();
        for (B2CProductDimTi entity : list) {
            if (StringUtils.isBlank(entity.getProductCode()) || StringUtils.isBlank(entity.getProductName())) {
                continue;
            }
            Integer con = productDimList.stream().filter(e -> entity.getProductCode().equals(e.getProductCode()) &&
                    entity.getProductName().equals(e.getProductName())).collect(Collectors.toList()).size();
            //1.套件编码+套件名称是否存在校验
            if (con.equals(0)) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getProductCode() + "】" + "【" + entity.getProductName() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("套件编码-套件名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
            //3.风格是否存在校验
            Integer styleCon = styleDimList.stream().filter(e -> e.getName().equals(entity.getStyle()))
                    .collect(Collectors.toList()).size();
            if (styleCon.equals(0)&&StringUtils.isNotEmpty(entity.getStyle())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getStyle() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("风格名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
            //4.空间是否存在校验
            Integer spaceCon = spaceDimList.stream().filter(e -> e.getName().equals(entity.getSpace()))
                    .collect(Collectors.toList()).size();
            if (spaceCon.equals(0)&&StringUtils.isNotEmpty(entity.getSpace())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getSpace() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("空间名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
            //5.材质是否存在校验
            Integer materialCon = materialDimList.stream().filter(e -> e.getName().equals(entity.getMaterial()))
                    .collect(Collectors.toList()).size();
            if (materialCon.equals(0)&&StringUtils.isNotEmpty(entity.getMaterial())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getMaterial() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("材质名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
            //6.年龄是否存在校验
            Integer ageCon = ageDimList.stream().filter(e -> e.getName().equals(entity.getAge()))
                    .collect(Collectors.toList()).size();
            if (ageCon.equals(0)&&StringUtils.isNotEmpty(entity.getAge())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getAge() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("年龄名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
            //7.品类细分是否存在校验
            Integer functionCon = functionDimList.stream().filter(e -> e.getName().equals(entity.getCategoryDetail()))
                    .collect(Collectors.toList()).size();
            if (functionCon.equals(0)&&StringUtils.isNotEmpty(entity.getCategoryDetail())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getCategoryDetail() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("品类细分名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
        }
        return errorList;
    }
}
