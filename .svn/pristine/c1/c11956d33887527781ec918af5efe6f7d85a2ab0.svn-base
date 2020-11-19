package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan("com.springboot.configurer")
@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
//@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableAsync
public class ProvideApplication8012 {


	public static void main(String[] args) {
		SpringApplication.run(ProvideApplication8012.class, args);
		System.out.println("erpclound-provide-dept-8012===服务已经启动");
	}
}
