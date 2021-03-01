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
 * @Description b2c成品退款临时表 
 * @Author bxq
 * @Date 2019-11-25 
**/
@Data
@ApiModel("b2c成品退款临时表")
@Table(name = "IMPORT_B2C_PRODUCT_REFUND_TI")
public class B2CProductRefundTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "DATA_ID")
    private String dataId;
    @ApiModelProperty(value = "店铺名称")
    @Column(name = "SHOP_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "办事处")
    @Column(name = "AGENCY")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "办事处")
    private String agency;
    @ApiModelProperty(value = "平台单号")
    @Column(name = "PLATFORM_NO")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "平台单号")
    private String platformNo;
    @ApiModelProperty(value = "下单时间")
    @Column(name = "ORDER_TIME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "下单时间",nullAble = true)
    private String orderTime;
    @ApiModelProperty(value = "商品编码")
    @Column(name = "PRODUCT_CODE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "商品编码")
    private String productCode;
    @ApiModelProperty(value = "顾客id")
    @Column(name = "CUSTOMER_ID")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "顾客id")
    private String customerId;
    @ApiModelProperty(value = "收货地址")
    @Column(name = "RECEIVING_ADDRESS")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "收货地址",nullAble = true)
    private String receivingAddress;
    @ApiModelProperty(value = "商品名称")
    @Column(name = "PRODUCT_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "商品名称")
    private String productName;
    @ApiModelProperty(value = "退款申请数量")
    @Column(name = "REFUND_COUNT")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "退款申请数量",nullAble = true)
    private String refundCount;
    @ApiModelProperty(value = "应退款金额")
    @Column(name = "REFUNDABLE_AMOUNT")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "应退款金额")
    private String refundableAmount;
    @ApiModelProperty(value = "退款申请金额")
    @Column(name = "REFUND_APPLICATION_AMOUNT")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "退款申请金额")
    private String refundApplicationAmount;
    @ApiModelProperty(value = "退款原因")
    @Column(name = "REFUND_REASON")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "退款原因",nullAble = true)
    private String refundReason;
    @ApiModelProperty(value = "退款性质")
    @Column(name = "REFUND_NATURE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "退款性质")
    private String refundNature;
    @ApiModelProperty(value = "责任主体")
    @Column(name = "SUBJECT_TOLIABILITY")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "责任主体",nullAble = true)
    private String subjectToliability;
    @ApiModelProperty(value = "核实结果")
    @Column(name = "RESULTS")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "核实结果",nullAble = true)
    private String results;
    @ApiModelProperty(value = "处理日期")
    @Column(name = "PROCESS_DATE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "处理日期")
    private String processDate;
    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

}
