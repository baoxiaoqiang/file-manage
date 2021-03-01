package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ImportBasicService
 * @date 2019/6/20 14:20
 * @projectName file-manage
 * @description:
 */
@Service
public class ImportBasicService {

    @Autowired
    private IExcelImportService<OperationalDataBean> operationalDataImportService;

    @Autowired
    private IExcelImportService<EvaluateInfoBean> evaluateInfoService;

    @Autowired
    private IExcelImportService<SaleInfoBean> saleInfoService;

    @Autowired
    private IExcelImportService<EvaluateNegativeDetailBean> evaluateNegDetailService;
    @Autowired
    private IExcelImportService<LogisticsBean> logisticsService;
    @Autowired
    private IExcelImportService<PunishInfoBean> punishInfoService;
    @Autowired
    private IExcelImportService<SaleTargetMonthBean> saleTargetMonthService;
    @Autowired
    private IExcelImportService<SaleTargetYearBean> saleTargetYearService;
    @Autowired
    private IExcelImportService<ServiceBellwetherJDBean> serviceBellwetherJDService;
    @Autowired
    private IExcelImportService<ServiceBellwetherTaobaoBean> serviceBellwetherTaobaoService;
    @Autowired
    private IExcelImportService<ServiceSaleBeforeBean> serviceSaleBeforeService;
    @Autowired
    private IExcelImportService<PaySuccessRateBean> saleBeforePaySuccessRateService; // 最终付款成功率服务
    @Autowired
    private IExcelImportService<ServiceSalingBean> serviceSalingService;
    @Autowired
    private IExcelImportService<ShopBasicInfoBean> shopBasicInfoService;
    @Autowired
    private IExcelImportService<TransportationCostsBean> transportationCostsService;
    @Autowired
    private IExcelImportService<GovStoreSaleTi> govStoreSaleService;
    @Autowired
    private IExcelImportService<GovProductTopService> govProductTopService;

    @Autowired
    private IExcelImportService<GovTopPlatformTi> govTopPlatFormService;
    @Autowired
    private IExcelImportService<PutAwayTi> newProductExploitService;
    @Autowired
    private IExcelImportService<PutAwayTiForShootBatch> newProductShootBatchService;
    @Autowired
    private IExcelImportService<CdsProcessPieceTi> processPieceDetailService;
    @Autowired
    private IExcelImportService<B2CProductRefundTi> b2cProductRefundService;
    @Autowired
    private IExcelImportService<B2CProductDimTi> b2cProductDimService;
    @Autowired
    private IExcelImportService<PutAwayTiForTaskStatus> newProductBatchModifyService;
    @Autowired
    private IExcelImportService<ImportNewProductBasicTi> newProductBasicService;
    @Autowired
    private IExcelImportService<LogisticsAgingTi> logisticsAgingService;
    @Autowired
    private IExcelImportService<ImportNewProductTime2RemarkTi> newProductTime2RemarkService;
    @Autowired
    private IExcelImportService<CdsCupboardComponentTi> cdsCupboardService;
    @Autowired
    private IExcelImportService<CdsWardrobeComponentTi> cdsWardrobeService;
    @Autowired
    private IExcelImportService<ImportNewStoreInfo> storeInfoService;

    /**
     * @param typeEnum
     * @return
     * @Description 根据枚举，获取servcie
     * @Author heshiyi@quanyou.com.cn
     * @Date 14:25 2019/6/20
     **/
    protected IExcelImportService getServivceByFileImportTypeEnum(ImportFileTypeEnum typeEnum) {
        if (typeEnum == null) {
            return null;
        }
        switch (typeEnum) {
            //运营数据
            case operatData:
                return operationalDataImportService;
            //评价信息
            case evaluateInfo:
                return evaluateInfoService;
            case saleInfo:
                return saleInfoService;
            case saleTargetMonth:
                return saleTargetMonthService;
            case saleTargetYear:
                return saleTargetYearService;
            case transportationCosts:
                return transportationCostsService;
            case logistics:
                return logisticsService;
            case shopBasicInfo:
                return shopBasicInfoService;
            case badEvaluateDetail:
                return evaluateNegDetailService;
            case punishInfo:
                return punishInfoService;
            case serviceBellwetherTaobao:
                return serviceBellwetherTaobaoService;
            case serviceBellwetherJD:
                return serviceBellwetherJDService;
            case saleBeforeService:
                return serviceSaleBeforeService;
            case saleBeforePaySuccessRate:
                return saleBeforePaySuccessRateService;
            case salingService:
                return serviceSalingService;
            case govStoreSaleService:
                return govStoreSaleService;
            case govProductTopService:
                return govProductTopService;
            case govTopPlatFormService:
                return govTopPlatFormService;
            case newProductExploitService:
                return newProductExploitService;
            case newProductShootBatchService:
                return newProductShootBatchService;
            case processPieceDetailService:
                return processPieceDetailService;
            case b2cProductRefundService:
                return b2cProductRefundService;
            case b2cProductDimService:
                return b2cProductDimService;
            case newProductBatchModifyService:
                return newProductBatchModifyService;
            case newProductBasicService:
                return newProductBasicService;
            //友商物流时效
            case logisticsAgingService:
                return logisticsAgingService;
            //新品上新和备注
            case newProductTime2RemarkService:
                return newProductTime2RemarkService;
            //cds定制橱柜组件导入
            case cdsCupboardService:
                return cdsCupboardService;
            //cds定制衣柜组件导入
            case cdsWardrobeService:
                return cdsWardrobeService;
            //门店信息维护
            case storeInfoService:
                return storeInfoService;

            default:
                return null;
        }
    }

}
