package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.dao.IAnalysisTaskDao;
import cn.com.quanyou.ioc.file.manage.dao.IAnalysisTaskErrorDao;
import cn.com.quanyou.ioc.file.manage.facade.IAnalysisTaskService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ImportParamVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: AnalysisTaskService
 * @date 2019/6/12 11:01
 * @projectName file-manage
 * @description: 解析任务servcie
 */
@Transactional(readOnly = true)
@Service
public class AnalysisTaskService implements IAnalysisTaskService {

    @Autowired
    private IAnalysisTaskDao taskDao;

    @Autowired
    private IAnalysisTaskErrorDao errorDao;

    @Override
    public AnalysisTaskBean queryByDataId(String dataId) {

        return taskDao.queryEntityByPrimaryKey(dataId);
    }

    @Transactional
    @Override
    public ResultInfo<AnalysisTaskBean,String> addAnalysisTask(AnalysisTaskBean entity) {

        ResultInfo<AnalysisTaskBean,String> resultInfo = new ResultInfo<>();

        if(entity == null){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("解析文件信息丢失");
            return resultInfo;
        }

        if(!ImportFileTypeEnum.hasCorrentType(entity.getTaskType())){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("解析类型没有找到");
            return resultInfo;
        }
        taskDao.add(entity);
        resultInfo.setData(entity);

        return resultInfo;
    }

    @Override
    public ResultInfo<AnalysisTaskBean,String> updateAnalysisTask(AnalysisTaskBean entity) {
        ResultInfo<AnalysisTaskBean,String> resultInfo = new ResultInfo<>();
        if( entity == null || entity.getDataId() == null){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("参数错误");
            return resultInfo;
        }
        int num = taskDao.updateByPrimaryKey(entity);
        if(num == 0){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("没有找到对应的信息");
            return resultInfo;
        }
        resultInfo.setData(entity);
        return resultInfo;
    }

    @Override
    public ResultInfo<Integer,String> addAnalysisTaskErrorBatch(List<AnalysisTaskErrorBean> errorList,String loginUser) {
        ResultInfo<Integer,String> resultInfo = new ResultInfo<>();
        if( errorList == null || errorList.isEmpty() || StringUtils.isBlank(loginUser)){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("参数丢失");
            return resultInfo;
        }
        for(AnalysisTaskErrorBean error:errorList){
            error.setCreatedBy(loginUser);
            error.setLastUpdatedBy(loginUser);
        }

        int num = errorDao.addAnalysisTaskErrorBatch(errorList);
        resultInfo.setData(num);
        resultInfo.setMessage("成功新增错误信息："+num+"条");

        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<AnalysisTaskBean>, String> queryImportHistoryByImportType(ImportParamVo paramVo, PageInfo<AnalysisTaskBean> pageInfo) {
        ResultInfo<PageInfo<AnalysisTaskBean>,String > resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);

        if(paramVo == null || paramVo.getImportTypeSubList() == null || paramVo.getImportTypeSubList().size() == 0){
            return resultInfo;
        }

        List<AnalysisTaskBean> list  = taskDao.queryByImportType(paramVo,pageInfo.getStartRow(),pageInfo.getEndRow());
        pageInfo.setDataList(list);
        pageInfo.setTotal(taskDao.queryByImportTypeCount(paramVo));
        return resultInfo;
    }

    @Override
    public ResultInfo<PageInfo<AnalysisTaskErrorBean>, String> queryAnalysisExceptionByTaskId(String taskId, PageInfo<AnalysisTaskErrorBean> pageInfo) {

        List<AnalysisTaskErrorBean> errorList = errorDao.queryByTaskId(taskId,pageInfo.getStartRow(),pageInfo.getEndRow());
        pageInfo.setDataList(errorList);
        pageInfo.setTotal(errorDao.queryByTaskIdCount(taskId));
        ResultInfo<PageInfo<AnalysisTaskErrorBean>,String > resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;
    }
}
