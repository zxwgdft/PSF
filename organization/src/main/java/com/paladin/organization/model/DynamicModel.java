package com.paladin.organization.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author TontoZhou
 * @since 2020/1/10
 */
@Setter
@Getter
@ApiModel(description = "动态实体类")
public class DynamicModel {

    private String name;

    private List<DynamicProperty> properties;

}
