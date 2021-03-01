package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelHeader
 * @date 2019/6/25 13:53
 * @projectName file-manage
 * @description: 表头
 */
@Data
public class ExcelHeader {

    /**
     * @Description 列列表
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:53 2019/6/25
     **/
    private List<ExcelHeaderColumn> columns = new ArrayList<>();
}
