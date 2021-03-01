package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import java.io.Serializable;
import java.util.Date;

import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;

/**
 * 最终付款成功率
 * @Author  yhy
 * @Date 2019-07-19 
 */
@Data
public class PaySuccessRateBean extends BasicBean implements Serializable {

 /**
  * @Description 行数
  **/
 private Integer rowNumber;
 /**
  * 日期
  */
 @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 0,headerName = "日期")
  private Date saleDate;
 /**
  * 店铺
  */
 @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 1,headerName = "店铺")
  private String shopName;

 /**
  * 账户名
  */
 @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 2,headerName = "账户名",maxLength = 255)
 private String userName;

 /**
  * 询单->最终付款成功率
  */
 @ExcelModelAnnotation.FieldAnnotation(excelCellIndex = 3,headerName = "询单->最终付款成功率",maxBig = 1,nullAble = true)
 private String askPayRate;

 /**
  * @Description 上报时间，按日期全量更新
  * @Author heshiyi@quanyou.com.cn
  * @Date 10:21 2019/6/18
  **/
 private Date reportDate;

}
