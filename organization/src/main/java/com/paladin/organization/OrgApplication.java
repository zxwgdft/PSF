package com.paladin.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author TontoZhou
 * @since 2019/10/30
 */
@ComponentScan({"com.paladin.framework", "com.paladin.organization"})
@MapperScan(basePackages = "com.paladin.organization.dao")
@SpringBootApplication
@EnableEurekaClient
public class OrgApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgApplication.class, args);
    }


}
