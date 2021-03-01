package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.TransportationCostsBean;
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
public interface ITransportationCostsImportDao extends ImportBasicMapper<TransportationCostsBean> {

    /**
    * @Description 根据运费时间，删除
    * @Author heshiyi@quanyou.com.cn
    * @Date 18:16 2019/6/19
    * @param tranDate
    * @return
    **/
    int deleteByTranDate(Date tranDate);
}
