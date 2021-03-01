package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.dao.IRelationDataValidateDao;
import cn.com.quanyou.ioc.file.manage.facade.IRelationDataValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: RelationDataValidateService
 * @date 2019/6/13 16:25
 * @projectName file-manage
 * @description: 关联校验实现
 */
@Service
public class RelationDataValidateService implements IRelationDataValidateService {

    @Autowired
    private IRelationDataValidateDao validateDao;

    @Override
    public List<Map<String, String>> queryShopNames() {
        return validateDao.queryShopNames();
    }

    @Override
    public Boolean isCorrectShopNameAndUserName(String shopName, String userName) {
        return null;
    }

    @Override
    public List<Map<String,String>> queryProvinces(){
        return validateDao.queryProvinces();
    }

    @Override
    public List<Map<String,String>> queryReviewTypes() {
        return validateDao.queryReviewTypes();
    }
}
