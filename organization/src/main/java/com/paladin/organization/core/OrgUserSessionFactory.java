package com.paladin.organization.core;

import com.paladin.framework.service.UserSession;
import com.paladin.framework.service.UserSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
@Component
public class OrgUserSessionFactory implements UserSessionFactory {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserSession createUserSession(String subject) {
        if (authenticationManager.isAppClient(subject)) {
            return new AppClientSession(subject, authenticationManager);
        } else {
            return new OrgUserSession(subject, authenticationManager);
        }
    }

}
