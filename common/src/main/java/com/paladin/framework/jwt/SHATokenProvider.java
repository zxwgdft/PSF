package com.paladin.framework.jwt;

import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.random.RandomUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class SHATokenProvider implements TokenProvider {

    @Value("${jwt.key.SHA.key:}")
    private String base64Key;
    @Value("${jwt.token-validity-in-milliseconds:1800000}")
    private long tokenValidityInMilliseconds;
    @Value("${jwt.issuer:}")
    private String issuer;

    private byte[] keyBytes;

    public SHATokenProvider(String base64Key, long tokenValidityInSeconds) {
        this.keyBytes = Base64.decodeBase64(base64Key);
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtil.isNotEmpty(base64Key)) {
            this.keyBytes = Base64.decodeBase64(base64Key);
        }
    }

    public SHATokenProvider() {
        String str = RandomUtil.getRandomString(125);
        Key key = new SecretKeySpec(str.getBytes(),
                SignatureAlgorithm.HS512.getJcaName());
        this.keyBytes = key.getEncoded();
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
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();
    }


    public Claims parseJWT(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(jwtToken)
                .getBody();
    }


}
