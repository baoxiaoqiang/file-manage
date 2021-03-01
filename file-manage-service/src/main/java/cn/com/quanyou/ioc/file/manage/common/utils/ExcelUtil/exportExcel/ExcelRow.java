package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelRow
 * @date 2019/6/25 14:12
 * @projectName file-manage
 * @description:
 */
@Data
public class ExcelRow {

    /**
     * @Description 列属性
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:13 2019/6/25
     **/
    List<ExcelColumnProperty> columns= new ArrayList<>();
}
