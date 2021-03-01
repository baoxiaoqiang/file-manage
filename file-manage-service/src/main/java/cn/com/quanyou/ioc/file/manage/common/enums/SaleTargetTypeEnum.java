package cn.com.quanyou.ioc.file.manage.common.enums;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: SaleTargetTypeEnum
 * @date 2019/6/20 14:01
 * @projectName file-manage
 * @description: 立项目标类型
 */
public enum SaleTargetTypeEnum {
    /**
     * @Description  月度立项目标
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    targetMonth("targetMonth","月度立项目标"),

    /**
     * @Description  年度立项目标
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:06 2019/6/5
     **/
    targetYear("targetYear","年度立项目标"),
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


    private SaleTargetTypeEnum(String code , String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
