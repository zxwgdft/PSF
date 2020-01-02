package com.paladin.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author TontoZhou
 * @since 2020/1/2
 */
public class UserSessionThreadManager implements HandlerInterceptor {

    @Value("${auth.user-field:User-ID}")
    private String authUserField;

    @Autowired
    private UserSessionFactory userSessionFactory;

    private final static ThreadLocal<UserSession> sessionMap = new ThreadLocal<>();

    /// 可以使用缓存控制UserSession,但需要有线程维护

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(authUserField);
        if (userId != null && userId.length() > 0) {
            UserSession userSession = userSessionFactory.createUserSession(userId);
            if (userSession != null) {
                sessionMap.set(userSession);
            }
        }
        return true;
    }


    public static UserSession getCurrentUserSession() {
        return sessionMap.get();
    }
}
