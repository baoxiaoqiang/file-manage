package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: OperationalDataBean
 * @date 2019/6/615:31
 * @projectName file-manage
 * @description: 各店运营数据
 */
@Data
public class OperationalDataBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 店铺名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:32 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "店铺")
    private String shopName;

    /**
     * @Description  日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:33 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "日期")
    private Date saleDate;

//    /**
//     * @Description 支付金额
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:34 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "支付金额",nullAble = true)
//    private BigDecimal payAmount;
//
//    /**
//     * @Description 买错退款的金额
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:39 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "拍错退款金额",nullAble = true)
//    private BigDecimal refundWrongAmount;
//
//    /**
//     * @Description 非销售金额
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:40 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "非销售金额",nullAble = true)
//    private BigDecimal noneSaleAmount;
//
//    /**
//     * @Description 实际销售金额
//     * @Author heshiyi@quanyou.com.cn
//     * @Date  2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "实际销售额",nullAble = true)
//    private BigDecimal realSaleAmount;

    /**
     * @Description 推广费用
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:43 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "推广费用",nullAble = true)
    private BigDecimal promotionFee;

    /**
     * @Description 访客数量
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:44 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "访客数",nullAble = true)
    private Integer vistorCount;

    /**
     * @Description 浏览量
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:45 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "浏览量",nullAble = true)
    private Integer viewCount;

    /**
     * @Description 支付转化率
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:48 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "支付转化率",maxBig = 1,nullAble = true)
    private BigDecimal payConversionRate;

//    /**
//     * @Description 客单价
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:50 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "客单价",nullAble = true)
//    private BigDecimal amountPerBuyer;
//
//    /**
//     * @Description 支付订单数量
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:51 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "支付订单数",nullAble = true)
//    private Integer paidOrderCount;
//
//    /**
//     * @Description 支付买家数
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:52 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "支付买家数",nullAble = true)
//    private Integer paidBuyerCount;
//
//    /**
//     * @Description 售后退款（周）
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:53 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "售后退款（周）",nullAble = true)
//    private BigDecimal refundPostSaleWeek;
//
//    /**
//     * @Description 店铺排名
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:55 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "店铺排名",intMin = 1,nullAble = true)
//    private Integer storeRank;
//
//    /**
//     * @Description 预售销售额
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:56 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "预售销售额",nullAble = true)
//    private BigDecimal bookingAmount;
//
//    /**
//     * @Description 预售总金额，包括刷
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:57 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 16,headerName = "预售总额+刷",nullAble = true)
//    private BigDecimal bookingPlusAmount;
//
//    /**
//     * @Description 新零售
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 15:59 2019/6/6
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 17,headerName = "新零售",nullAble = true)
//    private BigDecimal newRetailAmount;

    /**
     * @Description 备注
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:59 2019/6/6
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "备注",maxLength = 2000,nullAble = true)
    private String saleRemark;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
