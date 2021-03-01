package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import java.io.Serializable;
import java.util.Date;

import cn.com.quanyou.ioc.file.manage.vo.BasicBean;
import lombok.Data;
import javax.persistence.Table;
import javax.persistence.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description 店铺商品销售数据上报表 
 * @Author yangli
 * @Date 2019-09-09 
**/
@Data
@ApiModel("店铺商品销售数据上报表")
@Table(name = "IMPORT_GOV_STORE_SALE_T")
public class GovStoreSale extends BasicBean implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "DATA_ID")
    private String dataId;
    @ApiModelProperty(value = "日期")
    @Column(name = "SALE_DATE")
    private Date saleDate;
    @ApiModelProperty(value = "省份名称")
    @Column(name = "PROVINCE")
    private String province;
    @ApiModelProperty(value = "店铺名称")
    @Column(name = "SHOP_NAME")
    private String shopName;
    @ApiModelProperty(value = "订单号")
    @Column(name = "ORDER_NUMBER")
    private String orderNumber;
    @ApiModelProperty(value = "顾客ID")
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @ApiModelProperty(value = "商品名称")
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @ApiModelProperty(value = "购买数量")
    @Column(name = "BUY_COUNT")
    private String buyCount;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

}
