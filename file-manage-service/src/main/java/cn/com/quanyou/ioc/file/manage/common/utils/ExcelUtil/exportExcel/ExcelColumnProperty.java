package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelColumnProperty
 * @date 2019/6/25 13:41
 * @projectName file-manage
 * @description: 列属性
 */
@Data
public class ExcelColumnProperty extends ExcelBasic implements Serializable {

    /**
     * @Description 属性名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:38 2019/6/25
     **/
    private String fieldName;

    /**
     * @Description 属性类型
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:38 2019/6/25
     **/
    private String fieldType;

    /**
     * @Description 格式化
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:38 2019/6/25
     **/
    private String fieldTypeFormat;

}
