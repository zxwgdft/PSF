package com.paladin.organization.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author TontoZhou
 * @since 2020/3/27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenToken {

    private String token;
    private long expiresTime;

}
