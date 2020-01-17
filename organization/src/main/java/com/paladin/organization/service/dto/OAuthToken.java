package com.paladin.organization.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2020/1/15
 */
@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "OAuth Token")
public class OAuthToken {

    @ApiModelProperty("授权token")
    private String accessToken;
    @ApiModelProperty("过期时长（毫秒）")
    private long expiresIn;

}
