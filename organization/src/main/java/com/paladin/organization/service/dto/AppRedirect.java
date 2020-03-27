package com.paladin.organization.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2020/3/25
 */
@Getter
@Setter
public class AppRedirect {

    private String code;
    private String appId;
    private String userId;

}