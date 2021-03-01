package cn.com.quanyou.ioc.file.manage.common.enums;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ValidStatusEnum
 * @date 2019/6/417:45
 * @projectName file-manage
 * @description:
 */
public enum ValidStatusEnum {

    unValid(0,"待验证"),

    success(1,"有效"),

    fail(-1,"有误"),

    ;

    /**
     * @Description 有效状态编码
     * @Date 17:46 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    private Integer code;

    /**
     * @Description  有效状态说明
     * @Date 17:46 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    private String description;

    ValidStatusEnum(Integer code,String description){
        this.code = code;
        this.description =description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
