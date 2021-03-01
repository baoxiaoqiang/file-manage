package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.OperationalDataBean;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ImportTest
 * @date 2019/6/1014:24
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImportTest {

    private final static Logger logger = LoggerFactory.getLogger(ImportTest.class);

    @Autowired
    private IExcelImportService<OperationalDataBean> operationalDataImportService;


//    @Test
    public void importOperationalData(){

        UploadFileInfoBean fileInfo = new UploadFileInfoBean();
        fileInfo .setFilePath("http://172.30.3.48:80/group1/M00/09/E6/rB4DMFz_InaAMfUnAAAjznx6NGQ14.xlsx");
        fileInfo.setGroupName("group1");
        fileInfo.setRemoteFileName("M00/09/E6/rB4DMFz_InaAMfUnAAAjznx6NGQ14.xlsx");

        AnalysisTaskBean analysisTaskBean = new AnalysisTaskBean();
        analysisTaskBean.setTaskType(ImportFileTypeEnum.operatData.getType());
        analysisTaskBean.setFileInfo(fileInfo);

        ExcelModel excelModel = new ExcelModel();

        operationalDataImportService.importExcel(analysisTaskBean,excelModel);
    }

    @Test
    public void testLoad() throws Exception{

        InputStream in  = FileManager.downFile("group1","M00/09/E7/rB4DMF0AvKuAcrNaAABE_qwz8g854.xlsx");

        Workbook wk = StreamingReader.builder()
                .rowCacheSize(10)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(0);

        //遍历所有的行
        for (Row row : sheet) {
            logger.info("开始遍历第" + row.getRowNum() + "行数据：");
            //遍历所有的列
            for (Cell cell : row) {
                logger.info(cell.getStringCellValue() + " ");
            }
        }
    }

}