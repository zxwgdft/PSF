package com.paladin.organization.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author TontoZhou
 * @since 2019/12/11
 */
@Getter
@Setter
public class OrgPersonnel {

    @Id
    private String id;

    private String orgUnitId;

    private String orgAgencyId;

    private String orgAssessTeamId;

    private String name;

    private String cellphone;

    private String account;

    private Date recordCreateTime;

    private Integer sex;

    private Integer oeducation;

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
