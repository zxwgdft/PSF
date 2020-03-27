package com.paladin.organization.core;

/**
 * @author TontoZhou
 * @since 2020/3/25
 */
public interface OrgUserSessionLoader {

    void loadUserSession(OrgUserSession userSession);

    void loadPersonnel(OrgUserSession userSession);

}
