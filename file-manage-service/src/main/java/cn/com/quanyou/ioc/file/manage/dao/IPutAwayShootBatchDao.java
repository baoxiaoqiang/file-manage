package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PutAwayTiForShootBatch;
import org.springframework.stereotype.Component;

/**
 * @Description //新上架拍摄时间上报接口表Mapper
 * @Author bxq
 * @Date 2019/10/10
 **/
@Component
public interface IPutAwayShootBatchDao extends ImportBasicMapper<PutAwayTiForShootBatch>{

    /**
     * 是否存在校验
     * @param pt
     * @return
     */
    Integer existCheck(PutAwayTiForShootBatch pt);

    /**
     * 校验spu+拍摄批次的唯一
     * @param pt
     * @return
     */
    Integer onlyCheck(PutAwayTiForShootBatch pt);

    /**
     * 校验spu+新品的唯一
     * @param pt
     * @return
     */
    Integer onlyCheckForType(PutAwayTiForShootBatch pt);
}
