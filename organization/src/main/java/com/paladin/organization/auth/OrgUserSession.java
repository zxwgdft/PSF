package com.paladin.organization.auth;

import com.paladin.framework.service.UserSession;
import com.paladin.organization.model.constant.UserType;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
public class OrgUserSession extends UserSession {

    private String sysUserId;
    private OrgUserSessionLoad orgUserSessionLoad;

    public OrgUserSession(String sysUserId, OrgUserSessionLoad orgUserSessionLoad) {
        this.sysUserId = sysUserId;
        this.orgUserSessionLoad = orgUserSessionLoad;
    }

    private volatile boolean loaded = false;

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

    public String getSysUserId() {
        return sysUserId;
    }


    private UserType userType;
    private String userName;
    private String userId;

    public String getUserId() {
        lazyLoad();
        return userId;
    }

    public UserType getUserType() {
        lazyLoad();
        return userType;
    }

    public String getUserName() {
        lazyLoad();
        return userName;
    }


    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
