package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: TransportationCostsBean
 * @date 2019/6/10 15:24
 * @projectName file-manage
 * @description: 运费
 */
@Data
public class TransportationCostsBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 店铺
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:25 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "店铺")
    private String shopName;

    /**
     * @Description 运费金额
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:25 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "运费金额")
    private BigDecimal money;

    /**
     * @Description 时间
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:25 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "时间")
    private Date tranDate;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;
}
