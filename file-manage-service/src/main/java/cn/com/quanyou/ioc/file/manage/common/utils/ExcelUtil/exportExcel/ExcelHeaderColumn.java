package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelHeaderColumn
 * @date 2019/6/25 13:25
 * @projectName file-manage
 * @description: EXCEL中的header设置
 */
@Data
public class ExcelHeaderColumn extends ExcelBasic implements Serializable {

    /**
     * @Description 表头的名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:28 2019/6/25
     **/
    private String headerName;

    /**
     * @Description 列的序号
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:10 2019/6/25
     **/
    private Integer columnIndex;
}
