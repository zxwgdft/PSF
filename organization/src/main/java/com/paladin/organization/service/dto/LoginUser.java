package com.paladin.organization.service.dto;

import com.paladin.organization.model.constant.Sex;
import com.paladin.organization.model.constant.UserState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2019/12/26
 */
@Getter
@Setter
@ApiModel(description = "登录用户")
public class LoginUser {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("是否记住")
    private boolean isRememberMe;


    private Sex sex;

    private UserState state;

}
