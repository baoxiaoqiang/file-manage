package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ServiceSaleBeforeBean
 * @date 2019/6/11 9:05
 * @projectName file-manage
 * @description: 售前导入信息
 */
@Data
public class ServiceSaleBeforeBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "日期")
    private Date saleDate;

    /**
     * @Description 店铺
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "店铺")
    private String shopName;

    /**
     * @Description 账户名
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "账户名",maxLength = 255)
    private String userName;

    /**
     * @Description 销售额
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "销售额",nullAble = true)
    private BigDecimal saleVolume;

    /**
     * @Description 完成退款金额
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "完成退款金额",nullAble = true)
    private BigDecimal completeRefundMoney;

    /**
     * @Description 咨询人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "咨询人数",nullAble = true)
    private Integer counsellingUserNum;

    /**
     * @Description 接待人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "接待人数",nullAble = true)
    private Integer receptionUserNum;

//    /**
//     * @Description 询单->最终付款成功率
//     * @Author heshiyi@quanyou.com.cn
//     * @Date 9:40 2019/6/11
//     **/
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "询单->最终付款成功率",maxBig = 1,nullAble = true)
//    private BigDecimal askPayRate;

    /**
     * @Description E客服评价发送率
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "E客服评价发送率",maxBig = 1,nullAble = true)
    private BigDecimal customerServiceEvaluateRate;

    /**
     * @Description 客户满意比
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "客户满意比",maxBig = 1,nullAble = true)
    private BigDecimal userSatisfactionRate;

    /**
     * @Description 客单价
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "客单价",nullAble = true)
    private BigDecimal userPerPrice;

    /**
     * @Description 旺旺回复率
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "旺旺回复率",maxBig = 1,nullAble = true)
    private BigDecimal wangwangReplyRate;

    /**
     * @Description 平均响应时间
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "平均响应时间",nullAble = true)
    private BigDecimal responseTimeAverage;

    /**
     * @Description 客服答问比
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "客服答问比",nullAble = true)
    private BigDecimal askAnswerCompare;

    /**
     * @Description 上班天数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "上班天数",nullAble = true)
    private Integer workDays;

    /**
     * @Description 服务总时长
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:42 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "服务总时长",nullAble = true)
    private Integer serviceMinutes;

    /**
     * @Description 平均日服务时长
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:42 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "平均日服务时长",nullAble = true)
    private Integer serviceMinutesPerDay;

    /**
     * @Description 挂起总时长
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:42 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 16,headerName = "挂起总时长",nullAble = true)
    private Integer hangUpMinutes;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
