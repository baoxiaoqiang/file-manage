package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ServiceSalingBean
 * @date 2019/6/11 9:53
 * @projectName file-manage
 * @description: 售中服务
 */
@Data
public class ServiceSalingBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:14 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "日期")
    private Date saleDate;

    /**
     * @Description 店铺
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:15 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "店铺")
    private String shopName;

    /**
     * @Description 账户名
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:15 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "账户名",maxLength = 255)
    private String userName;

    /**
     * @Description 咨询人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "咨询人数",nullAble = true)
    private BigDecimal counsellingUserNum;

    /**
     * @Description 接待人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "接待人数",nullAble = true)
    private BigDecimal receptionUserNum;

    /**
     * @Description 直接接待人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:15 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "直接接待人数",nullAble = true)
    private BigDecimal receptionDirectUserNum;

    /**
     * @Description 转入人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:15 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "转入人数",nullAble = true)
    private BigDecimal changeIntoUserNum;

    /**
     * @Description 转出人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:15 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "转出人数",nullAble = true)
    private BigDecimal changeOutUserNum;

    /**
     * @Description 买家发起
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:15 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "买家发起",nullAble = true)
    private BigDecimal buyerLaunch;

    /**
     * @Description 客服主动跟进
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "客服主动跟进",nullAble = true)
    private BigDecimal customerServiceFollow;

    /**
     * @Description 总消息
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "总消息",nullAble = true)
    private BigDecimal messageTotal;

    /**
     * @Description 买家消息
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "买家消息",nullAble = true)
    private BigDecimal messageNumBuyer;

    /**
     * @Description 客服消息
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "客服消息",nullAble = true)
    private BigDecimal messageNumCustomerService;

    /**
     * @Description 答问比
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "答问比",nullAble = true)
    private BigDecimal compareAskAnswer;

    /**
     * @Description 客服字数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "客服字数",nullAble = true)
    private BigDecimal wordNumCustomerService;

    /**
     * @Description 最大同时接待
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:16 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "最大同时接待",nullAble = true)
    private BigDecimal maxReceptionNumConcurrently;

    /**
     * @Description 未回复人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 16,headerName = "未回复人数",nullAble = true)
    private BigDecimal numNotReply;

    /**
     * @Description 旺旺回复率
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 17,headerName = "旺旺回复率",nullAble = true)
    private BigDecimal replyRateWangwang;

    /**
     * @Description 慢响应人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 18,headerName = "慢响应人数",nullAble = true)
    private BigDecimal numResponseSlowly;

    /**
     * @Description 长接待人数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 19,headerName = "长接待人数",nullAble = true)
    private BigDecimal numReceptionLongTime;

    /**
     * @Description 首次响应(秒)
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 20,headerName = "首次响应(秒)",nullAble = true)
    private BigDecimal timesResponseFirstTime;

    /**
     * @Description 平均响应(秒)
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 21,headerName = "平均响应(秒)",nullAble = true)
    private BigDecimal timesResponseAverage;

    /**
     * @Description 平均接待时长
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:17 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 22,headerName = "平均接待时长",nullAble = true)
    private BigDecimal timesReceptionAverage;

    /**
     * @Description 发出评价数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:18 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 23,headerName = "发出评价数",nullAble = true)
    private BigDecimal evaluateNumSendOut;

    /**
     * @Description 收到评价数
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:18 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 24,headerName = "收到评价数",nullAble = true)
    private BigDecimal evaluateNumReceived;

    /**
     * @Description 很满意
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:24 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 25,headerName = "很满意",nullAble = true)
    private BigDecimal rateVerySatisfied;

    /**
     * @Description 满意
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 26,headerName = "满意",nullAble = true)
    private BigDecimal rateSatisfied;

    /**
     * @Description 一般
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 27,headerName = "一般",nullAble = true)
    private BigDecimal rateNormal;

    /**
     * @Description 不满
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 28,headerName = "不满",nullAble = true)
    private BigDecimal rateDissatisfied;

    /**
     * @Description 很不满
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 29,headerName = "很不满",nullAble = true)
    private BigDecimal rateDissatisfiedVery;

    /**
     * @Description 评价发送率
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 30,headerName = "评价发送率",nullAble = true)
    private BigDecimal rateEvaluateSend;

    /**
     * @Description 评价返回率
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 31,headerName = "评价返回率",nullAble = true)
    private BigDecimal rateEvaluateReturn;

    /**
     * @Description 客户满意比
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:26 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 32,headerName = "客户满意比",nullAble = true)
    private BigDecimal rateCustomerSatisfied;

    /**
     * @Description 客服服务满意度
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:26 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 33,headerName = "客服服务满意度",nullAble = true)
    private BigDecimal rateCustomerServiceSatisfied;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;
}
