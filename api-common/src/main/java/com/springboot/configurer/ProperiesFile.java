package com.springboot.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource(value={"classpath:application-dev.properties"})
public class ProperiesFile {

    @Value("${web.appId}")
    private String appId;
    @Value("${web.appSecret}")
    private String appSecret;
    @Value("${web.address}")
    private String address;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
