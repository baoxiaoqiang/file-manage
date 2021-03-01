package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import java.io.Serializable;
import java.util.Date;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;
import javax.persistence.Table;
import javax.persistence.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description 店铺商品销售数据上报接口表 
 * @Author yangli
 * @Date 2019-09-09 
**/
@Data
@ApiModel("店铺商品销售数据上报接口表")
@Table(name = "IMPORT_GOV_STORE_SALE_TI")
public class GovStoreSaleTi extends BasicBean implements Serializable {

    private Integer rowNumber;

    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "日期")
    private Date saleDate;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "省份")
    private String province;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "店铺")
    private String shopName;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "订单号")
    private String orderNumber;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "顾客ID")
    private String customerId;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "商品名称")
    private String productName;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "购买数量")
    private String buyCount;

    private Date reportDate;

}
