package cn.com.quanyou.ioc.file.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IRelationDataValidateDao
 * @date 2019/6/13 16:30
 * @projectName file-manage
 * @description:
 */
@Component
public interface IRelationDataValidateDao {

    /**
    * @Description 根据店铺名称，获取店铺数量
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:33 2019/6/13
    * @return
    **/
    List<Map<String,String>> queryShopNames();

    /**
    * @Description 根据省份名称，获取省份数量
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:42 2019/6/13
    * @return
    **/
    List<Map<String,String>> queryProvinces();

    /**
    * @Description 问题类型数量
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:11 2019/6/19
    * @return
    **/
    List<Map<String,String>> queryReviewTypes();

}
