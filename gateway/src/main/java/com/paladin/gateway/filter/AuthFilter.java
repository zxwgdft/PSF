package com.paladin.gateway.filter;

import com.paladin.framework.common.R;
import com.paladin.gateway.service.AuthService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author TontoZhou
 * @since 2019/12/23
 */
public class AuthFilter implements GatewayFilter, Ordered {

    private AuthService authService;

    private AuthGatewayFilterFactory.Config config;

    public AuthFilter(AuthGatewayFilterFactory.Config config) {
        this.config = config;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();

        R result = authService.authenticate("admin", "admin123");
        System.out.println(result);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
