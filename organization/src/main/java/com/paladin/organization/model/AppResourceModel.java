package com.paladin.organization.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author TontoZhou
 * @since 2020/1/10
 */
@Setter
@Getter
@ApiModel(description = "应用资源实体类")
@ToString
public class AppResourceModel extends DynamicModel {

    public static final String FIELD_APP_ID = "appId";

    private String id;
    private String appId;

}
