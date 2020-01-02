package com.paladin.organization.auth;

import com.paladin.framework.service.UserSession;
import com.paladin.framework.service.UserSessionFactory;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */

public class OrgUserSessionFactory implements UserSessionFactory {

    @Override
    public UserSession createUserSession(String subject) {
        return new OrgUserSession(subject);
    }

}
