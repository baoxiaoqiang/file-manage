package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ImportNewProductTime2RemarkTi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author bxq
 * @desc
 * @date 2020/1/2 15:58
 */
@Component
public interface INewProductTime2RemarkDao extends ImportBasicMapper<ImportNewProductTime2RemarkTi>{

    /**
     * 校验存在
     * @param spuCode
     * @return
     */
    int existCheck(@Param("spuCode") String spuCode);
}
