package cn.com.quanyou.ioc.file.manage.facade;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ImportParamVo;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IAnalysisTaskService
 * @date 2019/6/12 9:55
 * @projectName file-manage
 * @description: excel解析任务接口
 */
public interface IAnalysisTaskService {

    /**
    * @Description 添加解析任务
    * @Author heshiyi@quanyou.com.cn
    * @Date 11:00 2019/6/12
    * @param entity 解析任务信息
    * @return
    **/
    ResultInfo<AnalysisTaskBean,String> addAnalysisTask (AnalysisTaskBean entity);

    /**
    * @Description 根据主键ID，获取任务信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 19:18 2019/6/18
    * @param dataId
    * @return
    **/
    AnalysisTaskBean queryByDataId(String dataId);

    /**
    * @Description 更新任务信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:54 2019/6/12
    * @param entity 解析任务，其中dataId不能为空
    * @return
    **/
    ResultInfo<AnalysisTaskBean,String> updateAnalysisTask(AnalysisTaskBean entity);

    /**
    * @Description 批量添加解析错误信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 15:05 2019/6/12
    * @param errorList 错误信息列表
    * @return
    **/
    ResultInfo<Integer,String> addAnalysisTaskErrorBatch(List<AnalysisTaskErrorBean> errorList,String loginUser);


    /**
     * @Description 根据导入类型，获取历史导入记录
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:30 2019/6/14
     * @param paramVo
     * @return
     **/
    ResultInfo<PageInfo<AnalysisTaskBean>, String> queryImportHistoryByImportType(ImportParamVo paramVo, PageInfo<AnalysisTaskBean> pageInfo);

    /**
    * @Description 根据任务ID，获取解析异常信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:04 2019/6/15
    * @param taskId 任务Id
    * @param pageInfo 分页信息
    * @return
    **/
    ResultInfo<PageInfo<AnalysisTaskErrorBean>,String> queryAnalysisExceptionByTaskId(String taskId,PageInfo<AnalysisTaskErrorBean> pageInfo);

}
