package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import java.util.Date;
import lombok.Data;
import javax.persistence.Table;
import javax.persistence.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description 店铺流量以及服务数据上报接口表 
 * @Author yangli
 * @Date 2019-09-09 
**/
@Data
@ApiModel("店铺流量以及服务数据上报接口表")
@Table(name = "IMPORT_GOV_STORE_SERVICE_TI")
public class GovStoreServiceTi {

    @ApiModelProperty(value = "主键ID")
    @Column(name = "DATA_ID")
    private String dataId;
    @ApiModelProperty(value = "日期")
    @Column(name = "SALE_DATE")
    private String saleDate;
    @ApiModelProperty(value = "店铺名称")
    @Column(name = "SHOP_NAME")
    private String shopName;
    @ApiModelProperty(value = "咨询客服人数")
    @Column(name = "COUNSELLING_BUYER_COUNT")
    private String counsellingBuyerCount;
    @ApiModelProperty(value = "客服回复率")
    @Column(name = "REPLY_RATIO")
    private String replyRatio;
    @ApiModelProperty(value = "浏览量")
    @Column(name = "VIEW_COUNT")
    private String viewCount;
    @ApiModelProperty(value = "总访客数")
    @Column(name = "TOTAL_VISITOR_COUNT")
    private String totalVisitorCount;
    @ApiModelProperty(value = "免费渠道访客数")
    @Column(name = "CHANNEL_FREE_VISITOR_COUNT")
    private String channelFreeVisitorCount;
    @ApiModelProperty(value = "付费渠道访客数")
    @Column(name = "CHANNEL_PAY_VISITOR_COUNT")
    private String channelPayVisitorCount;
    @ApiModelProperty(value = "自助渠道访客数")
    @Column(name = "CHANNEL_SELF_VISITOR_COUNT")
    private String channelSelfVisitorCount;
    @ApiModelProperty(value = "其他渠道访客数")
    @Column(name = "CHANNEL_OTHER_VISITOR_COUNT")
    private String channelOtherVisitorCount;
    @ApiModelProperty(value = "行数，第一行为1")
    @Column(name = "ROW_NUMBER")
    private String rowNumber;
    @ApiModelProperty(value = "任务ID")
    @Column(name = "TASK_ID")
    private String taskId;
    @ApiModelProperty(value = "验证结果，见枚举：ValidStatusEnum")
    @Column(name = "VALID_STATUS")
    private String validStatus;
    @ApiModelProperty(value = "上报日期，数据按上报日期全量更新")
    @Column(name = "REPORT_DATE")
    private String reportDate;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

}
