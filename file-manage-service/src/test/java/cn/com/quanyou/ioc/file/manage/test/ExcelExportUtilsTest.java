package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel.ExcelExportUtils;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.EvaluateNegativeDetailBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceBellwetherTaobaoBean;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelExportUtilsTest
 * @date 2019/6/25 14:48
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExcelExportUtilsTest {

    @Autowired
    private IExcelImportService<ServiceBellwetherTaobaoBean> excelImportService;

    @Autowired
    private IExcelImportService<EvaluateNegativeDetailBean> excelImportServiceE;

    @Test
    public void getFileInfo() {
        SearchParamVo searchParamVo = new SearchParamVo();
        searchParamVo.setSearchGroup(ImportFileTypeEnum.serviceBellwetherTaobao.getGroup());

        SearchParamVo searchParamVoE = new SearchParamVo();
        searchParamVoE.setSearchGroup(ImportFileTypeEnum.evaluateInfo.getGroup());
        PageInfo<ServiceBellwetherTaobaoBean> pageInfo =  new PageInfo<>();

        PageInfo<EvaluateNegativeDetailBean> pageInfoE =  new PageInfo<>();

        ResultInfo<PageInfo<ServiceBellwetherTaobaoBean>, String>  resultInfo = excelImportService.search(searchParamVo,pageInfo);
        ResultInfo<PageInfo<EvaluateNegativeDetailBean>, String>  resultInfoE = excelImportServiceE.search(searchParamVo,pageInfoE);

        Map<String, List> dataMap = new HashMap<>();
        ExcelExportUtils excelExportUtils = new ExcelExportUtils();
        dataMap.put(EvaluateNegativeDetailBean.class.getName(),resultInfoE.getData().getDataList());
        dataMap.put(ServiceBellwetherTaobaoBean.class.getName(),resultInfo.getData().getDataList());
        File file = excelExportUtils.test("ServiceBellwetherTaobao",dataMap);
        System.out.println(file.getAbsolutePath());
    }
}