package com.paladin.organization.service;

import com.paladin.framework.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author TontoZhou
 * @since 2020/1/16
 */
@Service
public class TokenService {

    @Autowired
    private TokenProvider tokenProvider;

    public String createToken(String subject) {
        return tokenProvider.createJWT(subject, null, null);
    }

    public String createToken(String subject, Date expiration) {
        return tokenProvider.createJWT(subject, null, expiration);
    }


}
