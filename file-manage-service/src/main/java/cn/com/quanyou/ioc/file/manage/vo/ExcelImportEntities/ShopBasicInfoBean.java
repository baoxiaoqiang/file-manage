package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ShopBasicInfoBean
 * @date 2019/6/11 10:28
 * @projectName file-manage
 * @description: 店铺账号员工信息
 */
@Data
public class ShopBasicInfoBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 店铺
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:31 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "店铺")
    private String shopName;

    /**
     * @Description 账号名
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:32 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "账号名",maxLength = 40)
    private String userName;

    /**
     * @Description 员工姓名
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:32 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "员工姓名",maxLength = 40)
    private String staffName;

    /**
     * @Description 工号
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:32 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "工号",maxLength = 40)
    private String jobNumber;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;
}
