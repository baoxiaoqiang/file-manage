package cn.com.quanyou.ioc.file.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages="cn.com.quanyou.ioc.file.manage")
@MapperScan(basePackages = "cn.com.quanyou.ioc.file.manage.dao")
public class FileManageApplication {

    public static void main(String[] args){

        SpringApplication.run(FileManageApplication.class,args);
    }
}
