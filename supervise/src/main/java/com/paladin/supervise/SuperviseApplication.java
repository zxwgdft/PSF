package com.paladin.supervise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@ComponentScan({"com.paladin.framework", "com.paladin.supervise"})
@MapperScan(basePackages = "com.paladin.supervise.dao")
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SuperviseApplication {

    public static void main(String[] args) {
        SpringApplication.run( SuperviseApplication.class, args );
    }

}
