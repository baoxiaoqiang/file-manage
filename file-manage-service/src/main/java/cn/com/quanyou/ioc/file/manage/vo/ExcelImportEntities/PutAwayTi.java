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
 * @Description 新上架上报接口表 
 * @Author yangli
 * @Date 2019-10-10 
**/
@Data
@ApiModel("新上架上报接口表")
@Table(name = "IMPORT_S_PUTAWAY_TI")
public class PutAwayTi extends BasicBean implements Serializable {


    @ApiModelProperty(value = "产品类型")
    @Column(name = "PRODUCT_TYPE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "产品类型", strRang = {"新品", "翻拍"})
    private String productType;

    @ApiModelProperty(value = "翻拍原因")
    @Column(name = "RE_SHOOTING_REASON")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "产品型号",nullAble = true)
    private String reShootingReason;

    @ApiModelProperty(value = "产品名称")
    @Column(name = "PRODUCT_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "产品名称")
    private String productName;

    @ApiModelProperty(value = "新品批次")
    @Column(name = "PRODUCT_BATCH")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "新品批次")
    private String productBatch;

    @ApiModelProperty(value = "上报日期，数据按上报日期全量更新")
    @Column(name = "REPORT_DATE")
    private String reportDate;

    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;
}
