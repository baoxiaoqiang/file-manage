package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IAnalysisTaskDao
 * @date 2019/6/12 9:56
 * @projectName file-manage
 * @description:
 */
@Component
public interface IAnalysisTaskErrorDao extends ImportBasicMapper<AnalysisTaskErrorBean> {

    /**
    * @Description 批量插入错误信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 15:21 2019/6/12
    * @param errorList
    * @return
    **/
    int addAnalysisTaskErrorBatch(List<AnalysisTaskErrorBean> errorList);


    /**
    * @Description 获取解析任务的解析异常信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:13 2019/6/15
    * @param taskId 任务ID
    * @param startRow
    * @param endRow
    * @return
    **/
    List<AnalysisTaskErrorBean> queryByTaskId(@Param("taskId") String taskId, @Param("startRow") Integer startRow, @Param("endRow")Integer endRow);

    /**
    * @Description 获取总条数
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:14 2019/6/15
    * @param taskId 任务ID
    * @return
    **/
    int queryByTaskIdCount(@Param("taskId") String taskId);
}
