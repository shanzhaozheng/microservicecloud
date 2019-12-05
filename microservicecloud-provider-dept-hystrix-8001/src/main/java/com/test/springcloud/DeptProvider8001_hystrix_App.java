package com.test.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@EnableCircuitBreaker
public class DeptProvider8001_hystrix_App {
    public static void main(String[] args) {
        SpringApplication.run(DeptProvider8001_hystrix_App.class,args);
    }
}
