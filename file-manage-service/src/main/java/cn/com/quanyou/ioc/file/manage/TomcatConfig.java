package cn.com.quanyou.ioc.file.manage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class TomcatConfig {

    @Value("${file.max-file-size}")
    private Long MaxFileSize;
    @Value("${file.max-request-size}")
    private Long MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.ofMegabytes(MaxFileSize));
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(MaxRequestSize));
        return factory.createMultipartConfig();
    }
}