package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelBasic
 * @date 2019/6/25 13:43
 * @projectName file-manage
 * @description: excel基础属性
 */
@Data
public class ExcelBasic implements Serializable {

    /**
     * @Description 占多少列，默认1
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:44 2019/6/25
     **/
    private Integer columnNum = 1;

    /**
     * @Description 占多少行,默认1
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:44 2019/6/25
     **/
    private Integer rowNum =1;
}
