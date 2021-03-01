package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PutAwayTiForTaskStatus;
import cn.com.quanyou.ioc.file.manage.vo.NewNodeStatusVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bxq
 * @desc
 * @date 2019/12/18 13:44
 */
@Component
public interface INewProductBatchModifyDao extends ImportBasicMapper<PutAwayTiForTaskStatus>{

    List<NewNodeStatusVo> queryNodeStatus(@Param(value = "nodeId") String nodeId);
}
