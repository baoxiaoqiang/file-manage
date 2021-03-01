package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.common.component.util.RegexUtils;
import cn.com.quanyou.ioc.common.component.util.StringUtils;
import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.ICdsProcessPieceTiDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.*;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.CdsProcessPieceTi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CdsProcessPieceService extends ExcelImportService implements IExcelImportService<CdsProcessPieceTi> {

    @Autowired
    private ICdsProcessPieceTiDao iCdsProcessPieceTiDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<CdsProcessPieceTi> excelUtils = new ExcelUtilsMap<>(CdsProcessPieceTi.class, this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(), fileInfo.getRemoteFileName()), excelModel, task);
    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        iCdsProcessPieceTiDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
        //查询对应taskId 批次导入后校验成功的数据
        List<Map<String, Object>> dataList = iCdsProcessPieceTiDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<CdsProcessPieceTi> beanUtils = new BeanUtils<>(CdsProcessPieceTi.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId, beanUtils, dataList, validList, unvalidList, loginUser);

        if (!validList.isEmpty()) {
            //更新验证结果
            iCdsProcessPieceTiDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if (!unvalidList.isEmpty()) {
            //更新验证结果
            iCdsProcessPieceTiDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {
        ResultInfo<String, String> resultInfo = super.checkAnalysisStatus(taskId);
        if (resultInfo.isFailed()) {
            return resultInfo;
        }
        int count = iCdsProcessPieceTiDao.queryMistakeAndUncheckedCount(taskId);
        if (count > 0) {
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task=" + taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
        /** 执行存储过程更新业务表数据*/
        iCdsProcessPieceTiDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<CdsProcessPieceTi>, String> search(SearchParamVo searchParamVo, PageInfo<CdsProcessPieceTi> pageInfo) {
        List<CdsProcessPieceTi> dataList = iCdsProcessPieceTiDao.search(searchParamVo, pageInfo.getStartRow(), pageInfo.getEndRow());
        pageInfo.setDataList(dataList);
        pageInfo.setTotal(iCdsProcessPieceTiDao.querySearchTotal(searchParamVo, pageInfo.getStartRow(), pageInfo.getEndRow()));

        ResultInfo<PageInfo<CdsProcessPieceTi>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<CdsProcessPieceTi> list = entityList;
        List<CdsProcessPieceVo> processNameList = iCdsProcessPieceTiDao.listProcessNameList(list.get(0).getFactoryCode(), list.get(0).getLineCode());
        List<CdsEmployeeInfoVo> employeeInfoList = iCdsProcessPieceTiDao.listEmployeeInfo();

        Set<String> totalRepetitionList = new HashSet<>();
        for (CdsProcessPieceTi entity : list) {

            if (entity.getEmployeeName() == null || entity.getCardNo() == null || entity.getProcessName() == null) {
                continue;
            }
            // 1校验工序名
            List<CdsProcessPieceVo> nameList = processNameList.stream().filter(e -> e.getProcessName().equals(entity.getProcessName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(nameList)) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("工序");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo(processNameList.get(0).getFactoryName() + processNameList.get(0).getLineName() + "没有【" + entity.getProcessName() + "】工序");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            // 2校验员工信息
            List<CdsEmployeeInfoVo> existList = employeeInfoList.stream().filter(e -> e.getEmployeeName().equals(entity.getEmployeeName()) &&
                    e.getCardNo().equals(entity.getCardNo()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(existList)) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("姓名+身份证号码");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("导入excel中的姓名【" + entity.getEmployeeName() + "】或身份证号码【" + entity.getCardNo() + "】不正确，请检查");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            //3数据重复校验
            List<String> currentRepetitionList = list.stream().filter(
                    e -> entity.getProcessName().equals(e.getProcessName())
                            && entity.getCardNo().equals(e.getCardNo())
                            && entity.getEmployeeName().equals(e.getEmployeeName())
                            && Integer.valueOf(entity.getRowNumber()) <= Integer.valueOf(e.getRowNumber()))
                    .map(CdsProcessPieceTi::getRowNumber)
                    .collect(Collectors.toList());
            if (currentRepetitionList.size() > 1) {

                if (!totalRepetitionList.contains(entity.getRowNumber())) {
                    totalRepetitionList.addAll(currentRepetitionList);

                    // 找到重复的rowNumbers
                    List<Integer> rowNumbers = currentRepetitionList.stream()
                            .filter(e -> !e.equals(entity.getRowNumber()))
                            .map(e -> Integer.parseInt(e) + 1)
                            .collect(Collectors.toList());

                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("存在相同数据");
                    errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                    errorInfo.setErrorInfo("在第" + rowNumbers + "行存在与第[" + (Integer.parseInt(entity.getRowNumber()) + 1) + "]行相同的[工序，姓名，身份证号]");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }
            }
            // 4校验出勤小时：必须为数字
            if (StringUtils.isNotBlank(entity.getWorkHour()) && !RegexUtils.checkNumber(entity.getWorkHour())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("出勤小时");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("出勤小时【" + entity.getWorkHour() + "】必须为数字");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            // 5校验计件工资：必须为数字
            if (StringUtils.isNotBlank(entity.getPieceWage()) && !RegexUtils.checkNumber(entity.getPieceWage())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("计件工资");
                errorInfo.setRow(Integer.parseInt(entity.getRowNumber()));
                errorInfo.setErrorInfo("计件工资【" + entity.getPieceWage() + "】必须为数字");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }
        }

        return errorList;
    }

}
