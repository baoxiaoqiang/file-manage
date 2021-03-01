package cn.com.quanyou.ioc.file.manage.common.enums;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ResultStatusEnum
 * @date 2019/6/511:02
 * @projectName file-manage
 * @description: 验证状态信息
 */
public enum ValidateStatusEnum {


    /**
     * @Description 等待验证
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:33 2019/6/18
     **/
    wait(0,"待解析"),


    /**
     * @Description 验证成功
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:33 2019/6/18
     **/
    success(1,"解析中"),

    /**
     * @Description 验证失败
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:32 2019/6/18
     **/
    faile(-1,"解析完成"),
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

    private ValidateStatusEnum(Integer code , String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
