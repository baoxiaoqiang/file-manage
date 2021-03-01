package cn.com.quanyou.ioc.file.manage.vo;

import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.SaleTargetTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ServiceBellwetherEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SearchParamVo
 * @date 2019/6/20 13:19
 * @projectName file-manage
 * @description: 查询参数VO
 */
@Data
public class SearchParamVo implements Serializable {

    /**
     * @Description 查询类型，见枚举：ImportFileTypeEnum.type
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:35 2019/6/20
     **/
    private String searchGroup;

    /**
     * @Description 开始时间：运营数据\运费\差评信息\服务风向\售前\售中
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:20 2019/6/20
     **/
    private Date startDate;

    /**
     * @Description 结束时间：运营数据\差评信息\服务风向\售前\售中
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:21 2019/6/20
     **/
    private Date endDate;

    /**
     * @Description 风向标类型：服务风向
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:33 2019/6/20
     **/
    private String weatherType;

    /**
     * @Description 店铺：运营数据\各省销售\销售目标\运费\差评信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:21 2019/6/20
     **/
    private List<String> shopName;

    /**
     * @Description 省份：各省销售
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:14 2019/6/21
     **/
    private List<String> provinces;

    /**
     * @Description 年份：各省销售\销售目标\运费\处罚信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:22 2019/6/20
     **/
    private Integer year;

    /**
     * @Description 月份：各省销售\运费\处罚信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:23 2019/6/20
     **/
    private Integer month;

    /**
     * @Description 销售目标月份
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:25 2019/6/21
     **/
    private List<Integer> months;

    /**
     * @Description 目标类型：销售目标（页面选择：见枚举：SaleTargetTypeEnum）
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:23 2019/6/20
     **/
    private String targetType;

    /**
     * @Description 办事处：差评信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:29 2019/6/20
     **/
    private List<String> agency;

    /**
     * @Description 产品类型：差评信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:31 2019/6/20
     **/
    private List<String> productType;

    /**
     * @Description 问题类型：差评信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:31 2019/6/20
     **/
    private List<String> problemType;

    /**
     * @Description 问题细分：差评信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:32 2019/6/20
     **/
    private List<String> problemTypeSub;

    /**
     * @Description  销售目标的时间列表，里面的list中，第一个是开始时间，第二个是结束时间。此列表是根据所选择的年份和月份list获取的。
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:48 2019/6/21
     **/
    List<List<Date>> dates;

    public Date getStartDate() {
        if(this.getSearchType() == null){
            return null;
        }
        if(ImportFileTypeEnum.punishInfo.getType().equals(this.getSearchType().getType())){
            //处罚
            return this.getPunishStartDate();
        }
        return startDate;
    }

    public Date getEndDate() {
        if(this.getSearchType() == null){
            return null;
        }
        if(ImportFileTypeEnum.punishInfo.getType().equals(this.getSearchType().getType())){
            //处罚
            return this.getPunishEndDate();
        }
        return endDate;
    }


    public List<List<Date>> getDates() {
        if(this.year == null){
            return null;
        }
        List<List<Date>> list = new ArrayList<>();
        if(months == null || months.size() ==0){
            List<Date> dates = new ArrayList<>();
            dates.add(DateUtil.parseDate(year+""));
            dates.add(DateUtil.parseDate((year+1)+""));
            list.add(dates);
        }else{
            for(int m:months){
                List<Date> dates = new ArrayList<>();
                dates.add(DateUtil.parseDate(year+""+m));
                dates.add(this.getMonthEnd(year,m));
                list.add(dates);
            }
        }

        return list;
    }

    private Date getPunishStartDate() {
        if(this.year == null){
            return null;
        }
        if(this.month == null){
            return DateUtil.parseDate(year+"");
        }else{
            return DateUtil.parseDate(year+""+month);
        }
    }

    private Date getPunishEndDate() {
        if(this.year == null){
            return null;
        }
        if(this.month == null){
            //整年
            return DateUtil.parseDate((year+1)+"");
        }else{
            //整月
            return this.getMonthEnd(year,month);
        }
    }

    private Date getMonthEnd(int year,int month){
        Date date = DateUtil.parseDate(year+""+month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        return calendar.getTime();
    }

    /***
    * @Description  获取查询的真正类型。
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:13 2019/6/20
    * @param
    * @return
    **/
    public ImportFileTypeEnum getSearchType(){

        if(this.searchGroup == null){
            return null;
        }
        if(this.searchGroup.equals(ImportFileTypeEnum.saleTargetMonth.getGroup())){
            if(SaleTargetTypeEnum.targetMonth.getCode().equals(this.targetType)){
                //月度立项目标
                return ImportFileTypeEnum.saleTargetMonth;
            }
            if(SaleTargetTypeEnum.targetYear.getCode().equals(this.targetType)){
                //月度立项目标
                return ImportFileTypeEnum.saleTargetYear;
            }
        }else if(this.searchGroup.equals(ImportFileTypeEnum.badEvaluateDetail.getGroup())){
            return ImportFileTypeEnum.badEvaluateDetail;
        }else if(this.searchGroup.equals(ImportFileTypeEnum.serviceBellwetherTaobao.getGroup())){
            if(ServiceBellwetherEnum.taobao.getCode().equals(this.weatherType)){
                return ImportFileTypeEnum.serviceBellwetherTaobao;
            }else if(ServiceBellwetherEnum.jd.getCode().equals(this.weatherType)){
                return ImportFileTypeEnum.serviceBellwetherJD;
            }
        }else{
            return ImportFileTypeEnum.getByGroup(this.searchGroup);
        }
        return null;
    }

}
