package cn.com.quanyou.ioc.file.manage.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ImportFileTypeEnum
 * @date 2019/6/109:23
 * @projectName file-manage
 * @description: 文件导入类型枚举
 */
public enum ImportFileTypeEnum {

    /**
     * @Description 运营数据
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:27 2019/6/10
     **/
    operatData("operatData","运营数据", UploadFileTypeEnum.importExcelTemplate,1,"operatDataGroup"),
    saleInfo("saleInfo","各省销售情况",UploadFileTypeEnum.importExcelTemplate,1,"saleInfoGroup"),
    saleTargetMonth("saleTargetMonth","月度立项销售目标",UploadFileTypeEnum.importExcelTemplate,1,"saleTargetGroup"),
    saleTargetYear("saleTargetYear","年度立项销售目标",UploadFileTypeEnum.importExcelTemplate,1,"saleTargetGroup"),
    transportationCosts("transportationCosts","运费",UploadFileTypeEnum.importExcelTemplate,1,"transportationCostsGroup"),
    logistics("logistics","物流配送",UploadFileTypeEnum.importExcelTemplate,1,"logisticsGroup"),
    evaluateInfo("evaluateInfo","评价信息",UploadFileTypeEnum.importExcelTemplate,1,"evaluateInfoGroup"),
    badEvaluateDetail("badEvaluateDetail","差评详情",UploadFileTypeEnum.importExcelTemplate,1,"evaluateInfoGroup"),
    punishInfo("punishInfo","处罚信息",UploadFileTypeEnum.importExcelTemplate,1,"punishInfoGroup"),
    serviceBellwetherTaobao("serviceBellwetherTaobao","淘系风向标",UploadFileTypeEnum.importExcelTemplate,2,"serviceBellwetherGroup"),
    serviceBellwetherJD("serviceBellwetherJD","京东风向标",UploadFileTypeEnum.importExcelTemplate,1,"serviceBellwetherGroup"),
    saleBeforeService("saleBeforeService","售前服务",UploadFileTypeEnum.importExcelTemplate,1,"saleBeforeServiceGroup"),
    saleBeforePaySuccessRate("saleBeforePaySuccessRate","最终付款成功率",UploadFileTypeEnum.importExcelTemplate,1,"saleBeforeServiceGroup"),
    salingService("salingService","售中服务",UploadFileTypeEnum.importExcelTemplate,1,"salingServiceGroup"),
    shopBasicInfo("shopBasicInfo","服务账号与员工配置",UploadFileTypeEnum.importExcelTemplate,1,"shopBasicInfoGroup"),
    govStoreSaleService("govStoreSaleService","各店运营数据-政府",UploadFileTypeEnum.importExcelTemplate,1,"govStoreSaleGroup"),
    govProductTopService("govProductTopService","产品访问次数",UploadFileTypeEnum.importExcelTemplate,1,"govProductTopGroup"),
    govTopPlatFormService("govTopPlatFormService","各平台渠道访客TOP5",UploadFileTypeEnum.importExcelTemplate,1,"govTopPlatFormGroup"),
    newProductExploitService("newProductExploitService","新品上架批量新增",UploadFileTypeEnum.importExcelTemplate,1,"newProductExploitGroup"),
    newProductShootBatchService("newProductShootBatchService","拍摄批次导入",UploadFileTypeEnum.importExcelTemplate,1,"newProductShootBatchGroup"),
    processPieceDetailService("processPieceDetailService","工序计件明细导入",UploadFileTypeEnum.importExcelTemplate,1,"processPieceDetailGroup"),
    b2cProductRefundService("b2cProductRefundService","成品退款明细",UploadFileTypeEnum.importExcelTemplate,1,"b2cProductRefundGroup"),
    b2cProductDimService("b2cProductDimService","产品维护数据",UploadFileTypeEnum.importExcelTemplate,1,"b2cProductDimGroup"),
    newProductBatchModifyService("newProductBatchModifyService","批量修改流程节点",UploadFileTypeEnum.importExcelTemplate,1,"newProductBModifyGroup"),
    newProductBasicService("newProductBasicService","新品基础信息导入",UploadFileTypeEnum.importExcelTemplate,1,"newProductBasicGroup"),
    logisticsAgingService("logisticsAgingService","友商物流",UploadFileTypeEnum.importExcelTemplate,1,"logisticsAgingGroup"),
    newProductTime2RemarkService("newProductTime2RemarkService","上新时间和备注",UploadFileTypeEnum.importExcelTemplate,1,"newProductTime2RemarkGroup"),
    cdsCupboardService("cdsCupboardService","定制橱柜组件导入",UploadFileTypeEnum.importExcelTemplate,1,"cdsCupboardGroup"),
    cdsWardrobeService("cdsWardrobeService","定制衣柜组件导入",UploadFileTypeEnum.importExcelTemplate,1,"cdsWardrobeGroup"),
    storeInfoService("storeInfoService","门店信息",UploadFileTypeEnum.importExcelTemplate,1,"storeInfoGroup"),
    ;

