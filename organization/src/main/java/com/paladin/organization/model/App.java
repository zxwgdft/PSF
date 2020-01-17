package com.paladin.organization.model;

import com.paladin.framework.common.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

/**
 * @author TontoZhou
 * @since 2020/1/8
 */
@Setter
@Getter
@ApiModel(description = "应用系统")
public class App extends BaseModel {

    public static final String FIELD_CLIENT_ID = "clientId";
    public static final String FIELD_CLIENT_SECRET = "clientSecret";

    @Id
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "应用系统名称")
    private String appName;

    @ApiModelProperty(value = "应用系统URL")
    private String appUrl;

    @ApiModelProperty(value = "联系人")
    private String contactName;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端秘钥")
    private String clientSecret;

    @ApiModelProperty(value = "客户端公钥")
    private String clientPublicSecret;
}
