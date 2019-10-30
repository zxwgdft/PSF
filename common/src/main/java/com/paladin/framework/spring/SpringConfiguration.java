package com.paladin.framework.spring;

import com.paladin.framework.core.PaladinProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PaladinProperties.class)
public class SpringConfiguration {
    @Bean
    public SpringContainerManager springContainerManager() {
        return new SpringContainerManager();
    }

    @Bean
    public SpringBeanHelper springBeanHolder() {
        return new SpringBeanHelper();
    }
}
