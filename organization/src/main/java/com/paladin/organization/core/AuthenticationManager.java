package com.paladin.organization.core;

import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.jwt.TokenProvider;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.secure.SecureUtil;
import com.paladin.organization.model.App;
import com.paladin.organization.model.SysUser;
import com.paladin.organization.model.constant.AppConstant;
import com.paladin.organization.service.AppRedirectService;
import com.paladin.organization.service.AppService;
import com.paladin.organization.service.PersonnelService;
import com.paladin.organization.service.SysUserService;
import com.paladin.organization.service.dto.LoginApp;
import com.paladin.organization.service.dto.LoginUser;
import com.paladin.organization.service.dto.OAuthParam;
import com.paladin.organization.service.vo.OpenToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author TontoZhou
 * @since 2020/3/26
 */
@Component
public class AuthenticationManager implements OrgUserSessionLoader, AppClientSessionLoader {

    // 应用客户端session_id前缀，用户维持会话时标明是应用客户端
    @Value("${app.client.session-prefix:AppClient_}")
    private String appClientSessionPrefix;

    // 应用客户端token过期时间
    @Value("${app.client.token-expire-milliseconds:86400000}")
    private long appClientTokenExpireMilliseconds;

    // 用户token过期时间
    @Value("${user.token-expire-milliseconds:3600000}")
    private long userTokenExpireMilliseconds;


    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AppService appService;
    @Autowired
    private AppRedirectService appRedirectService;
    @Autowired
    private PersonnelService personnelService;


    /**
     * 用户登录并返回token
     *
     * @param loginUser
     * @return 返回token，如果未通过验证则会抛出异常
     */
    public OpenToken authenticateAndCreateToken(LoginUser loginUser) {
        SysUser sysUser = authenticateUser(loginUser);
        long expires = System.currentTimeMillis() + userTokenExpireMilliseconds;
        String jwtToken = tokenProvider.createJWT(sysUser.getId(), null, new Date(expires));
        return new OpenToken(jwtToken, expires);
    }

    /**
     * 用户登录
     *
     * @param loginUser
     * @return 用户信息
     */
    public SysUser authenticateUser(LoginUser loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (StringUtil.isNotEmpty(username) || StringUtil.isNotEmpty(password)) {
            SysUser sysUser = sysUserService.getUserByAccount(username);
            if (sysUser != null) {
                int state = sysUser.getState();
                if (state == AppConstant.USER_STATE_STOP) {
                    throw new BusinessException("账号不可用");
                }

                if (state != AppConstant.USER_STATE_ENABLED) {
                    throw new BusinessException("账号不可用");
                }

                // TODO 增加手机登录（手机账号或手机验证码登录）

                if (SecureUtil.hashByMD5(password, sysUser.getSalt()).equals(sysUser.getPassword())) {
                    return sysUser;
                } else {
                    throw new BusinessException("密码错误");
                }
            } else {
                throw new BusinessException("账号不存在");
            }
        } else {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "账号或密码不能为空");
        }
    }

    /**
     * APP客户端登录并获取token
     *
     * @param loginApp
     * @return 返回token，验证未通过则会抛出异常
     */
    public OpenToken authenticateAndCreateToken(LoginApp loginApp) {
        String subject = authenticateApp(loginApp);
        long expires = System.currentTimeMillis() + userTokenExpireMilliseconds;
        String jwtToken = tokenProvider.createJWT(subject, null, new Date(expires));
        return new OpenToken(jwtToken, expires);
    }

    /**
     * APP客户端登录
     *
     * @param loginApp
     * @return 加入了识别字段的appId
     */
    private String authenticateApp(LoginApp loginApp) {
        String appId = loginApp.getAppId();
        String appSecret = loginApp.getAppSecret();
        if (StringUtil.isNotEmpty(appId) || StringUtil.isNotEmpty(appSecret)) {
            App app = appService.getAppByAppId(appId);
            if (app != null) {
                int state = app.getState();
                if (state == AppConstant.APP_STATE_STOP) {
                    throw new BusinessException("应用客户端已停用");
                }

                if (state != AppConstant.APP_STATE_ENABLED) {
                    throw new BusinessException("应用客户端不可用");
                }

                // TODO 判断请求地址是否是与APP相同域名，安全性考虑

                if (appSecret.equals(app.getAppSecret())) {
                    return createAppClientSubject(appId);
                } else {
                    throw new BusinessException("秘钥错误");
                }
            } else {
                throw new BusinessException("应用客户端不存在");
            }
        } else {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "应用ID与秘钥不能为空");
        }
    }


    /**
     * oauth授权验证并返回带有验证确认码的重定向url
     *
     * @param oauthParam
     * @return 返回重定向跳转的url
     */
    public String authenticateAndRedirect(OAuthParam oauthParam) {
        SysUser sysUser = authenticateUser(new LoginUser(oauthParam.getUsername(), oauthParam.getPassword(), false));
        // 返回重定向
        return appRedirectService.getRedirectUrlByOauth(oauthParam.getAppId(), sysUser);
    }

    /**
     * 加载用户会话基本信息
     *
     * @param userSession
     */
    @Override
    public void loadUserSession(OrgUserSession userSession) {
        String sysUserId = userSession.getUserId();

        SysUser sysUser = sysUserService.get(sysUserId);
        if (sysUser == null) {
            throw new BusinessException("登录用户不存在");
        }

        if (sysUser.getIsSysAdmin()) {
            userSession.isSystemAdmin = true;
        }

        userSession.personnelId = sysUser.getPersonnelId();
        userSession.userName = sysUser.getAccount();

        // TODO 加载权限
    }

    /**
     * 加载登录用户对应人员信息
     *
     * @param userSession
     */
    @Override
    public void loadPersonnel(OrgUserSession userSession) {
        if (StringUtil.isNotEmpty(userSession.personnelId)) {
            userSession.personnel = personnelService.get(userSession.personnelId);
            if (userSession.personnel == null) {
                throw new BusinessException("登录用户对应人员信息已经不存在");
            }
        }
    }


    /**
     * 加载应用客户端会话基本信息
     *
     * @param session
     */
    @Override
    public void loadAppClientSession(AppClientSession session) {

        // TODO 读取客户端session相关信息
    }

    /**
     * 判断对象是否是APP客户端，通过Token中带的subject是否包含固定前缀
     *
     * @param subject
     * @return
     */
    public boolean isAppClient(String subject) {
        return subject.startsWith(appClientSessionPrefix);
    }

    /**
     * 基于app_id创建subject
     */
    public String createAppClientSubject(String appId) {
        return appClientSessionPrefix + appId;
    }

    /**
     * 根据subject解析出app_id，是createAppClientSubject方法的反向
     */
    public String getAppClientIdFromSubject(String subject) {
        return subject.substring(appClientSessionPrefix.length());
    }

}
