package com.paladin.organization.core;

import com.paladin.framework.common.HttpCode;
import com.paladin.framework.common.R;
import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.jwt.TokenProvider;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.secure.SecureUtil;
import com.paladin.organization.model.App;
import com.paladin.organization.model.SysUser;
import com.paladin.organization.model.constant.AppConstant;
import com.paladin.organization.service.AppService;
import com.paladin.organization.service.OrgPersonnelService;
import com.paladin.organization.service.SysUserService;
import com.paladin.organization.service.dto.LoginApp;
import com.paladin.organization.service.dto.LoginUser;
import com.paladin.organization.service.vo.OpenToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private OrgPersonnelService orgPersonnelService;


    /**
     * 用户登录
     *
     * @param loginUser
     * @return
     */
    public R authenticate(LoginUser loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (StringUtil.isNotEmpty(username) || StringUtil.isNotEmpty(password)) {
            SysUser sysUser = sysUserService.getUserByAccount(username);
            if (sysUser != null) {
                int state = sysUser.getState();
                if (state == AppConstant.USER_STATE_STOP) {
                    return R.fail("账号不可用");
                }

                if (state != AppConstant.USER_STATE_ENABLED) {
                    return R.fail("账号不可用");
                }

                if (SecureUtil.hashByMD5(password, sysUser.getSalt(), 1).equals(sysUser.getPassword())) {
                    long expires = System.currentTimeMillis() + userTokenExpireMilliseconds;
                    String jwtToken = tokenProvider.createJWT(sysUser.getId(), null, new Date(expires));
                    return R.success(new OpenToken(jwtToken, expires));
                } else {
                    return R.fail("密码错误");
                }
            } else {
                return R.fail("账号不存在");
            }
        } else {
            return R.fail(HttpCode.BAD_REQUEST, "账号或密码不能为空");
        }
    }

    /**
     * APP客户端登录
     *
     * @param loginApp
     * @return
     */
    public R authenticate(LoginApp loginApp) {
        String appId = loginApp.getAppId();
        String appSecret = loginApp.getAppSecret();
        if (StringUtil.isNotEmpty(appId) || StringUtil.isNotEmpty(appSecret)) {
            App app = appService.getAppByAppId(appId);
            if (app != null) {
                int state = app.getState();
                if (state == AppConstant.APP_STATE_STOP) {
                    return R.fail("应用客户端已停用");
                }

                if (state != AppConstant.APP_STATE_ENABLED) {
                    return R.fail("应用客户端不可用");
                }

                if (appSecret.equals(app.getAppSecret())) {
                    String subject = createAppClientSubject(app.getId());
                    long expires = System.currentTimeMillis() + userTokenExpireMilliseconds;
                    String jwtToken = tokenProvider.createJWT(subject, null, new Date(expires));
                    return R.success(new OpenToken(jwtToken, expires));
                } else {
                    return R.fail("秘钥错误");
                }
            } else {
                return R.fail("应用客户端不存在");
            }
        } else {
            return R.fail(HttpCode.BAD_REQUEST, "应用ID与秘钥不能为空");
        }
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
            userSession.personnel = orgPersonnelService.get(userSession.personnelId);
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

    public String createAppClientSubject(String id) {
        return appClientSessionPrefix + id;
    }
}
