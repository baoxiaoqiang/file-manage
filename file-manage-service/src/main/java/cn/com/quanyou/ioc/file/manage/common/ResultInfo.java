package cn.com.quanyou.ioc.file.manage.common;

import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ResultInfo
 * @date 2019/6/511:00
 * @projectName file-manage
 * @description:
 */
public class ResultInfo<T,E> {


//    public ResultInfo(T t){
//        this.data = t;
//    }
    public ResultInfo(){

    }

    /**
     * @Description  结果返回的数据
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:27 2019/6/5
     **/
    private T data;

    /**
     * @Description 结果状态
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:27 2019/6/5
     **/
    private ResultStatusEnum resultStatusEnum = ResultStatusEnum.success;

    /**
     * @Description 结果信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:27 2019/6/5
     **/
    private E message;

    public E getMessage() {
        return message;
    }

    /**
    * @Description 是否失败
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:21 2019/6/10
    * @param
    * @return true 是失败，false  没有失败
    **/
    public boolean isFailed(){
        if(ResultStatusEnum.failed.getCode().equals(this.resultStatusEnum.getCode())){
            return true;
        }
        return false;
    }

    public void setMessage(E message) {
        this.message = message;
    }

    public ResultStatusEnum getResultStatusEnum() {
        return resultStatusEnum;
    }

    public void setResultStatusEnum(ResultStatusEnum resultStatusEnum) {
        this.resultStatusEnum = resultStatusEnum;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
