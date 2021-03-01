package cn.com.quanyou.ioc.file.manage.vo;

import lombok.Data;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: AnalysisTaskErrorBean
 * @date 2019/6/12 14:58
 * @projectName file-manage
 * @description:
 */
@Data
public class AnalysisTaskErrorBean extends BasicBean{

    /**
     * @Description 任务ID
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:00 2019/6/12
     **/
    private String taskId;

    /**
     * @Description  excel行
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:00 2019/6/12
     **/
    private Integer row;

    /**
     * @Description  excel表头名
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:00 2019/6/12
     **/
    private String cellName;

    /**
     * @Description 错误信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:00 2019/6/12
     **/
    private String errorInfo;
}
