package com.paladin.organization.service;

import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.exception.SystemException;
import com.paladin.framework.exception.SystemExceptionCode;
import com.paladin.framework.service.Condition;
import com.paladin.framework.service.QueryType;
import com.paladin.framework.service.UserSessionThreadManager;
import com.paladin.framework.service.WebServiceSupport;
import com.paladin.framework.utils.secure.AESEncryptUtil;
import com.paladin.organization.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 方式1：跳转到APP并传递加密的userId和token，APP判断是否已经存在userId，不存在则直接使用token从平台获取信息处理（简单，取巧，传递token过长）
     * 方式2：跳转到APP并传递加密的userId，APP自己获取平台token，并获取userId相应信息（更传统通用，符合OAuth2）
     *
     *
     * @param appId
     * @return
     */
    public String getRedirectAppUrl(String appId) {
        App app = get(appId);
        if (app == null) {
            throw new BusinessException("找不到对应应用系统[ID:" + appId + "]");
        }

        // TODO 判断是否有权跳转应用


        String url = app.getAppUrl();

        String userId = UserSessionThreadManager.getCurrentUserSession().getUserId();
        String token = tokenService.createToken(userId, TokenService.TYPE_APP);

        // 加密token和userId，客户端需要用私钥解密
        String encryptedToken = null;
        String encryptedUserId = null;

        String secret = app.getClientSecret();

        try {
            encryptedToken = AESEncryptUtil.encrypt(token, secret, true);
            encryptedUserId = AESEncryptUtil.encrypt(userId, secret, true);
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
