package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceBellwetherTaobaoBean;
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
public interface IServiceBellwetherTaobaoImportDao extends ImportBasicMapper<ServiceBellwetherTaobaoBean> {

    /**
    * @Description 根据导入日期，删除数据
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:40 2019/6/19
    * @param reportDate
    * @return
    **/
    int deleteByReportDate(@Param("reportDate") Date reportDate);
}
