package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ImportBasicMapper
 * @date 2019/6/12 9:58
 * @projectName file-manage
 * @description:
 */
public interface ImportBasicMapper<T> {

    int add(T entity);

    int updateByPrimaryKey(T entity);

    <T> T queryEntityByPrimaryKey(@Param("dataId") String dataId);


    /**
    * @Description 克隆历史表到正式表
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:50 2019/6/22
    * @param taskId
    * @return
    **/
    int cloneFromTempT(@Param("taskId") String taskId);

    /**
     * @Description 克隆历史表到正式表单个店铺
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:50 2019/6/22
     * @param taskId
     * @return
     **/
    int cloneFromTempTByShop(@Param("taskId") String taskId, @Param("shopName") String shopName);

    /**
    * @Description 根据任务，删除正式表数据
    * @Author heshiyi@quanyou.com.cn
    * @Date 11:18 2019/6/22
    * @param taskId
    * @return
    **/
    int deleteByTaskId(@Param("taskId") String taskId);
    /**
     * @Description 批量MAP保存
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:45 2019/6/17
     * @param dataList
     * @return
     **/
    int addBatchMap(List<Map<String,Object>> dataList);


    /**
     * @Description 根据任务ID，获取信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 21:13 2019/6/17
     * @param taskId
     * @return
     **/
    List<Map<String,Object>> queryByTaskId(@Param("taskId") String taskId,@Param("validStatus") Integer validStatus);


    /**
    * @Description 根据主键ID，更新验证状态信息。
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:38 2019/6/18
    * @param dataIds 主键ID列表
    * @param validStatusCode 状态信息
    * @return
    **/
    int updateValidStatusByDataIds(@Param("dataIds") List<String> dataIds,@Param("validStatusCode") Integer validStatusCode);

    /**
    * @Description 查询未验证、验证未通过的数据条数
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:59 2019/6/18
    * @param taskId
    * @return
    **/
    int queryMistakeAndUncheckedCount(@Param("taskId") String taskId);

    /**
    * @Description 查询
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:35 2019/6/20
    * @param searchParamVo
    * @param startRow
    * @param endRow
    * @return
    **/
    List<T> search(@Param("searchParam") SearchParamVo searchParamVo,@Param("startRow") Integer startRow, @Param("endRow")Integer endRow);

    /**
    * @Description 查询总条数
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:42 2019/6/20
    * @param searchParamVo
    * @param startRow
    * @param endRow
    * @return
    **/
    int querySearchTotal(@Param("searchParam") SearchParamVo searchParamVo,@Param("startRow") Integer startRow, @Param("endRow")Integer endRow);
}
