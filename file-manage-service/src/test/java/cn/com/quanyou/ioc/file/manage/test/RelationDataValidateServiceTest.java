package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.facade.IRelationDataValidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: RelationDataValidateServiceTest
 * @date 2019/6/13 16:38
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationDataValidateServiceTest {

    @Autowired
    private IRelationDataValidateService validateService;

    @Test
    public void isCorrentShopName() {

    }

    @Test
    public void isCorrentShopNameAndUserName() {

    }

    @Test
    public void isCorrentProvince() {
    }
}