package com.paladin.framework.jwt;

import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * @author TontoZhou
 * @since 2019/12/26
 */
public interface TokenProvider {

    default String createJWT(String subject) {
        return createJWT(subject, null);
    }

    String createJWT(String subject, Map<String, Object> claims);


    Claims parseJWT(String jwtToken);

}
