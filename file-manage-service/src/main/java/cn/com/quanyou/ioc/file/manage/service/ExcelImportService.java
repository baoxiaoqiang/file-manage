package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.ConstantsFileManage;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ProcessResultEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ProcessStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.DateUtil;
import cn.com.quanyou.ioc.file.manage.dao.IAnalysisTaskDao;
import cn.com.quanyou.ioc.file.manage.facade.IAnalysisTaskService;
import cn.com.quanyou.ioc.file.manage.facade.IRelationDataValidateService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ShopBasicInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelImportService
 * @date 2019/6/16 19:37
 * @projectName file-manage
 * @description:
 */
@Service
@Slf4j
public abstract class  ExcelImportService<T>  {


    @Autowired
    protected IRelationDataValidateService dataValidateService;

    @Autowired
    protected IAnalysisTaskService analysisTaskService;

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private IAnalysisTaskDao analysisTaskDao;

    /***
    * @Description 检查是否可以开始同步
    * @Author heshiyi@quanyou.com.cn
    * @Date 11:35 2019/6/22
    * @param taskId
    * @return
    **/
    protected ResultInfo<String,String> checkAnalysisStatus(String taskId){
        ResultInfo<String,String> resultInfo = new ResultInfo<>();

        AnalysisTaskBean taskBean = analysisTaskService.queryByDataId(taskId);
        if(taskBean == null){
            log.info("解析任务没有找到，不能同步：task="+taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("解析任务没有找到");
            return resultInfo;
        }
        if(!ProcessStatusEnum.analysisFinished.name().equals(taskBean.getProcessStatus())){
            log.info("解析状态不是完成状态，不能同步：task="+taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("解析状态不是完成状态");
            return resultInfo;
        }
        if(!ProcessResultEnum.success.name().equals(taskBean.getProcessResult())){
            log.info("解析结果不是解析成功，不能同步：task="+taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("解析结果不是解析成功");
            return resultInfo;
        }
        return resultInfo;
    }


    /**
    * @Description 检测导入的日期是否和excel里面的日期一致
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:00 2019/6/19
    * @param reportDate
    * @param excelDate
    * @param taskId
    * @param rowNum
    * @param headerName
    * @return
    **/
    protected ResultInfo<AnalysisTaskErrorBean,String > checkReportDate(Date reportDate,Date excelDate,String taskId,Integer rowNum,String headerName){
        ResultInfo<AnalysisTaskErrorBean,String > resultInfo = new ResultInfo<>();

        if(excelDate == null){
            return resultInfo;
        }

        if(!DateUtil.isSameDayAndNotEmpty(reportDate,excelDate)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
            error.setErrorInfo(headerName+"和导入的时候选择的日期不一致");
            error.setRow(rowNum);
            error.setCellName(headerName);
            error.setTaskId(taskId);
            resultInfo.setData(error);
        }
        return resultInfo;
    }

    /**
     * @Description 检测导入的日期是否和excel里面的日期同月
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:00 2019/6/19
     * @param reportDate
     * @param excelDate
     * @param taskId
     * @param rowNum
     * @param headerName
     * @return
     **/
    protected ResultInfo<AnalysisTaskErrorBean,String > checkReportDate4operationData(Date reportDate,Date excelDate,String taskId,Integer rowNum,String headerName){
        ResultInfo<AnalysisTaskErrorBean,String > resultInfo = new ResultInfo<>();

        if(excelDate == null){
            return resultInfo;
        }

        Calendar ca = Calendar.getInstance();
        ca.setTime(reportDate);
        String reportDateStr = ca.get(Calendar.YEAR) + "" + (ca.get(Calendar.MONTH) + 1);

        Calendar cal = Calendar.getInstance();
        cal.setTime(excelDate);
        String excelDateStr = cal.get(Calendar.YEAR) + "" +  (cal.get(Calendar.MONTH) + 1) + "";

        if(!reportDateStr.equals(excelDateStr)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
            error.setErrorInfo(headerName+"和导入的时候选择的日期不一致");
            error.setRow(rowNum);
            error.setCellName(headerName);
            error.setTaskId(taskId);
            resultInfo.setData(error);
        }
        return resultInfo;
    }

    /**
    * @Description 月份检查
    * @Author heshiyi@quanyou.com.cn
    * @Date 15:35 2019/6/19
    * @param reportDate
    * @param excelDate
    * @param taskId
    * @param rowNum
    * @param headerName
    * @return
    **/
    protected ResultInfo<AnalysisTaskErrorBean,String > checkReportDateMonth(Date reportDate,Date excelDate,String taskId,Integer rowNum,String headerName){
        ResultInfo<AnalysisTaskErrorBean,String > resultInfo = new ResultInfo<>();

        if(excelDate == null){
            return resultInfo;
        }

        if(!DateUtil.isSameMonthAndNotEmpty(reportDate,excelDate)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
            error.setErrorInfo(headerName+"和导入的时候选择的日期月份不一致");
            error.setRow(rowNum);
            error.setCellName(headerName);
            error.setTaskId(taskId);
            resultInfo.setData(error);
        }
        return resultInfo;
    }

    /**
    * @Description 省份验证
    * @Author heshiyi@quanyou.com.cn
    * @Date 15:46 2019/6/19
    * @param province
    * @param taskId
    * @param rowNum
    * @param provinces
    * @return
    **/
    protected ResultInfo<AnalysisTaskErrorBean,String > checkProvince(String province,String taskId,Integer rowNum,List<Map<String,String>> provinces){
        ResultInfo<AnalysisTaskErrorBean,String > resultInfo = new ResultInfo<>();
        if(StringUtils.isBlank(province)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("省份名称为空");
            AnalysisTaskErrorBean analysisTaskErrorBean = new AnalysisTaskErrorBean();
            analysisTaskErrorBean.setTaskId(taskId);
            analysisTaskErrorBean.setCellName("省");
            analysisTaskErrorBean.setRow(rowNum);
            analysisTaskErrorBean.setErrorInfo("省份为空");
            resultInfo.setData(analysisTaskErrorBean);
            return resultInfo;
        }

        boolean isCorrectProvince = false;
        for(Map<String,String> map : provinces){
            if(province.equals(map.get("PROVINCE"))){
                isCorrectProvince = true;
                break;
            }
        }

        if(!isCorrectProvince){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("省份不存在");
            AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
            errorInfo.setTaskId(taskId);
            errorInfo.setCellName("省");
            errorInfo.setRow(rowNum);
            errorInfo.setErrorInfo("省份（"+province+"）不存在");
            resultInfo.setData(errorInfo);
        }
        return resultInfo;
    }



    protected ResultInfo<AnalysisTaskErrorBean,String > isCorrectShopAndUserName(String taskId,Integer rowNum,String shopName,String userName,List<ShopBasicInfoBean> basicList){
        ResultInfo<AnalysisTaskErrorBean,String > resultInfo = new ResultInfo<>();
        if(StringUtils.isBlank(shopName) || StringUtils.isBlank(userName)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("店铺员工关联校验失败");
            AnalysisTaskErrorBean analysisTaskErrorBean = new AnalysisTaskErrorBean();
            analysisTaskErrorBean.setTaskId(taskId);
            analysisTaskErrorBean.setCellName("账户名");
            analysisTaskErrorBean.setRow(rowNum);
            analysisTaskErrorBean.setErrorInfo("店铺员工关联校验失败");
            resultInfo.setData(analysisTaskErrorBean);
            return resultInfo;
        }

        if(basicList == null || basicList.isEmpty()){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("店铺员工基础信息没有找到");
            AnalysisTaskErrorBean analysisTaskErrorBean = new AnalysisTaskErrorBean();
            analysisTaskErrorBean.setTaskId(taskId);
            analysisTaskErrorBean.setCellName("账户名");
            analysisTaskErrorBean.setRow(rowNum);
            analysisTaskErrorBean.setErrorInfo("店铺员工基础信息没有找到");
            resultInfo.setData(analysisTaskErrorBean);
            return resultInfo;
        }

        for( ShopBasicInfoBean basic : basicList){
            if(shopName.equals(basic.getShopName()) && userName.equals(basic.getUserName())){
                return resultInfo;
            }
        }

        resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
        resultInfo.setMessage("店铺员工关联校验失败");
        AnalysisTaskErrorBean analysisTaskErrorBean = new AnalysisTaskErrorBean();
        analysisTaskErrorBean.setTaskId(taskId);
        analysisTaskErrorBean.setCellName("账户名");
        analysisTaskErrorBean.setRow(rowNum);
        analysisTaskErrorBean.setErrorInfo("店铺员工关联校验失败");
        resultInfo.setData(analysisTaskErrorBean);
        return resultInfo;
    }

    /**
     * @Description 关联校验
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:11 2019/6/13
     * @return
     **/
    protected ResultInfo<AnalysisTaskErrorBean,String > checkShop(String shopName,String taskId,Integer rowNum,List<Map<String,String>> shopNames){
        ResultInfo<AnalysisTaskErrorBean,String > resultInfo = new ResultInfo<>();
        if(StringUtils.isBlank(shopName)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("店铺名称为空");
            AnalysisTaskErrorBean analysisTaskErrorBean = new AnalysisTaskErrorBean();
            analysisTaskErrorBean.setTaskId(taskId);
            analysisTaskErrorBean.setCellName("店铺");
            analysisTaskErrorBean.setRow(rowNum);
            analysisTaskErrorBean.setErrorInfo("店铺为空");
            resultInfo.setData(analysisTaskErrorBean);
            return resultInfo;
        }

        boolean isCorrectShopName = false;
        for(Map<String,String> map : shopNames){
            if(shopName.equals(map.get("SHOP_NAME"))){
                isCorrectShopName = true;
                break;
            }
        }

        if(!isCorrectShopName){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("店铺不存在");
            AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
            errorInfo.setTaskId(taskId);
            errorInfo.setCellName("店铺");
            errorInfo.setRow(rowNum);
            errorInfo.setErrorInfo("店铺（"+shopName+"）不存在");
            resultInfo.setData(errorInfo);
        }
        return resultInfo;
    }

    /**
     * 20190919 -新增对应店铺名称判断[如售中导入时选择店铺只可以导入选择的店铺信息]
     * 20190925 -需求变更：导入时可以导入与选择的店铺名称不同的数据，但只对选择的店铺的数据进行校验并存档
     * @param shopName
     * @param taskId
     * @param rowNum
     * @param resultInfo
     * @return
     */
    private boolean checkValidShopName(String shopName, String taskId, Integer rowNum, ResultInfo<AnalysisTaskErrorBean, String> resultInfo) {
        String reqShopName = analysisTaskDao.queryShopNameByTaskId(taskId);
        if(StringUtils.isNotBlank(reqShopName)){
            if(!shopName.equals(reqShopName)){
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("文件中店铺名称["+ shopName +"]与所选[" + reqShopName + "]不一致");
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(taskId);
                errorInfo.setCellName("店铺");
                errorInfo.setRow(rowNum);
                errorInfo.setErrorInfo("文件中店铺名称["+ shopName +"]与所选[" + reqShopName + "]不一致");
                resultInfo.setData(errorInfo);

                return true;
            }
        }
        return false;
    }

    public abstract List<AnalysisTaskErrorBean> relationDataCheck(List<String> validList,List<String> unvalidList,List<T> entityList);

    public List<T> dataValidate(String taskId,BeanUtils<T> beanUtils,List<Map<String,Object>> dataList,List<String> validList,List<String> unvalidList,String loingUser) {
        List<T> entityList = new ArrayList<>();

        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        for(Map<String,Object> map:dataList){
            String dataId = (String)map.get(ConstantsFileManage.PRIMARY_KEY);
            if(StringUtils.isBlank(dataId)){
                continue;
            }
            ResultInfo<List<AnalysisTaskErrorBean>,Boolean> checkResult = beanUtils.checkData(map,taskId);
            if(checkResult.getMessage()){
                validList.add(dataId);
            }else{
                //验证失败
                errorList.addAll(checkResult.getData());//加入错误信息
                unvalidList.add(dataId);
            }
            entityList.add(beanUtils.getFromMap(map));
        }

        errorList.addAll(this.relationDataCheck(validList,unvalidList,entityList));

        AnalysisTaskBean taskBean = new AnalysisTaskBean();
        taskBean.setDataId(taskId);

        if(errorList.size() == 0){
            //错误信息为空
            taskBean.setProcessResult(ProcessResultEnum.success.name());
        }else{
            //持久化错误信息
            taskBean.setProcessResult(ProcessResultEnum.fail.name());
            analysisTaskService.addAnalysisTaskErrorBatch(errorList,loingUser);
        }

        //解析完成
        taskBean.setProcessStatus(ProcessStatusEnum.analysisFinished.name());
        //更新最终验证结果
        analysisTaskService.updateAnalysisTask(taskBean);

        //在验证通过里面，去除验证没有通过的
        this.removeIdFromUnvalidList(validList,unvalidList);

        //去除重复
        List<String> list =  new ArrayList<>(new TreeSet<String>(unvalidList));
        unvalidList.clear();
        unvalidList.addAll(list);

        return entityList;
    }

    /**
    * @Description 完成同步
    * @Author heshiyi@quanyou.com.cn
    * @Date 9:39 2019/6/22
    * @param taskId
    * @return
    **/
    protected ResultInfo<AnalysisTaskBean,String> finishSyn(String taskId){


        if(StringUtils.isBlank(taskId)){
            ResultInfo<AnalysisTaskBean,String> resultInfo = new ResultInfo<>();
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("参数丢失taskID");
            return resultInfo;
        }

        AnalysisTaskBean taskBean = new AnalysisTaskBean();
        taskBean.setDataId(taskId);
        taskBean.setProcessStatus(ProcessStatusEnum.synFinished.name());
        //更新最终验证结果
        return analysisTaskService.updateAnalysisTask(taskBean);
    }

    private void removeIdFromUnvalidList(List<String> validList,List<String> unvalidList){
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < validList.size(); i++) {
            map.put(validList.get(i), i);
        }

        for (int i = 0; i < unvalidList.size(); i++) {
            Integer pos = map.get(unvalidList.get(i));
            if (pos==null) {
                continue;
            }
            validList.set(pos, null);
        }

        for (int i = validList.size()-1; i>=0; i--) {
            if (validList.get(i)==null) {
                validList.remove(i);
            }
        }
    }
}
