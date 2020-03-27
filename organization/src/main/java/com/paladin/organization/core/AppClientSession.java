package com.paladin.organization.core;

import com.paladin.framework.service.UserSession;

/**
 * @author TontoZhou
 * @since 2020/3/26
 */
public class AppClientSession extends UserSession {

    private String id;
    private AppClientSessionLoader sessionLoader;

    public AppClientSession(String id, AppClientSessionLoader sessionLoader) {
        this.id = id;
        this.sessionLoader = sessionLoader;
    }

    private volatile boolean loaded = false;

    public void lazyLoad() {
        if (!loaded) {
            synchronized (this) {
                if (!loaded) {
                    sessionLoader.loadAppClientSession(this);
                    loaded = true;
                }
            }
        }
    }

    @Override
    public String getUserId() {
        return id;
    }


}
