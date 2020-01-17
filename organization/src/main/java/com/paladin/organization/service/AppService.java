package com.paladin.organization.service;

import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.exception.SystemException;
import com.paladin.framework.exception.SystemExceptionCode;
import com.paladin.framework.service.Condition;
import com.paladin.framework.service.QueryType;
import com.paladin.framework.service.UserSessionThreadManager;
import com.paladin.framework.service.WebServiceSupport;
import com.paladin.framework.utils.secure.RSAEncryptUtil;
import com.paladin.organization.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;

/**
 * @author TontoZhou
 * @since 2019/12/12
 */
@Service
public class AppService extends WebServiceSupport<App> {

    @Autowired
    private TokenService tokenService;

    public App getAppByClient(String clientId) {
        return searchOne(new Condition(App.FIELD_CLIENT_ID, QueryType.EQUAL, clientId));
    }

    /**
     * 获取重定向应用的URL
     *
     * @param appId
     * @return
     */
    public String getRedirectAppUrl(String appId) {
        App app = get(appId);
        if (app == null) {
            throw new BusinessException("找不到对应应用系统[ID:" + appId + "]");
        }

        String url = app.getAppUrl();

        String userId = UserSessionThreadManager.getCurrentUserSession().getUserId();
        String token = tokenService.createToken(userId, TokenService.TYPE_PERSONNEL);

        // 加密token和userId，客户端需要用私钥解密
        String encryptedToken = null;
        String encryptedUserId = null;

        try {
            RSAPublicKey publicKey = RSAEncryptUtil.getRSAPublicKey(app.getClientPublicSecret());
            encryptedToken = RSAEncryptUtil.encrypt(token, publicKey);
            encryptedUserId = RSAEncryptUtil.encrypt(userId, publicKey);
        } catch (Exception e) {
            throw new SystemException(SystemExceptionCode.CODE_ERROR_DATA, "无法加密跳转应用的Token", e);
        }

        // 拼接URL
        int i = url.indexOf("?");
        if (i < 0) {
            url += "?";
        } else if (i < url.length() - 1) {
            url += "&";
        }
        url += "userId=" + encryptedUserId + "&token=" + encryptedToken;

        return url;
    }
}
