package com.paladin.organization.configuration;

import com.paladin.framework.shiro.ShiroProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TontoZhou
 * @since 2019/12/13
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "paladin.shiro")
public class OrganizationShiroProperties extends ShiroProperties {

    /**
     * 登录URL
     */
    private String loginUrl = "/organization/login";

    /**
     * 登出URL
     */
    private String logoutUrl = "/organization/logout";

    /**
     * 登录成功跳转URL
     */
    private String successUrl = "/organization/index";

    /**
     * 未验证跳转页面
     */
    private String unauthorizedUrl = "/static/html/error_401.html";

}
