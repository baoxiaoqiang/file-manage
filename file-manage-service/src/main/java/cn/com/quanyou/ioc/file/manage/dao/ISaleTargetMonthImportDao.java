package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.SaleTargetMonthBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IOperationalDataImportDao
 * @date 2019/6/14 10:37
 * @projectName file-manage
 * @description: 总评数据
 */
@Component
public interface ISaleTargetMonthImportDao extends ImportBasicMapper<SaleTargetMonthBean> {

    /**
    * @Description 根据年份，删除数据
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:16 2019/6/19
    * @param targetYear
    * @return
    **/
    int deleteByTargetYear(@Param("targetYear") int targetYear);
}
