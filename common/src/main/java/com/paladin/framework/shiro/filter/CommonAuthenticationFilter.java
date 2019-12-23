package com.paladin.framework.shiro.filter;

import com.paladin.framework.common.HttpCode;
import com.paladin.framework.common.R;
import com.paladin.framework.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 增强版本，增加ajax请求返回和json格式请求获取登录信息
 */
@Slf4j
public class CommonAuthenticationFilter extends FormAuthenticationFilter {

    public static final String ERROR_KEY_LOGIN_FAIL_MESSAGE = "loginFailMessage";

    /**
     * 强制启用ajax
     */
    private boolean forceToAjax = false;

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if (forceToAjax || WebUtil.isAjaxRequest((HttpServletRequest) request)) {
                WebUtil.sendJsonByCors((HttpServletResponse) response, R.fail(HttpCode.UNAUTHORIZED));
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (WebUtil.isJson(httpRequest)) {
            Map<String, Object> dataMap = WebUtil.parseJsonFromRequest(httpRequest, Map.class);
            String username = (String) dataMap.get(getUsernameParam());
            String password = (String) dataMap.get(getPasswordParam());
            Boolean isRememberMe = (Boolean) dataMap.get(getRememberMeParam());
            String host = getHost(request);
            return createToken(username, password, isRememberMe == null ? false : isRememberMe, host);
        } else {
            return super.createToken(request, response);
        }
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if (forceToAjax || WebUtil.isAjaxRequest((HttpServletRequest) request)) {
            onLoginSuccessAjaxResponse(token, subject, request, response);
            return false;
        } else {
            issueSuccessRedirect(request, response);
            return false;
        }
    }

    protected void onLoginSuccessAjaxResponse(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        R result = R.success(subject.getSession().getId());
        WebUtil.sendJsonByCors((HttpServletResponse) response, result);
    }

    private final static Map<Class, String> exceptionMessageMap;

    static {
        exceptionMessageMap = new HashMap<>();
        exceptionMessageMap.put(IncorrectCredentialsException.class, "账号密码不正确");
        exceptionMessageMap.put(ExpiredCredentialsException.class, "账号密码过期");
        exceptionMessageMap.put(CredentialsException.class, "账号密码异常");
        exceptionMessageMap.put(ConcurrentAccessException.class, "无法同时多个用户登录");
        exceptionMessageMap.put(UnknownAccountException.class, "账号不存在");
        exceptionMessageMap.put(ExcessiveAttemptsException.class, "账号验证次数超过限制");
        exceptionMessageMap.put(LockedAccountException.class, "账号被锁定");
        exceptionMessageMap.put(DisabledAccountException.class, "账号被禁用");
        exceptionMessageMap.put(AccountException.class, "账号异常");
        exceptionMessageMap.put(UnsupportedTokenException.class, "不支持当前TOKEN");
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String errorMsg = exceptionMessageMap.get(e.getClass());
        if (errorMsg == null) {
            errorMsg = e.getMessage();
        }

        if (forceToAjax || WebUtil.isAjaxRequest((HttpServletRequest) request)) {
            WebUtil.sendJsonByCors((HttpServletResponse) response, R.fail(HttpCode.UNAUTHORIZED, errorMsg));
            return false;
        } else {
            setFailureAttribute(request, e);
            request.setAttribute(ERROR_KEY_LOGIN_FAIL_MESSAGE, errorMsg);
            return true;
        }
    }


    public void setForceToAjax(boolean forceToAjax) {
        this.forceToAjax = forceToAjax;
    }

    public boolean isForceToAjax() {
        return this.forceToAjax;
    }


}
