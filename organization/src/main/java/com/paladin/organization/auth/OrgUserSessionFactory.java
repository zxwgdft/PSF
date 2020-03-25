package com.paladin.organization.auth;

import com.paladin.framework.service.UserSession;
import com.paladin.framework.service.UserSessionFactory;
import com.paladin.organization.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */

public class OrgUserSessionFactory implements UserSessionFactory {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserSession createUserSession(String subject) {
        return new OrgUserSession(subject, sysUserService);
    }

}
