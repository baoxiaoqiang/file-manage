package cn.com.quanyou.ioc.file.manage.common.enums;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ResultStatusEnum
 * @date 2019/6/511:02
 * @projectName file-manage
 * @description:
 */
public enum ResultStatusEnum {

    /**
     * @Description  成功
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    success("200","成功"),

    /**
     * @Description  失败
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:06 2019/6/5
     **/
    failed("201","失败"),

    /**
     * @Description 警告
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:27 2019/6/6
     **/
    warning("203","警告"),

    /**
     * @Description 已经上传
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:41 2019/6/14
     **/
    alreadyUpload("204","已经上传"),
    ;

    /**
     * @Description 结果信息编码
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    private String code;

    /**
     * @Description  结果信息说明
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:05 2019/6/5
     **/
    private String description;

    public boolean isSuccess(){

        return ResultStatusEnum.success.getCode().equals(this.getCode()) ? true:false;
    }

    public boolean isFail(){

        return ResultStatusEnum.failed.getCode().equals(this.getCode()) ? true:false;
    }

    public boolean isWarning(){
        return ResultStatusEnum.warning.getCode().equals(this.getCode()) ? true:false;
    }

    private ResultStatusEnum(String code , String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }}
