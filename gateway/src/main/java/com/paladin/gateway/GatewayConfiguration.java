package com.paladin.gateway;

import com.paladin.framework.jwt.SHATokenProvider;
import com.paladin.framework.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TontoZhou
 * @since 2019/12/10
 */
@Slf4j
@Configuration
public class GatewayConfiguration {

    @Bean
    public TokenProvider getTokenProvider() {
        return new SHATokenProvider();
    }

}
