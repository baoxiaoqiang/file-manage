package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.EvaluateNegativeDetailBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IOperationalDataImportDao
 * @date 2019/6/14 10:37
 * @projectName file-manage
 * @description: 总评数据
 */
@Component
public interface IEvaluateNegativeDetailImportDao extends ImportBasicMapper<EvaluateNegativeDetailBean> {

    /**
    * @Description 根据评价日期，删除差评详情
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:24 2019/6/19
    * @param evaluateDate
    * @return
    **/
    int deleteByEvaluateDate(@Param("evaluateDate") Date evaluateDate);
}
