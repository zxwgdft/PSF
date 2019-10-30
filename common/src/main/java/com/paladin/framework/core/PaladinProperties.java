package com.paladin.framework.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paladin")
@Data
public class PaladinProperties {
    /**
     * Web 开关
     */
    private boolean webEnabled = true;
    /**
     * Mybatis 开关
     */
    private boolean mybatisEnabled = true;
    /**
     * SHIRO 开关
     */
    private boolean shiroEnabled = true;
    /**
     * CAS 开关
     */
    private boolean casEnabled = false;
    /**
     * DRUID 开关
     */
    private boolean druidEnabled = false;
    /**
     * 默认密码
     */
    private String defaultPassword = "1";
    /**
     * 是否集群
     */
    private boolean cluster = false;
}
