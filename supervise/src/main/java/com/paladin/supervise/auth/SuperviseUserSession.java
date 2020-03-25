package com.paladin.supervise.auth;

import com.paladin.framework.service.UserSession;
import com.paladin.supervise.model.organization.OrgPersonnel;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
public class SuperviseUserSession extends UserSession {

    private OrgPersonnel personnel;

    public SuperviseUserSession(OrgPersonnel personnel) {
        this.personnel = personnel;
    }

    public OrgPersonnel getPersonnel() {
        return personnel;
    }

    @Override
    public String getUserId() {
        return personnel.getId();
    }
}
