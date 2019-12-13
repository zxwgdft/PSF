package com.paladin.framework.shiro;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ShiroProperties {

    /**
     * token field，如果NULL则不用
     */
    private String tokenField;

    /**
     * session 保存在redis中key的前缀
     */
    private String sessionPrefix = "shiro-session";

    /**
     * session 在redis中缓存时间
     */
    private int sessionTime = 30;

    /**
     * session lastAccessTime 更新间隔
     */
    private int accessTimeUpdateInterval = 120 * 1000;

    /**
     * shiro 过滤链定义
     */
    private Map<String, String> filterChainDefinition;


    private boolean isRedisEnabled;

}
