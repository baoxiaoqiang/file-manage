package cn.com.quanyou.ioc.file.manage.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ResultStatusEnum
 * @date 2019/6/511:02
 * @projectName file-manage
 * @description:
 */
public enum ProcessResultEnum {


    /**
     * @Description 失败
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/12
     **/
    fail(0,"失败"),

    /**
     * @Description 成功
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:27 2019/6/6
     **/
    success(1,"成功"),

    /**
     * @Description 部分失败
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:26 2019/6/12
     **/
    failPart(2,"部分失败"),
    ;

    /**
     * @Description 结果信息编码
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    private Integer code;

    /**
     * @Description  结果信息说明
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    private String description;

    public static ProcessResultEnum getByName(String name ){
        if(StringUtils.isBlank(name)){
            return null;
        }
        for(ProcessResultEnum result : ProcessResultEnum.values()){
            if(result.name().equals(name)){
                return result;
            }
        }
        return null;
    }

    private ProcessResultEnum(Integer code , String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
