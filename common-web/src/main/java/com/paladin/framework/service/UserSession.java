package com.paladin.framework.service;


import java.io.Serializable;

/**
 * 用户会话信息
 *
 * @author TontoZhou
 * @since 2018年1月29日
 */
public abstract class UserSession implements Serializable {

    private String userId;

    public UserSession(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        final UserSession that = (UserSession) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }





}
