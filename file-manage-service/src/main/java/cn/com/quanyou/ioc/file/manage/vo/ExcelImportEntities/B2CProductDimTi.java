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
 * @Description 套件关联临时表 
 * @Author bxq
 * @Date 2019-11-26 
**/
@Data
@ApiModel("套件关联临时表")
@Table(name = "IMPORT_B2C_PRODUCT_DIM_TI")
public class B2CProductDimTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "产品编码")
    @Column(name = "PRODUCT_CODE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "产品编码")
    private String productCode;
    @ApiModelProperty(value = "产品名称")
    @Column(name = "PRODUCT_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "产品名称")
    private String productName;
    @ApiModelProperty(value = "品类")
    @Column(name = "CATEGORY")
//    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "品类",nullAble = true)
    private String category;
    @ApiModelProperty(value = "风格")
    @Column(name = "STYLE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "风格",nullAble = true)
    private String style;
    @ApiModelProperty(value = "空间")
    @Column(name = "SPACE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "空间",nullAble = true)
    private String space;
    @ApiModelProperty(value = "材质")
    @Column(name = "MATERIAL")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "材质",nullAble = true)
    private String material;
    @ApiModelProperty(value = "年龄")
    @Column(name = "AGE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "年龄",nullAble = true)
    private String age;
    @ApiModelProperty(value = "品类细分")
    @Column(name = "CATEGORY_DETAIL")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "品类细分",nullAble = true)
    private String categoryDetail;
    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;
}
