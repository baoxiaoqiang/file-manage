package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceBellwetherTaobaoBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceSaleBeforeBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ServiceSaleBeforeServiceTest
 * @date 2019/6/18 22:25
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceSaleBeforeServiceTest {

    @Autowired
    IExcelImportService<ServiceSaleBeforeBean> importService;

    @Test
    public void dataValidate() {
        importService.dataValidate("8B9B231B0B0BCDBBE053DC031EACDC13","sys");
    }

    @Test
    public void copyDataFromTemp() {
        importService.copyDataFromTemp("8B9B231B0B0BCDBBE053DC031EACDC13");
    }
}