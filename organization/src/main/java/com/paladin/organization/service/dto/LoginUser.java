package com.paladin.organization.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2019/12/26
 */
@Getter
@Setter
public class LoginUser {

    private String username;

    private String password;

    private boolean isRememberMe;

}
