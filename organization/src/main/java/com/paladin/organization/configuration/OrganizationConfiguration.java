package com.paladin.organization.configuration;

import com.paladin.framework.jwt.AESTokenProvider;
import com.paladin.framework.jwt.TokenProvider;
import com.paladin.framework.service.UserSessionThreadManager;
import com.paladin.framework.utils.StringUtil;
import com.paladin.organization.auth.OrgUserSessionFactory;
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
@EnableConfigurationProperties(OrganizationProperties.class)
public class OrganizationConfiguration {

    @Bean
    public TokenProvider getTokenProvider(Environment env) {
        AESTokenProvider tokenProvider = new AESTokenProvider();
        tokenProvider.setBase64Key(env.getProperty("jwt.key"));
        String expireMillisecondStr = env.getProperty("jwt.expire-millisecond");
        long expireMillisecond = StringUtil.isEmpty(expireMillisecondStr) ? 30 * 60 * 1000 : Long.valueOf(expireMillisecondStr);
        tokenProvider.setTokenExpireMilliseconds(expireMillisecond);
        tokenProvider.setIssuer(env.getProperty("jwt.issuer"));
        return tokenProvider;
    }

    @Bean
    public OrgUserSessionFactory getOrgUserSessionFactory() {
        return new OrgUserSessionFactory();
    }

    @Bean
    public UserSessionThreadManager getUserSessionThreadManager() {
        return new UserSessionThreadManager();
    }

}
