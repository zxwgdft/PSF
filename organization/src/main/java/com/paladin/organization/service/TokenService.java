package com.paladin.organization.service;

import com.paladin.framework.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TontoZhou
 * @since 2020/1/16
 */
@Service
public class TokenService {

    private final static String FIELD_TYPE = "type";

    public final static String TYPE_APP = "app";
    public final static String TYPE_PERSONNEL = "personnel";

    @Autowired
    private TokenProvider tokenProvider;


    public String createToken(String subject, String type) {
        return createToken(subject, type, null);
    }

    public String createToken(String subject, String type, Date expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(FIELD_TYPE, type);
        return tokenProvider.createJWT(subject, claims, expiration);
    }




}
