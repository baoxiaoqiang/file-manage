package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.facade.IAnalysisTaskService;
import cn.com.quanyou.ioc.file.manage.facade.IAsyncTaskService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.ImportParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: AnalysisTaskServiceTest
 * @date 2019/6/12 11:04
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalysisTaskServiceTest {

    @Autowired
    IAnalysisTaskService taskService;

    @Autowired
    private IAsyncTaskService asyncTaskService;

//    @Test
    public void add() {

        AnalysisTaskBean entity = new AnalysisTaskBean();
        entity.setExcelRowAnalysis(0);
        entity.setFileDataId("test");
        entity.setProcessResult("success");
        entity.setProcessStatus("waitAnalysis");
        entity.setTaskType(ImportFileTypeEnum.operatData.getType());

        taskService.addAnalysisTask(entity);

    }

    @Test
    public void synTask(){
        AnalysisTaskBean entity = new AnalysisTaskBean();
        entity.setExcelRowAnalysis(0);
        entity.setFileDataId("8B1D742ADE63C3D5E053DC031EACD4DD");
        entity.setTaskType(ImportFileTypeEnum.operatData.getType());

        UploadFileInfoBean fileInfo = new UploadFileInfoBean();
        fileInfo.setGroupName("group1");
        fileInfo.setRemoteFileName("M00/09/E7/rB4DMF0AvKuAcrNaAABE_qwz8g854.xlsx");
        entity.setFileInfo(fileInfo);

        ExcelModel excelModel =new ExcelModel();
        excelModel.setDataRowStart(1);

        ImportParamVo importParam = new ImportParamVo();

        List<ResultInfo<AnalysisTaskBean,String>> task = asyncTaskService.createImportExcelTask(entity,importParam);
    }
}