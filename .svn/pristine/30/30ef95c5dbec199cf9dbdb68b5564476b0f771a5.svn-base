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
//@EnableElasticsearchRepositories(basePackages = "com.springboot.repositories")
public class ProvideApplication5001 {


	public static void main(String[] args) {
		SpringApplication.run(ProvideApplication5001.class, args);
		System.out.println("center-server-5001===服务已经启动");
	}
}
