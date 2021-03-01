package cn.com.quanyou.ioc.file.manage.facade;

import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IRelationDataValidateService
 * @date 2019/6/13 16:14
 * @projectName file-manage
 * @description: 关联数据校验
 */
public interface IRelationDataValidateService {

    /**
    * @Description 是否正确的店铺名称。
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:14 2019/6/13
    * @return
    **/
    List<Map<String,String>> queryShopNames();

    /**
    * @Description 是否匹配的店铺名称和用户名
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:22 2019/6/13
    * @param shopName 店铺名称
    * @param userName 用户名
    * @return
    **/
    Boolean isCorrectShopNameAndUserName(String shopName, String userName);

    /**
    * @Description 是否正确的省份名称
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:28 2019/6/13
    * @return
    **/
    List<Map<String,String>> queryProvinces();


    /**
    * @Description 是否正确的问题类型
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:08 2019/6/19
    * @return
    **/
    List<Map<String,String>> queryReviewTypes();

}
