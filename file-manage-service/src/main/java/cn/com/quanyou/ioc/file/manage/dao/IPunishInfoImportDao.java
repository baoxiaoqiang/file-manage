package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PunishInfoBean;
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
public interface IPunishInfoImportDao extends ImportBasicMapper<PunishInfoBean> {

    /**
    * @Description 根据处罚时间，删除数据
    * @Author heshiyi@quanyou.com.cn
    * @Date 15:18 2019/6/19
    * @param punishDate
    * @return
    **/
    int deleteByPunishDae(@Param("punishDate") Date punishDate);
}