    /**
    * @Description 该子项所属的group,是否含有多个子项
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:08 2019/6/24
    * @param type
    * @return
    **/
    public static boolean groupHasMoreSub(String type){
        if(StringUtils.isBlank(type)){
            return false;
        }
        if(saleTargetMonth.getType().equals(type)
                || saleTargetYear.getType().equals(type)
                || evaluateInfo.getType().equals(type)
                || badEvaluateDetail.getType().equals(type)
                || serviceBellwetherTaobao.getType().equals(type)
                || serviceBellwetherJD.getType().equals(type)
                || saleBeforeService.getType().equals(type)
                || saleBeforePaySuccessRate.getType().equals(type)
        ){
            return true;
        }
        return false;
    }

    /**
     * @Description 类型,采用字符串，前后台传递，增加可读性
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:24 2019/6/10
     **/
    private final String type;

    /**
     * @Description 分组信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:45 2019/6/17
     **/
    private final String group;

    /**
     * @Description 说明
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:24 2019/6/10
     **/
    private final  String description;

    /**
     * @Description 导入数据开始行数，第一行是0
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:35 2019/6/17
     **/
    private final Integer dataStartRow;

    /**
     * @Description 文件类枚举
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:42 2019/6/10
     **/
    private final UploadFileTypeEnum uploadfileType;

    /**
    * @Description 是否有正确的导入类型
    * @Author heshiyi@quanyou.com.cn
    * @Date 11:42 2019/6/12
    * @param type 导入类型标识
    * @return
    **/
    public static boolean hasCorrentType(String type){
        for(ImportFileTypeEnum fileTypeEnum : ImportFileTypeEnum.values()){
            if(fileTypeEnum.getType().equals(type)){
                return true;
            }
        }

        return false;
    }

    /**
    * @Description 是否正确的分组
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:59 2019/6/21
    * @param group
    * @return
    **/
    public static boolean hasCorrentGroup(String group){
        for(ImportFileTypeEnum fileTypeEnum : ImportFileTypeEnum.values()){
            if(fileTypeEnum.getGroup().equals(group)){
                return true;
            }
        }

        return false;
    }

    /**
    * @Description 根据描述名称，获取导入类型信息。
    * @Author heshiyi@quanyou.com.cn
    * @Date 11:40 2019/6/15
    * @param description
    * @return
    **/
    public static ImportFileTypeEnum getByDescription(String description){
        if(StringUtils.isBlank(description)){
            return null;
        }
        for( ImportFileTypeEnum importFileTypeEnum : ImportFileTypeEnum.values()){
            if(importFileTypeEnum.getDescription().equals(description)){
                return importFileTypeEnum;
            }
        }
        return null;
    }

    /**
    * @Description 根据group，返回枚举，如果有多个，则返回第一个找到的
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:13 2019/6/20
    * @param group
    * @return
    **/
    public static ImportFileTypeEnum getByGroup(String group){
        if(StringUtils.isBlank(group)){
            return null;
        }
        for( ImportFileTypeEnum importFileTypeEnum : ImportFileTypeEnum.values()){
            if(importFileTypeEnum.getGroup().equals(group)){
                return importFileTypeEnum;
            }
        }
        return null;
    }


    private ImportFileTypeEnum(String type,String description,UploadFileTypeEnum uploadfileType,Integer dataStartRow,String group){
        this.type = type;
        this.description = description;
        this.uploadfileType = uploadfileType;
        this.dataStartRow = dataStartRow;
        this.group = group;
    }

    /**
    * @Description 根据导入类型，获取文件信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:05 2019/6/10
    * @param type 导入类型字符串标识
    * @return 导入类型枚举
    **/
    public static ImportFileTypeEnum getByType(String type){
        if(StringUtils.isBlank(type)){
            return null;
        }
        for( ImportFileTypeEnum importFileTypeEnum : ImportFileTypeEnum.values()){
            if(importFileTypeEnum.getType().equals(type)) {
                return importFileTypeEnum;
            }
        }
        return null;
    }

    /**
    * @Description 根据导入类型字符串标识，获取上传文件类型
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:06 2019/6/10
    * @param type 导入类型字符串标识
    * @return   上传类型
    **/
    public Integer getUploadFileTypeByType(String type){
        ImportFileTypeEnum importFileTypeEnum = getByType(type);
        if(importFileTypeEnum == null){
            return null;
        }

        return importFileTypeEnum.getUploadfileType().getFileType();
    }

    public String getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public UploadFileTypeEnum getUploadfileType() {
        return uploadfileType;
    }

    public Integer getDataStartRow() {
        return dataStartRow;
    }}
