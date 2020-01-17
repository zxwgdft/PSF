package com.paladin.organization.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2020/1/15
 */
@Getter
@Setter
@ApiModel(description = "OAuth参数")
public class OAuthParam {

    @ApiModelProperty("授权类型")
    private String grant_type;
    @ApiModelProperty("授权范围")
    private String scope;
    @ApiModelProperty("客户端ID")
    private String client_id;
    @ApiModelProperty("客户端秘钥")
    private String client_secret;

}
