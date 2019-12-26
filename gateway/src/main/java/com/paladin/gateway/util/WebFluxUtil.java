package com.paladin.gateway.util;

import com.paladin.framework.exception.SystemException;
import com.paladin.framework.exception.SystemExceptionCode;
import com.paladin.framework.utils.JsonUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author TontoZhou
 * @since 2019/12/26
 */
public class WebFluxUtil {

    public static Mono<Void> writeResponse(ServerWebExchange serverWebExchange, HttpStatus status, byte[] data) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(status);
        DataBuffer buffer = response
                .bufferFactory().wrap(data);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    public static Mono<Void> writeStringResponse(ServerWebExchange serverWebExchange, HttpStatus status, String data) {
        return writeResponse(serverWebExchange, status, data == null ? new byte[0] : data.getBytes());
    }

    public static Mono<Void> writeResponseByJson(ServerWebExchange serverWebExchange, HttpStatus status, Object data) {
        try {
            byte[] bystes = data == null ? new byte[0] : JsonUtil.getJson(data).getBytes();
            return writeResponse(serverWebExchange, status, bystes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(SystemExceptionCode.CODE_ERROR_CODE, "[" + (data == null ? "null" : data.getClass()) + "]无法转化为json格式");
        }
    }

}
