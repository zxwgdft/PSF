package com.paladin.supervise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@ComponentScan({"com.paladin.framework", "com.paladin.supervise"})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SuperviseApplication {

    public static void main(String[] args) {
        SpringApplication.run( SuperviseApplication.class, args );
    }

}
