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
 * @Description IMPORT_CDS_WARDROBE_COMPONENT_TI 
 * @Author bxq
 * @Date 2020-04-21 
**/
@Data
@ApiModel("IMPORT_CDS_WARDROBE_COMPONENT_TI")
@Table(name = "IMPORT_CDS_WARDROBE_COMPONENT_TI")
public class CdsWardrobeComponentTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "DATA_ID")
    private String dataId;
   /* @ApiModelProperty(value = "组件类别CODE")
    @Column(name = "COMPONENT_TYPE_CODE")
    private String componentTypeCode;*/

    @ApiModelProperty(value = "组件类别CODE（ps为了方便借用以前的名字）")
    @Column(name = "FACTORY_CODE")
    private String factoryCode;

    @ApiModelProperty(value = "组件名称")
    @Column(name = "COMPONENT_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "组件名称")
    private String componentName;
    @ApiModelProperty(value = "组件型号")
    @Column(name = "COMPONENT_MODEL")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "组件型号")
    private String componentModel;
    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

}
