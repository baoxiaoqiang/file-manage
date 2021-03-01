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
 * @description: 京东服务风向标
 */

@Data
public class ServiceBellwetherJDBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 总评分
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "总评分")
    private BigDecimal totalGrade;

    /**
     * @Description 售后体验
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:22 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "售后体验")
    private BigDecimal afterSaleService;

    /**
     * @Description 物流履约率
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:21 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "物流履约率")
    private BigDecimal logisticsImplementation;

    /**
     * @Description 用户评价
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:44 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "用户评价")
    private BigDecimal customerEvaluate;

    /**
     * @Description 客服咨询
     * @Date 17:46 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "客服咨询")
    private BigDecimal customerServiceCounselling;

    /**
     * @Description 交易纠纷
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:47 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "交易纠纷")
    private BigDecimal transactionsComplaint;

    /**
     * @Description 超过同行
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:49 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "超过同行")
    private BigDecimal exceedTrade;

    /**
     * @Description 较昨日
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:50 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "较昨日")
    private BigDecimal compareYesterday;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
