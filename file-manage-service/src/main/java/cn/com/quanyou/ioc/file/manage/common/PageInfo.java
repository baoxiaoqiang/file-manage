package cn.com.quanyou.ioc.file.manage.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: PageInfo
 * @date 2019/6/14 16:47
 * @projectName file-manage
 * @description:
 */
@Data
public class PageInfo<T> implements Serializable {

    /**
     * @Description 数据列表
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:49 2019/6/14
     **/
    private List<T> dataList;

    /**
     * @Description 第几页，从1开始
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:49 2019/6/14
     **/
    private Integer pageNum = ConstantsFileManage.PAGE_NUM_DEFAULT;

    /**
     * @Description 每页大小
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:50 2019/6/14
     **/
    private Integer pageSize=ConstantsFileManage.PAGE_SIZE_DEFAULT;

    /**
     * @Description 总记录数
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:50 2019/6/14
     **/
    private Integer total;

    public Integer getStartRow(){
        return (this.pageNum-1)*this.pageSize+1;
    }

    public Integer getEndRow(){
        return this.pageNum*this.pageSize;
    }

}
