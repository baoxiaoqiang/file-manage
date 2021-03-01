package cn.com.quanyou.ioc.file.manage.facade;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;

import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IExcelImportService
 * @date 2019/6/518:03
 * @projectName file-manage
 * @description:
 */
public interface IExcelImportService<T> {

    /**
    * @Description 导入Excel内容
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:30 2019/6/10
    * @param task 任务信息
    * @param excelModel 解析方案
    * @return
    **/
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel);

    /**
    * @Description 字符串保存excel内容
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:44 2019/6/17
    * @param dataList
    * @return
    **/
    void saveExcelDataMap(List<Map<String,Object>> dataList);

    /**
    * @Description 关联校验
    * @Author heshiyi@quanyou.com.cn
    * @Date 18:11 2019/6/13
    * @return
    **/
    void dataValidate(String taskId,String loginUser);

    /**
    * @Description 同步数据
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:58 2019/6/18
    * @param taskId
    * @return
    **/
    ResultInfo<String,String>  copyDataFromTemp(String taskId);

    /**
    * @Description 查询
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:15 2019/6/20
    * @param searchParamVo
    * @param pageInfo
    * @return
    **/
    ResultInfo<PageInfo<T>, String> search(SearchParamVo searchParamVo, PageInfo<T> pageInfo);

}
