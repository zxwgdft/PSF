package com.paladin.gateway.filter;

import com.paladin.framework.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author TontoZhou
 * @since 2019/12/23
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    @Autowired
    private TokenProvider tokenProvider;

    public AuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        AuthFilter filter = new AuthFilter(config);
        filter.setTokenProvider(tokenProvider);
        return filter;
    }

    public static class Config {

        private String tokenField = HttpHeaders.AUTHORIZATION;

        public String getTokenField() {
            return tokenField;
        }

        public void setTokenField(String tokenField) {
            this.tokenField = tokenField;
        }
    }

}
