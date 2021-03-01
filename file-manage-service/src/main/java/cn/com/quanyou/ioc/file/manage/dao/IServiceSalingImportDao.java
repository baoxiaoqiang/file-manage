package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceSalingBean;
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
public interface IServiceSalingImportDao extends ImportBasicMapper<ServiceSalingBean> {

    /**
    * @Description 根据日期，删除记录
    * @Author heshiyi@quanyou.com.cn
    * @Date 17:41 2019/6/19
    * @param saleDate
    * @return
    **/
    int deleteBySaleDate(@Param("saleDate") Date saleDate);

    /**
     * 根据店铺、日期删除
     * @param taskId
     * @return
     */
    int deleteByTaskIdAndShopName(@Param("taskId") String taskId, @Param("shopName") String shopName);

    /**
     * @Description: 根据任务id，店铺名称查询错误记录数
     * @param taskId
     * @param shopName
     * @Author: yangli
     * @Date: 2019/9/25-11:19
    **/
    int queryMistakeAndUncheckedCountByShopName(@Param("taskId")String taskId, @Param("shopName") String shopName);
}
