package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.ImportParamVo;
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
public interface IAnalysisTaskDao extends ImportBasicMapper<AnalysisTaskBean> {

    /**
    * @Description 根据导入类型，获取导入列表
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:44 2019/6/14
    * @param taskType
    * @return
    **/
    List<AnalysisTaskBean> queryByImportType(@Param("paramVo") ImportParamVo paramVo, @Param("startRow") Integer startRow, @Param("endRow")Integer endRow);

    /**
    * @Description 查询总条数
    * @Author heshiyi@quanyou.com.cn
    * @Date 17:05 2019/6/14
    * @param paramVo
    * @return
    **/
    int queryByImportTypeCount(ImportParamVo paramVo);

    /**
     * 根据任务id查询店铺名称
     * @param taskId
     * @return
     */
    String queryShopNameByTaskId(@Param("taskId") String taskId);

}
