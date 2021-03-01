package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: EvaluateInfoBean
 * @date 2019/6/10 16:43
 * @projectName file-manage
 * @description: 评价信息
 */
@Data
public class EvaluateInfoBean extends BasicBean implements Serializable {

    /**
     * @Description 行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 18:13 2019/6/13
     **/
    private Integer rowNumber;

    /**
     * @Description 评价日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:44 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "评价日期")
    private Date reviewDate;

    /**
     * @Description 店铺名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:45 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "店铺")
    private String shopName;

    /**
     * @Description 评价总数
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:45 2019/6/10
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "评价总数")
    private Integer evaluateNum;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;

}
