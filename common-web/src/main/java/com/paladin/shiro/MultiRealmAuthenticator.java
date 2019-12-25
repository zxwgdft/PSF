package com.paladin.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * 多Realm验证,准确抛出异常
 *
 * @author TontoZhou
 * @since 2019/11/27
 */
@Slf4j
public class MultiRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 重写该方法保证异常正确抛出,需要多个Realm支持不同Token，否则会出现异常覆盖
     *
     * @param realms
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {

        AuthenticationStrategy strategy = getAuthenticationStrategy();

        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        AuthenticationException authException = null;

        for (Realm realm : realms) {

            aggregate = strategy.beforeAttempt(realm, token, aggregate);

            if (realm.supports(token)) {

                AuthenticationInfo info = null;

                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable throwable) {

                    if (throwable instanceof AuthenticationException) {
                        authException = (AuthenticationException) throwable;
                    } else {
                        authException = new AuthenticationException("账号登录异常", throwable);
                    }
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, authException);

            }
        }

        if (authException != null) {
            throw authException;
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);

        return aggregate;
    }
}
