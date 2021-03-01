package cn.com.quanyou.ioc.file.manage.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Properties;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: PropertiesLoaderTest
 * @date 2019/6/15 19:00
 * @projectName file-manage
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesLoaderTest {



    @Test
    public void test() throws IOException {

        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/fdfs_client.conf"));

    }

}