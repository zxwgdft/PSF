package com.paladin.framework.jwt;

import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.secure.RSAEncryptUtil;
import com.paladin.framework.utils.secure.RSAKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

public class RSATokenProvider implements TokenProvider {


    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @Value("${jwt.key.RSA.public-key:}")
    private String base64PublicKey;
    @Value("${jwt.key.RSA.private-key:}")
    private String base64PrivateKey;
    @Value("${jwt.token-validity-in-milliseconds:1800000}")
    private long tokenValidityInMilliseconds;
    @Value("${jwt.issuer:}")
    private String issuer;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtil.isNotEmpty(base64PublicKey) && StringUtil.isNotEmpty(base64PrivateKey)) {
            this.publicKey = RSAEncryptUtil.getRSAPublicKey(base64PublicKey);
            this.privateKey = RSAEncryptUtil.getRSAPrivateKey(base64PrivateKey);
        }
    }

    public RSATokenProvider() throws Exception {
        RSAKey key = RSAEncryptUtil.getRSAKey("jwt", 1024);
        this.publicKey = key.getPublicKey();
        this.privateKey = key.getPrivateKey();
    }


    public String createJWT(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(validity)
                // claims加在最后，可覆盖之前的build
                .addClaims(claims)
                .signWith(SignatureAlgorithm.RS512, privateKey)
                .compact();
    }

    public Claims parseJWT(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwtToken)
                .getBody();
    }


}
