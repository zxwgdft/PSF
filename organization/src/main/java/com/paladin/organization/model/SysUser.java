package com.paladin.organization.model;

import com.paladin.framework.common.BaseModel;
import com.paladin.organization.model.constant.Bool;
import com.paladin.organization.model.constant.UserState;
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
public class SysUser extends BaseModel {

    public static final String COLUMN_FIELD_ACCOUNT = "account";

    @Id
    private String id;
    private Integer type;
    private String account;
    private String password;
    private String salt;
    private UserState state;
    private String cellphone;
    private String lastLoginIp;
    private Date lastLoginTime;
    private Bool isFirstLogin;

}
