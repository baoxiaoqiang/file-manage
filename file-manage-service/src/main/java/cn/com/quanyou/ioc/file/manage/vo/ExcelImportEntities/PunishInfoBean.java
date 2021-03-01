package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: PunishInfoBean
 * @date 2019/6/10 16:49
 * @projectName file-manage
 * @description: 处罚信息
 */
@Data
public class PunishInfoBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:51 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "日期")
    private Date punishDate ;

    /**
     * @Description 省
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:52 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "省")
    private String province;

    /**
     * @Description 店铺名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:52 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "店铺")
    private String shopName;

    /**
     * @Description 处罚金额
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:52 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "处罚金额")
    private BigDecimal punishMoney;

    /**
     * @Description 是否通报
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:53 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "是否通报",strRang = {"是","否"})
    private String isBulletion;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
