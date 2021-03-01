package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: LogisticsBean
 * @date 2019/6/10 15:26
 * @projectName file-manage
 * @description: 物流信息
 */
@Data
public class LogisticsBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 统计类型：1-办事处 3-第三方
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:27 2019/6/10
     **/
    //@ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,ints = {1,3},headerName = "统计类型",intMin = Integer.MIN_VALUE)
    //删除
    private Integer agencyType;

    /**
     * @Description 服务商 、办事处
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:27 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "办事处/服务商",maxLength = 255)
    private String agencyName;

    /**
     * @Description 总需配送商品行（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:27 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "总需配送订单")
    private Integer needShipTotal;

    /**
     * @Description 本月已配送商品行（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:28 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "已配送订单(本月)")
    private Integer thisMonthShippedCount;

    /**
     * @Description 本月未配送商品行（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:29 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "需配送订单(本月)")
    private BigDecimal thisMonthWaitCount;

    /**
     * @Description 已发货（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:35 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "已发货")
    private Integer shippedCount;

    /**
     * @Description 到店商品行数据（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:36 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "到店数据")
    private Integer deliveredCount;

    /**
     * @Description 缺货（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:36 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "缺货")
    private Integer outOfStockCount;
    
    /**
     * @Description 延期商品行（个）
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:37 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "延期订单(顾客暂不要货)")
    private Integer delayCount;

    /**
     * @Description 昨日实际配送商品行
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:37 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "昨日实际配送订单")
    private BigDecimal lastDayShippedCount;

    /**
     * @Description 昨日应配送商品行
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:38 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "昨日应配送订单")
    private BigDecimal lastDayTotalCount;
    
    /**
     * @Description 今日应配送商品行
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:39 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "今日应配送订单")
    private BigDecimal todayTotalCount;

    /**
     * @Description 今日已配送商品行
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:40 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "今日已配送商品行")
    private BigDecimal todayShippedCount;

    /**
     * @Description 昨日配送差异数量
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:40 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "差异数量")
    private BigDecimal lastDayDiffCount;

    /**
     * @Description 当前配送完成率
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:41 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "配送完成率",maxBig = 1)
    private BigDecimal nowShipFinishRatio;

    /**
     * @Description 日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/18
     **/
    private Date shipmentDate;

}
