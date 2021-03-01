package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 门店信息维护导入表
 * @Author yhl
 * @Date 2020-04-21
**/
@Data
@ApiModel("门店信息维护导入表")
@Table(name = "IMPORT_CDS_DIM_SHOP_TI")
public class ImportNewStoreInfo extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author yhl@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 门店编码
     * @Author yhl@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "门店编码",nullAble = true)
    private String shopCode;

    /**
     * @Description 门店名称
     * @Author yhl@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "门店名称")
    private String shopName;

    /**
     * @Description 业务类型名称
     * @Author yhl@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "业务类型")
    private String businessTypeName;

    /**
     * @Description 门店类型名称(店中店)
     * @Author yhl@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "门店类型",nullAble = true)
    private String shopTypeName;

    /**
     * @Description 定制业务账号编码
     * @Author yhl@quanyou.com.cn
     * @Date 9:39 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "定制业务账号编码",nullAble = true)
    private String cdsAccountCode;

    /**
     * @Description 办事处名称
     * @Author yhl@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "办事处")
    private String agencyName;

    /**
     * @Description 城市级别(地级)
     * @Author yhl@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "城市级别",nullAble = true)
    private String cityLevelName;
    /**
     * @Description 省份
     * @Author yhl@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "省份",nullAble = true)
    private String provinceName;

    /**
     * @Description 地市
     * @Author yhl@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "地市",nullAble = true)
    private String cityName;

    /**
     * @Description 区/县
     * @Author yhl@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "区/县",nullAble = true)
    private String districtName;

    /**
     * @Description 街道名称
     * @Author yhl@quanyou.com.cn
     * @Date 9:40 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "街道/镇",nullAble = true)
    private String streetName;


    /**
     * @Description 衣柜面积(㎡)
     * @Author yhl@quanyou.com.cn
     * @Date 9:41 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "衣柜面积(㎡)",nullAble = true)
    private String shopWardrobeArea;

    /**
     * @Description 橱柜面积(㎡)
     * @Author yhl@quanyou.com.cn
     * @Date 9:42 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "橱柜面积(㎡)",nullAble = true)
    private String shopCabinetArea;

    /**
     * @Description 卫浴面积(㎡)
     * @Author yhl@quanyou.com.cn
     * @Date 9:42 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "卫浴面积(㎡)",nullAble = true)
    private String shopSanitaryWareArea;

    /**
     * @Description 样板间
     * @Author yhl@quanyou.com.cn
     * @Date 9:42 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "样板间",nullAble = true)
    private String shopShowroomArea;

    /**
     * @Description 样板间业务类型(定制)
     * @Author yhl@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "样板间业务类型",nullAble = true)
    private String showroomTypeName;
    /**
     * @Description 门店状态(激活、停用)
     * @Author yhl@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 16,headerName = "门店状态(激活、停用)",nullAble = true)
    private String shopStatus;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author yhl@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
