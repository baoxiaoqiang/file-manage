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
 * @Description 新品基础信息导入表 
 * @Author bxq
 * @Date 2020-01-02 
**/
@Data
@ApiModel("新品基础信息导入表")
@Table(name = "IMPORT_S_NEW_PRODUCT_BASIC_TI")
public class ImportNewProductBasicTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "sku编码")
    @Column(name = "SKU_CODE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "sku名称")
    @Column(name = "SKU_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "新品批次号")
    @Column(name = "PRODUCT_BATCH_NO")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "新品批次号")
    private String productBatchNo;
    @Column(name = "SPU_FLAG")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "SPU标识")
    private String spuFlag;
    @Column(name = "SERIES")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "系列")
    private String series;
    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

}
