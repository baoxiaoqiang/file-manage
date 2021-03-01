package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel;

import cn.com.quanyou.ioc.file.manage.common.ConstantsFileManage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelModel
 * @date 2019/6/511:29
 * @projectName file-manage
 * @description: excel导出相关参数信息
 */

@Data
public class ExcelModel {

    /**
     * @Description excel的第几个sheet，第一个sheet为0.默认为第一个sheet
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:32 2019/6/5
     **/
    private Integer sheetNum  = 0;

    /**
     * @Description 列和导出/导入对象属性的对应关系，比如，第一列对应fileName属性，则：0，fileName
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:34 2019/6/5
     **/
    Map<Integer,String> cellAndDataAttributeMap = new HashMap<>();

    /**
     * @Description 表头名称map
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:18 2019/6/13
     **/
    Map<Integer,String> headerMap = new HashMap<>();

    /**
     * @Description  数据开始的行数，第一行为0，默认为第二行
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:44 2019/6/5
     **/
    private Integer dataRowStart = 1;

    /**
     * @Description 最大获取excel数据条数，如果为空，则获取所有的数据
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:14 2019/6/12
     **/
    private Integer maxRow = ConstantsFileManage.IMPORT_BATCH_ROW_NUMBER;

    /**
     * @Description 属性必须等于的值
     * @Author heshiyi@quanyou.com.cn
     * @Date 22:50 2019/6/13
     **/
    private Map<String,Object> fieldValueEqualMap = new HashMap<>();

}
