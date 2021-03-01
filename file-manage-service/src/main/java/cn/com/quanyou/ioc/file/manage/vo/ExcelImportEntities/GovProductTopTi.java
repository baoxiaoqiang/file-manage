package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 店铺商品销售数据上报接口表 
 * @Author yangli
 * @Date 2019-09-09 
**/
@Data
@ApiModel("店铺商品销售数据上报接口表")
@Table(name = "IMPORT_GOV_STORE_SALE_TI")
public class GovProductTopTi extends BasicBean implements Serializable {

    private Integer rowNumber;

    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "产品名称")
    private String productName;
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "访问次数")
    private String visitCount;

}
