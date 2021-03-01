package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.common.utils.StringUtil;
import cn.com.quanyou.ioc.file.manage.dao.IGovStoreSaleImportDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.GovStoreSaleTi;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class GovStoreSaleService extends ExcelImportService implements IExcelImportService<GovStoreSaleTi> {

    @Autowired
    private IGovStoreSaleImportDao importDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {

        UploadFileInfoBean fileInfo = task.getFileInfo();
        if(fileInfo == null || excelModel == null){
            return ;
        }
        ExcelUtilsMap<GovStoreSaleTi> excelUtils = new ExcelUtilsMap<>(GovStoreSaleTi.class,this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(),fileInfo.getRemoteFileName()),excelModel,task);
    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {

        if( dataList == null || dataList.isEmpty()){
            return;
        }
        importDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
        List<Map<String,Object>> dataList =  importDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<GovStoreSaleTi> beanUtils = new BeanUtils<>( GovStoreSaleTi.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId,beanUtils,dataList,validList,unvalidList,loginUser);

        if(!validList.isEmpty()){
            //更新验证结果
            importDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if(!unvalidList.isEmpty()){
            //更新验证结果
            importDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {
        ResultInfo<String,String> resultInfo = super.checkAnalysisStatus(taskId);
        if(resultInfo .isFailed()){
            return resultInfo;
        }
        int count = importDao.queryMistakeAndUncheckedCount(taskId);
        if(count>0){
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task="+taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
        importDao.deleteByTaskId(taskId);
        importDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<GovStoreSaleTi>, String> search(SearchParamVo searchParamVo, PageInfo<GovStoreSaleTi> pageInfo) {
        List<GovStoreSaleTi> dataList = importDao.search(searchParamVo,pageInfo.getStartRow(),pageInfo.getEndRow());
        pageInfo.setDataList(dataList);
        pageInfo.setTotal(importDao.querySearchTotal(searchParamVo,pageInfo.getStartRow(),pageInfo.getEndRow()));

        ResultInfo<PageInfo<GovStoreSaleTi>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<Map<String,String>> shopNames = dataValidateService.queryShopNames();

        List<Map<String,String>> provinces = dataValidateService.queryProvinces();

        List<GovStoreSaleTi> list = entityList;
        for(GovStoreSaleTi entity : list){

            ResultInfo<AnalysisTaskErrorBean,String > shopCheckResult = super.checkShop(entity.getShopName(),entity.getTaskId(),entity.getRowNumber(),shopNames);
            if(shopCheckResult.isFailed()){
                errorList.add(shopCheckResult.getData());
                unvalidList.add(entity.getDataId());
            }

            ResultInfo<AnalysisTaskErrorBean,String > provinceCheckResult = super.checkProvince(entity.getProvince(),entity.getTaskId(),entity.getRowNumber(),provinces);
            if(provinceCheckResult.isFailed()){
                errorList.add(provinceCheckResult.getData());
                unvalidList.add(entity.getDataId());
            }

            ResultInfo<AnalysisTaskErrorBean,String > dateCheckResult = super.checkReportDate(entity.getReportDate(),entity.getSaleDate(),entity.getTaskId(),entity.getRowNumber(),"日期");
            if(dateCheckResult.isFailed()){
                errorList.add(dateCheckResult.getData());
                unvalidList.add(entity.getDataId());
            }
            //正整数非空验证
            if (!isPureDigital(entity.getBuyCount())){
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("输入的【购买数量】不正确");
                error.setRow(entity.getRowNumber());
                error.setCellName("购买数量");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
        }


        return errorList;
    }

    public static boolean isPureDigital(String str) {
        if (str == null || "".equals(str)){
            return false;
        }
        Pattern p;
        Matcher m;
        p = Pattern.compile("[0-9]*");
        m = p.matcher(str);
        if (m.matches()){
            return true;
        }else{
            return false;
        }
    }

}
