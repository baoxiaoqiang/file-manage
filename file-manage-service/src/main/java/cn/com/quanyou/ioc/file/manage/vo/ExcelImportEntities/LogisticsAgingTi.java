package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description: 物流时效接口表
 * @Author: yangli
 * @Date: 2020/2/12-14:02
**/
@Data
@ApiModel("物流时效接口表")
@Table(name = "IMPORT_LOGISTICS_AGING_TI")
public class LogisticsAgingTi extends BasicBean implements Serializable {

    private Integer rowNumber;

    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "日期")
    private String dateCode;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "公司名称")
    private String companyName;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "耗时(天)")
    private String timeExpend;

}
