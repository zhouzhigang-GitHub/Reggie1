package com.zhou;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author 54086
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.zhou.mapper")
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
    }

    /**
     * 此方法放在启动类下即可，根据具体的业务逻辑可自行设置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(80)); //MB
        //factory.setMaxFileSize(DataSize.ofKilobytes(80)); //KB
//        factory.setMaxFileSize(DataSize.ofGigabytes(5)); //Gb
        /// 设置总上传数据总大小
        //factory.setMaxRequestSize(DataSize.ofKilobytes(100));  //KB
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));  //MB
        //factory.setMaxRequestSize(DataSize.ofGigabytes(100));  //Gb
        return factory.createMultipartConfig();
    }

}
