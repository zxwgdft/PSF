package com.paladin.organization.service;

import com.paladin.framework.common.HttpCode;
import com.paladin.framework.common.R;
import com.paladin.framework.exception.BusinessException;
import com.paladin.framework.service.Condition;
import com.paladin.framework.service.QueryType;
import com.paladin.framework.service.WebServiceSupport;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.secure.SecureUtil;
import com.paladin.organization.auth.OrgUserSession;
import com.paladin.organization.auth.OrgUserSessionLoad;
import com.paladin.organization.model.App;
import com.paladin.organization.model.OrgPersonnel;
import com.paladin.organization.model.SysUser;
import com.paladin.organization.model.constant.AppConstant;
import com.paladin.organization.model.constant.UserType;
import com.paladin.organization.service.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TontoZhou
 * @since 2019/12/12
 */
@Service
public class SysUserService extends WebServiceSupport<SysUser> implements OrgUserSessionLoad {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AppService appService;
    @Autowired
    private OrgPersonnelService orgPersonnelService;

    public SysUser getUserByAccount(String account) {
        return searchOne(new Condition(SysUser.COLUMN_FIELD_ACCOUNT, QueryType.EQUAL, account));
    }

    public R authenticate(LoginUser loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        if (StringUtil.isNotEmpty(username) || StringUtil.isNotEmpty(password)) {
            SysUser sysUser = getUserByAccount(username);
            if (sysUser != null) {
                int state = sysUser.getState();
                if (state == AppConstant.USER_STATE_STOP) {
                    return R.fail("账号不可用");
                }

                if (state != AppConstant.USER_STATE_ENABLED) {
                    return R.fail("账号不可用");
                }

                if (SecureUtil.hashByMD5(password, sysUser.getSalt(), 1).equals(sysUser.getPassword())) {
                    String jwtToken = tokenService.createToken(sysUser.getId());
                    return R.success(jwtToken);
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
     * 懒加载用户详细信息
     *
     * @param userSession
     */
    @Override
    public void loadUserSession(OrgUserSession userSession) {
        String sysUserId = userSession.getSysUserId();

        SysUser sysUser = get(sysUserId);
        if (sysUser == null) {
            throw new BusinessException("登录用户不存在");
        }

        UserType userType = sysUser.getType();

        if (userType == UserType.ADMIN) {
            userSession.setUserId(sysUserId);
            userSession.setUserName("系统管理员");
            userSession.setUserType(userType);
        } else if (userType == UserType.APP) {
            String userId = sysUser.getUserId();
            App app = appService.get(userId);
            if (app == null) {
                throw new BusinessException("登录用户信息不存在");
            }

            userSession.setUserType(userType);
            userSession.setUserName(app.getAppName());
            userSession.setUserId(userId);
        } else if (userType == UserType.APP) {
            String userId = sysUser.getUserId();
            OrgPersonnel personnel = orgPersonnelService.get(userId);
            if (personnel == null) {
                throw new BusinessException("登录用户信息不存在");
            }

            userSession.setUserType(userType);
            userSession.setUserName(personnel.getName());
            userSession.setUserId(userId);
        } else {
            throw new BusinessException("未知用户类型[" + userType.name() + "]");
        }
    }
}
