package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.OperationalDataBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IOperationalDataImportDao
 * @date 2019/6/14 10:37
 * @projectName file-manage
 * @description: 运营数据
 */
@Component
public interface IOperationalDataImportDao extends ImportBasicMapper<OperationalDataBean> {

    int deleteByTaskIdAndShopName(@Param("taskId")String taskId, @Param("shopName")String shopName);
}
