package com.springboot.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class UploadConfig {

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        //设定文件上传时写入内存的最大值，如果小于这个参数不会生成临时文件，默认为10240
        resolver.setMaxInMemorySize(104857600);
        //上传文件大小 50M 50*1024*1024
        resolver.setMaxUploadSize(100*1024*1024);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
}
