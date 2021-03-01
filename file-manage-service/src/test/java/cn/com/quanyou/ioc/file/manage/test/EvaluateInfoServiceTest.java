package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.EvaluateInfoBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: EvaluateInfoServiceTest
 * @date 2019/6/17 21:15
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluateInfoServiceTest {

    @Autowired
    IExcelImportService<EvaluateInfoBean> evaluateInfoService;

    @Test
    public void dataValidate() {


        evaluateInfoService.dataValidate("8B9B231B0952CDBBE053DC031EACDC13","sys");
    }

    @Test
    public void copyDataFromTemp() {
        evaluateInfoService.copyDataFromTemp("8B9B231B0952CDBBE053DC031EACDC13");
    }
}