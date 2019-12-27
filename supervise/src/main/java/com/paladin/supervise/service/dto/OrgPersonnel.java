package com.paladin.supervise.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author TontoZhou
 * @since 2019/12/11
 */
@Getter
@Setter
public class OrgPersonnel {

    private String id;

    private String unitId;

    private String agencyId;

    private String name;

    private String cellphone;

    private String account;

    private Date recordCreateTime;

    private Integer sex;

    private Integer education;

    private Integer nation;

    private Integer partisan;

    private Date birthday;

    private Integer jobDuties;

    private Integer jobRank;

    private Date startWorkTime;

    private Date comeUnitTime;

    private String resume;

    private String reward;

    private String punish;

    private Integer jobLevel;

    private Integer userProperty;

    private String identification;

    private Integer identificationType;

}
