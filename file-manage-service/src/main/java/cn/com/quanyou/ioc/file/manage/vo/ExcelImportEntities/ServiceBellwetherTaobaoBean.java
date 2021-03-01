package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ServiceBellwetherTaobaoBean
 * @date 2019/6/10 17:09
 * @projectName file-manage
 * @description: 淘宝服务风向标
 */

@Data
public class ServiceBellwetherTaobaoBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 店铺名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "店铺")
    private String shopName;

    /**
     * @Description 无忧购总评分
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "无忧购总评分")
    private BigDecimal totalGradeCarefreeBuy;

    /**
     * @Description 无忧购商品体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:19 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "无忧购商品体验")
    private BigDecimal productExperienceCarefreeBuy;

    /**
     * @Description 无忧购物流体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:21 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "无忧购物流体验")
    private BigDecimal logisticsExperienceCarefreeBuy;
    
    /**
     * @Description 无忧购售后体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:22 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "无忧购售后体验")
    private BigDecimal afterSaleExperienceCarefreeBuy;

    /**
     * @Description 无忧购纠纷投诉
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:23 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "无忧购纠纷投诉")
    private BigDecimal complaintExperienceCarefreeBuy;

    /**
     * @Description 无忧购咨询体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:24 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "无忧购咨询体验")
    private BigDecimal counsellingExperienceCarefreeBuy;

    /**
     * @Description 础服务基总评分
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "础服务基总评分")
    private BigDecimal totalGradeBasicService;

    /**
     * @Description 础服务商品体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:19 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "础服务商品体验")
    private BigDecimal productExperienceBasicService;

    /**
     * @Description 础服务物流体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:21 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "础服务物流体验")
    private BigDecimal logisticsExperienceBasicService;

    /**
     * @Description 础服务售后体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:22 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "础服务售后体验")
    private BigDecimal afterSaleExperienceBasicService;

    /**
     * @Description 础服务纠纷投诉
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:23 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "础服务纠纷投诉")
    private BigDecimal complaintExperienceBasicService;

    /**
     * @Description 础服务咨询体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:24 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "础服务咨询体验")
    private BigDecimal counsellingExperienceBasicService;

    /**
     * @Description  一键求助满意率
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:28 2019/6/10
     **/@ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "一键求助满意率")
    private BigDecimal oneKeyHelpSatisfactionsRate;

    /**
     * @Description 三年质保48H响应率
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:29 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "三年质保48H响应率")
    private BigDecimal threeYearH48ResponseRate;

    /**
     * @Description 三年质保15天完结率
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:30 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "三年质保15天完结率")
    private BigDecimal threeYearDay15CompletionRate;

    /**
     * @Description 异常报备
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:31 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 16,headerName = "异常报备",maxLength = 2000)
    private String exceptionReport;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
