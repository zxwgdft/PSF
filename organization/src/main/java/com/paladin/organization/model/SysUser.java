package com.paladin.organization.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author TontoZhou
 * @since 2019/12/13
 */
@Getter
@Setter
public class SysUser {

    public static final String COLUMN_FIELD_ACCOUNT = "account";

    @Id
    private String id;
    private String account;
    private String password;
    private String salt;
    private String userId;
    private Integer state;
    private Integer type;
    private Date lastLoginTime;
    private Integer isFirstLogin = 1;

}
