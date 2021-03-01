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
import cn.com.quanyou.ioc.file.manage.dao.IPutAwayShootBatchDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PutAwayTiForShootBatch;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewProductShootBatchService extends ExcelImportService implements IExcelImportService<PutAwayTiForShootBatch> {

    @Autowired
    private IPutAwayShootBatchDao iPutAwayShootBatchDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {
        UploadFileInfoBean fileInfo = task.getFileInfo();
        if (fileInfo == null || excelModel == null) {
            return;
        }
        ExcelUtilsMap<PutAwayTiForShootBatch> excelUtils = new ExcelUtilsMap<>(PutAwayTiForShootBatch.class, this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(), fileInfo.getRemoteFileName()), excelModel, task);
    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        iPutAwayShootBatchDao.addBatchMap(dataList);
    }

    @Override
    public void dataValidate(String taskId, String loginUser) {
        //查询对应taskId 批次导入后校验成功的数据
        List<Map<String, Object>> dataList = iPutAwayShootBatchDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<PutAwayTiForShootBatch> beanUtils = new BeanUtils<>(PutAwayTiForShootBatch.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId, beanUtils, dataList, validList, unvalidList, loginUser);

        if (!validList.isEmpty()) {
            //更新验证结果
            iPutAwayShootBatchDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if (!unvalidList.isEmpty()) {
            //更新验证结果
            iPutAwayShootBatchDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Override
    public ResultInfo<String, String> copyDataFromTemp(String taskId) {
        ResultInfo<String, String> resultInfo = super.checkAnalysisStatus(taskId);
        if (resultInfo.isFailed()) {
            return resultInfo;
        }
        int count = iPutAwayShootBatchDao.queryMistakeAndUncheckedCount(taskId);
        if (count > 0) {
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task=" + taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
        /** 执行存储过程更新业务表数据*/
        iPutAwayShootBatchDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<PutAwayTiForShootBatch>, String> search(SearchParamVo searchParamVo, PageInfo<PutAwayTiForShootBatch> pageInfo) {
        List<PutAwayTiForShootBatch> dataList = iPutAwayShootBatchDao.search(searchParamVo, pageInfo.getStartRow(), pageInfo.getEndRow());
        pageInfo.setDataList(dataList);
        pageInfo.setTotal(iPutAwayShootBatchDao.querySearchTotal(searchParamVo, pageInfo.getStartRow(), pageInfo.getEndRow()));

        ResultInfo<PageInfo<PutAwayTiForShootBatch>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;

    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<PutAwayTiForShootBatch> list = entityList;

        Set<Integer> totalRepetitionList = new HashSet<>();
        Set<Integer> totalRepetitionListAtType = new HashSet<>();
        for (PutAwayTiForShootBatch entity : list) {

            if (entity.getProductType() == null || entity.getSpuCode() == null
                    || entity.getProductBatch() == null || entity.getPhotoBatchDateCode() == null) {
                continue;
            }
            // 1校验日期格式
            if (!DateTimeUtils.checkDate(entity.getPhotoBatchDateCode())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("拍摄批次");
                errorInfo.setRow(entity.getRowNumber());
                errorInfo.setErrorInfo("拍摄批次（" + entity.getPhotoBatchDateCode() + "）格式不正确[eg:20190808]");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            }

            if (!DateTimeUtils.checkDate(entity.getProductBatch())) {
                AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                errorInfo.setTaskId(entity.getTaskId());
                errorInfo.setCellName("新品批次");
                errorInfo.setRow(entity.getRowNumber());
                errorInfo.setErrorInfo("新品批次（" + entity.getProductBatch() + "）格式不正确[eg:20190808]");

                errorList.add(errorInfo);
                unvalidList.add(entity.getDataId());
            } else {
                Integer con = iPutAwayShootBatchDao.existCheck(entity);
                //2验证是否存在数据
                if (con <= 0) {
                    AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                    error.setErrorInfo("SPU【"
                            + entity.getSpuCode() + "】-新品批次【"
                            + entity.getProductBatch() + "】在系统中不存在");
                    error.setRow(entity.getRowNumber());
                    error.setCellName("匹配数据错误");
                    error.setTaskId(entity.getTaskId());
                    errorList.add(error);
                    unvalidList.add(entity.getDataId());
                }
            }

            //3当节点为拍摄时间时，对拍摄时间进行非空校验
            if ("翻拍".equals(entity.getProductType())) {
                if (StringUtils.isBlank(entity.getReShootingReason())) {
                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("翻拍原因");
                    errorInfo.setRow(entity.getRowNumber());
                    errorInfo.setErrorInfo("当拍摄类型为翻拍时，翻拍原因不能为空！");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }

            } else if ("新品".equals(entity.getProductType())) {
                if (StringUtils.isNotEmpty(entity.getReShootingReason())) {
                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("翻拍原因");
                    errorInfo.setRow(entity.getRowNumber());
                    errorInfo.setErrorInfo("当拍摄类型为新品时，翻拍原因不能填写！");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }
            }

            //4.校验拍摄批次重复性（包括Excel表中的重复和同原数据的重复性）
            //4.1 excel 表验重
            List<Integer> currentRepetitionList = list.stream().filter(e -> e.getSpuCode().equals(entity.getSpuCode()) &&
                    e.getPhotoBatchDateCode().equals(entity.getPhotoBatchDateCode()))
                    .map(PutAwayTiForShootBatch::getRowNumber)
                    .collect(Collectors.toList());

            if (currentRepetitionList.size() > 1) {
                if (!totalRepetitionList.contains(entity.getRowNumber())) {
                    totalRepetitionList.addAll(currentRepetitionList);

                    // 找到重复的rowNumbers
                    List<Integer> rowNumbers = currentRepetitionList.stream()
                            .filter(e -> !e.equals(entity.getRowNumber()))
                            .map(e -> e + 1)
                            .collect(Collectors.toList());

                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("存在重复数据");
                    errorInfo.setRow(entity.getRowNumber());
                    errorInfo.setErrorInfo("在第" + rowNumbers + "行存在与第[" + (entity.getRowNumber() + 1) + "]行有相同的【SPU+拍摄批次】数据");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }
            }

            //4.2 验证数据库存在相同的spu+拍摄批次
            Integer onlyCheckNum = iPutAwayShootBatchDao.onlyCheck(entity);
            if (onlyCheckNum > 0) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("SPU【"
                        + entity.getSpuCode() + "】-拍摄批次【"
                        + entity.getPhotoBatchDateCode() + "】系统中已经存在");
                error.setRow(entity.getRowNumber());
                error.setCellName("SPU+拍摄批次");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }

            //4.3 验证同一个spu是同时导入多个新品
            List<Integer> currentRepetitionListAtType = list.stream().filter(e -> e.getSpuCode().equals(entity.getSpuCode()) &&
                    e.getProductType().equals(entity.getProductType()) && e.getProductType().equals("新品"))
                    .map(PutAwayTiForShootBatch::getRowNumber)
                    .collect(Collectors.toList());

            if (currentRepetitionListAtType.size() > 1) {
                if (!totalRepetitionListAtType.contains(entity.getRowNumber())) {
                    totalRepetitionListAtType.addAll(currentRepetitionListAtType);

                    // 找到重复的rowNumbers
                    List<Integer> rowNumbers = currentRepetitionListAtType.stream()
                            .filter(e -> !e.equals(entity.getRowNumber()))
                            .map(e -> e + 1)
                            .collect(Collectors.toList());

                    AnalysisTaskErrorBean errorInfo = new AnalysisTaskErrorBean();
                    errorInfo.setTaskId(entity.getTaskId());
                    errorInfo.setCellName("存在重复数据");
                    errorInfo.setRow(entity.getRowNumber());
                    errorInfo.setErrorInfo("在同一Excel中，同一个spu只能存在一个【新品】的拍摄批次,在第" + rowNumbers + "行与第[" + (entity.getRowNumber() + 1) + "]行相同");

                    errorList.add(errorInfo);
                    unvalidList.add(entity.getDataId());
                }
            }

            /*
            //4.4 验证数据是否存在相同的spu+新品
            Integer onlyCheckNumForType = iPutAwayShootBatchDao.onlyCheckForType(entity);
            if (onlyCheckNumForType > 0) {
                AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
                error.setErrorInfo("SPU【"
                        + entity.getSpuCode() + "】在系统中已经存在拍摄类型为【新品】的数据");
                error.setRow(entity.getRowNumber());
                error.setCellName("SPU+拍摄类型");
                error.setTaskId(entity.getTaskId());
                errorList.add(error);
                unvalidList.add(entity.getDataId());
            }*/
        }

        return errorList;
    }
}
