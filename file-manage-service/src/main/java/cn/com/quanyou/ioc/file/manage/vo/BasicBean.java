package cn.com.quanyou.ioc.file.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: BasicBean
 * @date 2019/6/12 9:46
 * @projectName file-manage
 * @description:
 */
@Data
@ApiModel("基础类")
public class BasicBean implements Serializable {

    /**
     * @Description  主键ID
     * @Date 14:17 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    @ApiModelProperty(value = "主键ID", hidden = true)
    private String dataId;


    /**
     * @Description 记录创建时间
     * @Date 14:24 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    @ApiModelProperty(value = "记录创建时间", hidden = true)
    private Date creationDate;

    /**
     * @Description 最后更新时间，此属性不需要设置
     * @Date 14:25 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    @ApiModelProperty(value = "最后更新时间，此属性不需要设置", hidden = true)
    private Date lastUpdateDate;

    /**
     * @Description 创建者
     * @Date 14:26 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    @ApiModelProperty(value = "创建者", hidden = true)
    private String createdBy;

    /**
     * @Description 最后更新者
     * @Date 14:26 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    @ApiModelProperty(value = "最后更新者", hidden = true)
    private String lastUpdatedBy;

    /**
     * @Description 数据状态，逻辑删除等相关标识用。0、已失效（逻辑删除）；1、正常；其他
     * @Date 14:27 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    @ApiModelProperty(value = "数据状态，逻辑删除等相关标识用。0、已失效（逻辑删除）；1、正常；其他", hidden = true)
    private Integer validStatus;

    /**
     * @Description 任务ID
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:54 2019/6/19
     **/
    @ApiModelProperty(value = "任务ID", hidden = true)
    private String taskId;


}
