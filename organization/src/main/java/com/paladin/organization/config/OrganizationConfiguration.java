package com.paladin.organization.config;

import com.paladin.framework.jwt.SHATokenProvider;
import com.paladin.framework.jwt.TokenProvider;
import com.paladin.framework.service.DataContainerManager;
import com.paladin.framework.service.ServiceSupportManager;
import com.paladin.framework.spring.SpringBeanHelper;
import com.paladin.framework.spring.SpringContainerManager;
import com.paladin.framework.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * @author TontoZhou
 * @since 2019/12/10
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(OrganizationProperties.class)
public class OrganizationConfiguration {

    /**
     * token 生产者
     */
    @Bean
    public TokenProvider getTokenProvider(Environment env) {
        SHATokenProvider tokenProvider = new SHATokenProvider();
        tokenProvider.setBase64Key(env.getProperty("jwt.key"));
        String expireMillisecondStr = env.getProperty("jwt.expire-millisecond");
        long expireMillisecond = StringUtil.isEmpty(expireMillisecondStr) ? 30 * 60 * 1000 : Long.valueOf(expireMillisecondStr);
        tokenProvider.setTokenExpireMilliseconds(expireMillisecond);
        tokenProvider.setIssuer(env.getProperty("jwt.issuer"));
        return tokenProvider;
    }


    /**
     * spring bean 获取帮助类
     */
    @Bean
    public SpringBeanHelper springBeanHolder() {
        return new SpringBeanHelper();
    }

    /**
     *
     */
    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory factory) {
        return new MongoTransactionManager(factory);
    }

    /**
     * spring container 管理器（用于spring加载完毕后运行的对象）
     */
    @Bean
    public SpringContainerManager springContainerManager() {
        return new SpringContainerManager();
    }

    /**
     * 数据容器管理器
     */
    @Bean
    public DataContainerManager getDataContainerManager() {
        return new DataContainerManager();
    }

    /**
     * service支持管理器
     */
    @Bean
    public ServiceSupportManager getServiceSupportConatiner() {
        return new ServiceSupportManager();
    }

}
