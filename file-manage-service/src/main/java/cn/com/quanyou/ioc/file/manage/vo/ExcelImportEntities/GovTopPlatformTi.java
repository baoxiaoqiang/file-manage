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
 * @Description 各平台各渠道访客数量top5上报接口表 
 * @Author yangli
 * @Date 2019-09-11 
**/
@Data
@ApiModel("各平台各渠道访客数量top5上报接口表")
@Table(name = "IMPORT_GOV_TOP_PLATFORM_TI")
public class GovTopPlatformTi extends BasicBean implements Serializable {

    @ApiModelProperty(value = "排名")
    @Column(name = "RANK_NO")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "排名", strRang = {"1","2","3","4","5"})
    private String rankNo;

    @ApiModelProperty(value = "描述")
    @Column(name = "RANK_REMARK")
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "描述")
    private String rankRemark;

    @ApiModelProperty(value = "行数，第一行为1", hidden = true)
    @Column(name = "ROW_NUMBER")
    private String rowNumber;

}
