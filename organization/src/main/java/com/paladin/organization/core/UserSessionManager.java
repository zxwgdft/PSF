package com.paladin.organization.core;

import com.paladin.framework.service.UserSession;
import com.paladin.framework.service.UserSessionThreadManager;
import org.springframework.stereotype.Component;

/**
 * @author TontoZhou
 * @since 2020/3/27
 */
@Component
public class UserSessionManager extends UserSessionThreadManager {


    public static AppClientSession getAppClientSession() {
        UserSession session = UserSessionThreadManager.sessionMap.get();
        if (session != null && session instanceof AppClientSession) {
            return (AppClientSession) session;
        }
        return null;
    }

    public static OrgUserSession getOrgUserSession() {
        UserSession session = UserSessionThreadManager.sessionMap.get();
        if (session != null && session instanceof OrgUserSession) {
            return (OrgUserSession) session;
        }
        return null;
    }

}
