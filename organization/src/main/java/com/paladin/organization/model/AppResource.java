package com.paladin.organization.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2020/1/8
 */
@Setter
@Getter
@ApiModel(description = "应用系统资源")
public class AppResource {

    private String id;
    private String name;
    private String appId;
    private String modelId;

    private String parent;
    private String path;

}
