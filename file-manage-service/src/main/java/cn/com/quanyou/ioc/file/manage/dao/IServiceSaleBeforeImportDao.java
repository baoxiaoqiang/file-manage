package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceSaleBeforeBean;
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
public interface IServiceSaleBeforeImportDao extends ImportBasicMapper<ServiceSaleBeforeBean> {

    int deleteBySaleDate(@Param("saleDate") Date saleDate);

    int deleteByTaskIdAndShopName(@Param("taskId") String taskId, @Param("shopName") String shopName);
}
