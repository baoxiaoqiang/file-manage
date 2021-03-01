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
 * @Description 新上架拍摄时间上报接口表
 * @Author bxq
 * @Date 2019-10-10 
**/
@Data
@ApiModel("新上架上报接口表")
@Table(name = "IMPORT_S_PUTAWAY_TI")
public class PutAwayTiForTaskStatus extends BasicBean implements Serializable {

    /*@ApiModelProperty(value = "拍摄类型")
    @Column(name = "PRODUCT_TYPE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "拍摄类型", strRang = {"新品", "翻拍"})
    private String productType;*/

    @ApiModelProperty(value = "SPU编码")
    @Column(name = "SPU_CODE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "SPU编码")
    private String spuCode;

    /*@ApiModelProperty(value = "新品批次")
    @Column(name = "PRODUCT_BATCH")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "新品批次")
    private String productBatch;*/

    @ApiModelProperty(value = "拍摄批次")
    @Column(name = "PHOTO_BATCH_DATE_CODE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "拍摄批次")
    private String photoBatchDateCode;

    @ApiModelProperty(value = "拍摄时间")
    @Column(name = "PRODUCT_SHOOT_DATE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "拍摄时间",nullAble = true)
    private String productShootDate;

    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

    @ApiModelProperty(value = "流程节点code（ps为了方便借用以前的名字）")
    @Column(name = "FACTORY_CODE")
    private String factoryCode;

    @ApiModelProperty(value = "流程节点code")
    @Column(name = "TASK_CODE")
    private String taskCode;
}
