package cn.com.quanyou.ioc.file.manage.common.enums;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SaleTargetTypeEnum
 * @date 2019/6/20 14:01
 * @projectName file-manage
 * @description: 风向标类型
 */
public enum ServiceBellwetherEnum {
    /**
     * @Description  京东
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    taobao("taobao","淘系"),

    /**
     * @Description  淘宝
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:06 2019/6/5
     **/
    jd("jd","京东"),
    ;

    /**
     * @Description 编码
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    private String code;

    /**
     * @Description  说明
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    private String description;


    private ServiceBellwetherEnum(String code , String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
