package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelSheet
 * @date 2019/6/25 14:13
 * @projectName file-manage
 * @description: sheet属性
 */
@Data
public class ExcelSheet {

    /**
     * @Description sheet名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:16 2019/6/25
     **/
    private String sheetName;

    /**
     * @Description sheetd对应的class实体类
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:17 2019/6/25
     **/
    private String sheetClass;

    /**
     * @Description 表头信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:14 2019/6/25
     **/
    private List<ExcelHeader> headers;

    /**
     * @Description 行信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:15 2019/6/25
     **/
    private ExcelRow row;

}
