package cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities;

import java.util.Date;
import lombok.Data;
import javax.persistence.Table;
import javax.persistence.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description IMPORT_B2C_PRODUCT_REFUND_T 
 * @Author bxq
 * @Date 2019-11-25 
**/
@Data
@ApiModel("IMPORT_B2C_PRODUCT_REFUND_T")
@Table(name = "IMPORT_B2C_PRODUCT_REFUND_T")
public class B2CProductRefund {

    @Column(name = "DATA_ID")
    private String dataId;
    @Column(name = "SHOP_NAME")
    private String shopName;
    @Column(name = "AGENCY")
    private String agency;
    @Column(name = "PLATFORM_NO")
    private String platformNo;
    @Column(name = "ORDER_TIME")
    private Date orderTime;
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "RECEIVING_ADDRESS")
    private String receivingAddress;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "REFUND_COUNT")
    private String refundCount;
    @Column(name = "REFUNDABLE_AMOUNT")
    private String refundableAmount;
    @Column(name = "REFUND_APPLICATION_AMOUNT")
    private String refundApplicationAmount;
    @Column(name = "REFUND_REASON")
    private String refundReason;
    @Column(name = "REFUND_NATURE")
    private String refundNature;
    @Column(name = "SUBJECT_TOLIABILITY")
    private String subjectToliability;
    @Column(name = "RESULTS")
    private String results;
    @Column(name = "PROCESS_DATE")
    private Date processDate;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

}
