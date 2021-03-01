package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SaleTargetMonthBean
 * @date 2019/6/10 15:06
 * @projectName file-manage
 * @description: 月度销售目标
 */
@Data
public class SaleTargetMonthBean extends BasicBean implements Serializable {


    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 店铺名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "店铺")
    private String shopName;

    /**
     * @Description 年份
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "年份",intMin = 1949)
    private Integer targetYear;

    /**
     * @Description 月份
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "月份",intMin = 1,intMax = 12)
    private Integer targetMonth;

    /**
     * @Description 销售目标
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:17 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "销售目标")
    private BigDecimal target;

    /**
     * @Description 销售目标类型：year，month
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:15 2019/6/16
     **/
    private String planType;

    /**
     * @Description 报告时间
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:52 2019/6/18
     **/
    private Date reportDate;
}
