package com.paladin.organization.core;

import com.paladin.framework.service.UserSession;
import com.paladin.organization.model.OrgPersonnel;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
public class OrgUserSession extends UserSession {

    private String userId;
    private OrgUserSessionLoader orgUserSessionLoad;

    public OrgUserSession(String userId, OrgUserSessionLoader orgUserSessionLoad) {
        this.userId = userId;
        this.orgUserSessionLoad = orgUserSessionLoad;
    }

    private volatile boolean loaded = false;
    private volatile boolean loaded_personnel = false;

    public void lazyLoad() {
        if (!loaded) {
            synchronized (this) {
                if (!loaded) {
                    orgUserSessionLoad.loadUserSession(this);
                    loaded = true;
                }
            }
        }
    }

    public void lazyLoadPersonnel() {
        if (!loaded_personnel) {
            synchronized (this) {
                if (!loaded_personnel) {
                    orgUserSessionLoad.loadPersonnel(this);
                    loaded_personnel = true;
                }
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    protected boolean isSystemAdmin;
    protected String userName;
    protected String personnelId;

    public String getUserName() {
        lazyLoad();
        return userName;
    }

    public boolean isSystemAdmin() {
        lazyLoad();
        return isSystemAdmin;
    }

    public String getPersonnelId() {
        lazyLoad();
        return personnelId;
    }


    protected OrgPersonnel personnel;

    public OrgPersonnel getPersonnel() {
        lazyLoad();
        lazyLoadPersonnel();
        return personnel;
    }

}
