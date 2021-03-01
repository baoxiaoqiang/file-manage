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
 * @Description IMPORT_CDS_PROCESS_PIECE_TI 
 * @Author bxq
 * @Date 2019-10-15 
**/
@Data
@ApiModel("IMPORT_CDS_PROCESS_PIECE_TI")
@Table(name = "IMPORT_CDS_PROCESS_PIECE_TI")
public class CdsProcessPieceTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "计件日期")
    @Column(name = "PIECE_DATE")
    private String pieceDate;
    @ApiModelProperty(value = "工厂编码")
    @Column(name = "FACTORY_CODE")
    private String factoryCode;
    @ApiModelProperty(value = "产线编码")
    @Column(name = "LINE_CODE")
    private String lineCode;
    @ApiModelProperty(value = "工序名称")
    @Column(name = "PROCESS_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "工序")
    private String processName;
    @ApiModelProperty(value = "员工姓名")
    @Column(name = "EMPLOYEE_NAME")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "姓名")
    private String employeeName;
    @ApiModelProperty(value = "身份证号")
    @Column(name = "CARD_NO")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "身份证号码")
    private String cardNo;
    @ApiModelProperty(value = "出勤小时")
    @Column(name = "WORK_HOUR")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "出勤小时")
    private String workHour;
    @ApiModelProperty(value = "计件工资")
    @Column(name = "PIECE_WAGE")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "计件工资")
    private String pieceWage;
    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

}
