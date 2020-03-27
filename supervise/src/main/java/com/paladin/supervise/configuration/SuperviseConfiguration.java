package com.paladin.supervise.configuration;

import com.paladin.framework.jwt.SHATokenProvider;
import com.paladin.framework.jwt.TokenProvider;
import com.paladin.framework.service.UserSessionThreadManager;
import com.paladin.framework.utils.StringUtil;
import com.paladin.supervise.auth.SuperviseUserSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author TontoZhou
 * @since 2019/12/10
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SuperviseProperties.class)
public class SuperviseConfiguration {

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

    @Bean
    public SuperviseUserSessionFactory getOrgUserSessionFactory() {
        return new SuperviseUserSessionFactory();
    }

    @Bean
    public UserSessionThreadManager getUserSessionThreadManager() {
        return new UserSessionThreadManager();
    }

}
