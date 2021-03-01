package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PutAwayTi;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description //新上架上报接口表Mapper
 * @Author yangli
 * @Date 2019/10/10-10:12
 **/
@Component
public interface IPutAwayTiDao extends ImportBasicMapper<PutAwayTi>{

    List<PutAwayTi> listUnPutwayRecord();
}
