package cn.com.quanyou.ioc.file.manage.facade;

import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.ImportParamVo;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IAsyncTaskService
 * @date 2019/6/12 13:05
 * @projectName file-manage
 * @description:
 */
public interface IAsyncTaskService {

    /**
    * @Description 创建导入任务
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:29 2019/6/14
    * @param task
    * @return
    **/
    public List<ResultInfo<AnalysisTaskBean,String>>  createImportExcelTask(AnalysisTaskBean task, ImportParamVo importParam);




}
