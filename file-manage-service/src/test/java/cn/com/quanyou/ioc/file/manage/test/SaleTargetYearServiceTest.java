package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.SaleTargetMonthBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.SaleTargetYearBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SaleTargetServiceTest
 * @date 2019/6/18 18:31
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SaleTargetYearServiceTest {

    @Autowired
    IExcelImportService<SaleTargetYearBean> importService;

    @Test
    public void dataValidate() {
        importService.dataValidate("8B9B231B0831CDBBE053DC031EACDC13","sys");
    }

    @Test
    public void copyDataFromTemp() {
        importService.copyDataFromTemp("8B9B231B0831CDBBE053DC031EACDC13");
    }
}