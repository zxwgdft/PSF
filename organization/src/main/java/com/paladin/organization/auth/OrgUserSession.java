package com.paladin.organization.auth;

import com.paladin.framework.service.UserSession;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
public class OrgUserSession extends UserSession {

    public OrgUserSession(String userId) {
        super(userId);
    }
}
