package com.paladin.organization.service;

import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.utils.StringUtil;
import com.paladin.organization.model.App;
import com.paladin.organization.service.dto.OAuthParam;
import com.paladin.organization.service.dto.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author TontoZhou
 * @since 2020/1/15
 */
@Service
public class OAuthService {

    @Autowired
    private AppService appService;

    @Autowired
    private TokenService tokenService;

    @Value("${jwt.app.expire-millisecond:1800000}")
    private long expireMillisecond;

    public OAuthToken clientOAuth(OAuthParam param) {

        String clientId = param.getClient_id();
        String clientSecret = param.getClient_secret();

        if (StringUtil.isEmpty(clientId) || StringUtil.isEmpty(clientSecret)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "客户端ID与秘钥不能为空");
        }

        App app = appService.getAppByClient(clientId);

        if (app == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "不存在的客户端[ID:" + clientId + "]");
        }

        if (!clientSecret.equals(app.getClientSecret())) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "客户端秘钥不正确");
        }

        Date expiration = new Date(System.currentTimeMillis() + expireMillisecond);

        String token = tokenService.createToken(app.getId(), TokenService.TYPE_APP, expiration);

        return new OAuthToken(token, expireMillisecond);
    }

}
