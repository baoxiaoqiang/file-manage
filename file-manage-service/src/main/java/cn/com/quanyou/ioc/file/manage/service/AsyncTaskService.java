package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ProcessStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.common.utils.StringUtil;
import cn.com.quanyou.ioc.file.manage.facade.IAnalysisTaskService;
import cn.com.quanyou.ioc.file.manage.facade.IAsyncTaskService;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.ImportParamVo;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: AsyncTask
 * @date 2019/6/12 13:05
 * @projectName file-manage
 * @description:
 */
@Component
@Async
@Slf4j
public class AsyncTaskService extends ImportBasicService implements IAsyncTaskService {

    @Autowired
    private IAnalysisTaskService analysisTaskService;

    @Override
    public List<ResultInfo<AnalysisTaskBean,String>>  createImportExcelTask(AnalysisTaskBean task, ImportParamVo importParam) {

        List<ResultInfo<AnalysisTaskBean,String>> returnInfoList   = new ArrayList<>();

        InputStream is = FileManager.downFile(task.getFileInfo().getGroupName(),task.getFileInfo().getRemoteFileName());

        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);            // InputStream or File for XLSX file (required)

        for(int i =0;i<workbook.getNumberOfSheets();i++){



            String sheetName = workbook.getSheetName(i);
            ImportFileTypeEnum type = ImportFileTypeEnum.getByDescription(sheetName);
            if(type == null){
                //导入sheet名称模板没有找到
                continue;
            }else{
                if(!type.getGroup().equals(importParam.getImportType())){
                    //不在此次导入的group之内
                    continue;
                }
            }
            AnalysisTaskBean taskNew = new AnalysisTaskBean();

            taskNew.setReportDate(importParam.getDate());
            taskNew.setTaskType(type.getType());
            taskNew.setExcelRowAnalysis(0);
            taskNew.setFileDataId(task.getFileDataId());
            taskNew.setProcessStatus(ProcessStatusEnum.waitAnalysis.name());
            taskNew.setFileInfo(task.getFileInfo());
            taskNew.setCreatedBy(task.getCreatedBy());
            taskNew.setLastUpdatedBy(task.getCreatedBy());
            taskNew.setImportUser(task.getCreatedBy());

            if (!StringUtils.isEmpty(importParam.getShopName())) {
                taskNew.setShopName(importParam.getShopName());
            }
            if (!StringUtils.isEmpty(importParam.getFactoryCode())) {
                taskNew.setFactoryCode(importParam.getFactoryCode());
            }
            if (!StringUtils.isEmpty(importParam.getFactoryName())) {
                taskNew.setFactoryName(importParam.getFactoryName());
            }
            if (!StringUtils.isEmpty(importParam.getLineCode())) {
                taskNew.setLineCode(importParam.getLineCode());
            }
            if (!StringUtils.isEmpty(importParam.getLineName())) {
                taskNew.setLineName(importParam.getLineName());
            }

            ResultInfo<AnalysisTaskBean,String> taskResult = analysisTaskService.addAnalysisTask(taskNew);
            if(taskResult.getResultStatusEnum().isFail()){
               log.error("创建解析任务失败");
                continue;
            }
            taskNew = taskResult.getData();
            taskNew.setProcessStatus(ProcessStatusEnum.analysising.name());
            ExcelModel excelModel = new ExcelModel();
            excelModel.setDataRowStart(type.getDataStartRow());
            excelModel.setSheetNum(i);

            IExcelImportService excelImportService = super.getServivceByFileImportTypeEnum(type);
            excelImportService.importExcel(taskNew,excelModel);


            ResultInfo<AnalysisTaskBean,String> resultInfo = new ResultInfo<>();
            resultInfo.setData(taskNew);
            returnInfoList.add(resultInfo);
        }
        try {
            is.close();
        } catch (IOException e) {

        }
        return returnInfoList;
    }
}
