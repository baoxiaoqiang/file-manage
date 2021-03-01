package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelFile
 * @date 2019/6/25 14:42
 * @projectName file-manage
 * @description: 导出文件信息
 */
@Data
public class ExcelFile {

    /**
     * @Description 文件名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:43 2019/6/25
     **/
    private String fileName;

    /**
     * @Description sheet属性信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:43 2019/6/25
     **/
    private List<ExcelSheet> sheets = new ArrayList<>();
}
