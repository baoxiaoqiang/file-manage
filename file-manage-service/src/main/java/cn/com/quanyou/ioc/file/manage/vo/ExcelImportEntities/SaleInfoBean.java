package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SaleInfoBean
 * @date 2019/6/1015:01
 * @projectName file-manage
 * @description: 销售情况
 */
@Data
public class SaleInfoBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 时间
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:03 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "时间")
    private Date saleDate;

    /**
     * @Description 省份
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:03 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "省份")
    private String province;

    /**
     * @Description 店铺名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:03 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "店铺")
    private String shopName;

    /**
     * @Description 订单数
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:04 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "订单数")
    private Integer orderNumber;

    /**
     * @Description 订单金额
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:04 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "订单金额")
    private BigDecimal orderAmount;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;
}
