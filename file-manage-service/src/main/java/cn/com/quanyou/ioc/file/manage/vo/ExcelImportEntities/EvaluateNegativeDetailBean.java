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
 * @description: 差评信息
 */
@Data
public class EvaluateNegativeDetailBean extends BasicBean implements Serializable {

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
    private Date evaluateDate;

    /**
     * @Description 受理日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:51 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "受理日期",nullAble = true)
    private Date acceptDate;

    /**
     * @Description 订单号
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "订单号",maxLength = 40)
    private String orderNumber;

    /**
     * @Description 店铺
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:59 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "店铺")
    private String shopName;

    /**
     * @Description 差评
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:59 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 4,headerName = "差评",maxLength = 40,nullAble = true)
    private String negativeEvaluate;

    /**
     * @Description 顾客ID
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:59 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 5,headerName = "顾客ID",maxLength = 100,nullAble = true)
    private String customerId;

    /**
     * @Description 反馈内容
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:59 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 6,headerName = "反馈内容",maxLength = 2000,nullAble = true)
    private String feedbackContent;

    /**
     * @Description 回复内容
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:59 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 7,headerName = "回复内容",maxLength = 2000,nullAble = true)
    private String replyContent;

    /**
     * @Description 办事处
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:00 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 8,headerName = "办事处",maxLength = 40,nullAble = true)
    private String sname;

    /**
     * @Description 顾客信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:00 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 9,headerName = "顾客信息",maxLength = 2000,nullAble = true)
    private String customerInfo;

    /**
     * @Description 专卖店
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:00 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 10,headerName = "专卖店",maxLength = 255,nullAble = true)
    private String exclusiveShop;

    /**
     * @Description 产品类型
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:00 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 11,headerName = "产品类型",maxLength = 255,nullAble = true)
    private String productType;

    /**
     * @Description 型号
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:00 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 12,headerName = "型号",maxLength = 255,nullAble = true)
    private String productModel;

    /**
     * @Description 问题类型
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 13,headerName = "问题类型",maxLength = 255)
    private String problemType;

    /**
     * @Description 类型细分
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 14,headerName = "类型细分",maxLength = 255)
    private String problemTypeSubdivide;

    /**
     * @Description 处理结果
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 15,headerName = "处理结果",maxLength = 1000,nullAble = true)
    private String handleResult;

    /**
     * @Description 顾客原因
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 16,headerName = "顾客原因",strRang = {"是","否"},nullAble = true)
    private String customerReason;

    /**
     * @Description 跟进时间
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 17,headerName = "跟进时间",nullAble = true)
    private String followDate;

    /**
     * @Description 处理状态
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 18,headerName = "处理状态",maxLength = 255,nullAble = true)
    private String handleStatus;

    /**
     * @Description 处理人
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:01 2019/6/11
     **/
    @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 19,headerName = "处理人",maxLength = 40,nullAble = true)
    private String handleUser;

    /**
     * @Description 上报时间，按日期全量更新
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:21 2019/6/18
     **/
    private Date reportDate;
}
