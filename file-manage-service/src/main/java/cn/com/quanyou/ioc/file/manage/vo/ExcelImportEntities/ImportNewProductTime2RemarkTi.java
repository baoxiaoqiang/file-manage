package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description 新品上新时间和备注信息导入表
 * @Author bxq
 * @Date 2020-01-02 
**/
@Data
@ApiModel("新品上新时间和备注信息导入表")
@Table(name = "IMPORT_S_NEW_PRODUCT_TIME2REMARK_TI")
public class ImportNewProductTime2RemarkTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "spu编码")
    @Column(name = "SPU_CODE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "SPU编码")
    private String spuCode;
    @ApiModelProperty(value = "sku名称")
    @Column(name = "SPU_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "上新时间")
    @Column(name = "ADD_TIME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "上新时间", nullAble = true)
    private String addTime;
    @Column(name = "ADD_REMARK")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "上新备注", nullAble = true)
    private String addRemark;

    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

}
