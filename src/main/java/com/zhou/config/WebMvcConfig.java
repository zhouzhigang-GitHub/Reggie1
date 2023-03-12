package com.zhou.config;

import com.zhou.common.JacksonObjectMapper;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.MultipartConfigElement;
import java.util.List;

/**
 * @Author 周志刚
 * @Date 2022/8/13 15:23
 * @PackageName: com.zhou.config
 * @ClassName: WebMvcConfig
 * @Description: TODO
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{


    /**
     * 静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 拓展mvc消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将转换器加入到mvc容器中
        converters.add(0,messageConverter);
    }


}
