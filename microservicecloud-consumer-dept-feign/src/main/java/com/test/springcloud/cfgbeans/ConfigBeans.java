package com.test.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBeans {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

   /* @Bean
    public IRule iRule(){
        //return new RoundRobinRule();//达到的目的，用我们重新选择的随机算法覆盖轮询
        //return new RandomRule();
        return new RetryRule();//重试

    }*/
}
