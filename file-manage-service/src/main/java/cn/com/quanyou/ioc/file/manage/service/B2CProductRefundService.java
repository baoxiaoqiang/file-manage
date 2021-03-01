package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.IB2CProductRefundTiDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.B2CProductRefundTi;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.IocemallProductDim;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author bxq
 * @desc
 * @date 2019/11/25 13:25
 */
@Service
@Slf4j
public class B2CProductRefundService extends ExcelImportService implements IExcelImportService<B2CProductRefundTi> {

    @Autowired
    private IB2CProductRefundTiDao ib2CProductRefundTiDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<B2CProductRefundTi> excelUtils = new ExcelUtilsMap<>(B2CProductRefundTi.class, this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(), fileInfo.getRemoteFileName()), excelModel, task);

    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        ib2CProductRefundTiDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
        List<Map<String, Object>> dataList = ib2CProductRefundTiDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<B2CProductRefundTi> beanUtils = new BeanUtils<>(B2CProductRefundTi.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId, beanUtils, dataList, validList, unvalidList, loginUser);

        if (!validList.isEmpty()) {
            //更新验证结果
            ib2CProductRefundTiDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if (!unvalidList.isEmpty()) {
            //更新验证结果
            ib2CProductRefundTiDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {
        ResultInfo<String, String> resultInfo = super.checkAnalysisStatus(taskId);
        if (resultInfo.isFailed()) {
            return resultInfo;
        }
        int count = ib2CProductRefundTiDao.queryMistakeAndUncheckedCount(taskId);
        if (count > 0) {
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task=" + taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
//        importDao.deleteByTaskId(taskId);
        ib2CProductRefundTiDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<B2CProductRefundTi>, String> search(SearchParamVo searchParamVo, PageInfo<B2CProductRefundTi> pageInfo) {
        return null;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<IocemallProductDim> orgDimList = ib2CProductRefundTiDao.getOrgDimList();
        List<IocemallProductDim> storeDimList = ib2CProductRefundTiDao.getStoreDimList();

        List<B2CProductRefundTi> list = entityList;
        for (B2CProductRefundTi entity : list) {
            //验证日期格式
            if (!isLegalDate(entity.getProcessDate())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("输入的【处理日期】格式不正确，eg:2019/1/1或2019-1-1");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("处理日期");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }

            if (!isLegalDate(entity.getOrderTime())) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("输入的【下单时间】格式不正确，eg:2019/1/1或2019-1-1");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("下单时间");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }

            //验证数字
            if (!isNumeric(entity.getRefundApplicationAmount())){
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("输入的【退款申请金额】格式不正确，必须为数字");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("退款申请金额");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }

            if (!isNumeric(entity.getRefundableAmount())){
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("输入的【应退款金额】格式不正确，必须为数字");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("应退款金额");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
            //办事处是否存在校验
            Integer platformCon = orgDimList.stream().filter(e -> entity.getAgency().equals(e.getName()))
                    .collect(Collectors.toList()).size();
            if (platformCon.equals(0)) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getAgency() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("办事处名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }

            //平台是否存在校验
            Integer storeCon = storeDimList.stream().filter(e -> entity.getShopName().equals(e.getName()))
                    .collect(Collectors.toList()).size();
            if (storeCon.equals(0)) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("没有与【" + entity.getShopName() + "】匹配的数据");
                error.setRow(Integer.valueOf(entity.getRowNumber()));
                error.setCellName("店铺名称");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }
        }
        return errorList;
    }

    /**
     * 判断字符串是否为数字（如果为空不判断）
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        Pattern p;
        Matcher m;
        p = Pattern.compile("([1-9]\\d*|\\d+\\.\\d+)$");
        m = p.matcher(str);
        if (m.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断时间格式 格式必须为“YYYY-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     *
     * @param sDate
     * @return
     */
    private static boolean isLegalDate(String sDate) {
        if (sDate == null || sDate.length() == 0) {
            return true;
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sDate = sDate.replace("/", "-");
            String[] dateArray = sDate.split("-");
            if (dateArray.length != 3) {
                return false;
            }
            if (dateArray[0].length() < 4) {
                return false;
            }
            if (dateArray[1].length() == 1) {
                dateArray[1] = "0" + dateArray[1];
            }
            if (dateArray[2].length() == 1) {
                dateArray[2] = "0" + dateArray[2];
            }
            //拼接年月日
            sDate = dateArray[0] + "-" + dateArray[1] + "-" + dateArray[2];
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isNumeric("0"));
    }
}
