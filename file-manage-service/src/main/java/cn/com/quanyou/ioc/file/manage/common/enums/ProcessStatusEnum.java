package cn.com.quanyou.ioc.file.manage.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ResultStatusEnum
 * @date 2019/6/511:02
 * @projectName file-manage
 * @description:
 */
public enum ProcessStatusEnum {


    /**
     * @Description 待解析
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:25 2019/6/12
     **/
    waitAnalysis(0,"待解析"),

    /**
     * @Description 解析中
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:27 2019/6/6
     **/
    analysising(1,"解析中"),

    /**
     * @Description 解析完成
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:26 2019/6/12
     **/
    analysisFinished(2,"解析完成"),

    /**
     * @Description 同步完成
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:32 2019/6/22
     **/
    synFinished(3,"同步完成"),

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

    private ProcessStatusEnum(Integer code , String description){
        this.code = code;
        this.description = description;
    }

    public static ProcessStatusEnum getByName(String name ){
        if(StringUtils.isBlank(name)){
            return null;
        }
        for(ProcessStatusEnum result : ProcessStatusEnum.values()){
            if(result.name().equals(name)){
                return result;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
