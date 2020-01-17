package com.paladin.organization.service;

import com.paladin.framework.common.HttpCode;
import com.paladin.framework.common.R;
import com.paladin.framework.service.Condition;
import com.paladin.framework.service.QueryType;
import com.paladin.framework.service.WebServiceSupport;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.secure.SecureUtil;
import com.paladin.organization.model.SysUser;
import com.paladin.organization.service.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TontoZhou
 * @since 2019/12/12
 */
@Service
public class SysUserService extends WebServiceSupport<SysUser> {

    @Autowired
    private TokenService tokenService;

    public SysUser getUserByAccount(String account) {
        return searchOne(new Condition(SysUser.COLUMN_FIELD_ACCOUNT, QueryType.EQUAL, account));
    }

    public R authenticate(LoginUser loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        if (StringUtil.isNotEmpty(username) || StringUtil.isNotEmpty(password)) {
            SysUser sysUser = getUserByAccount(username);
            if (sysUser != null) {
                if (SecureUtil.hashByMD5(password, sysUser.getSalt(), 1).equals(sysUser.getPassword())) {
                    String jwtToken = tokenService.createToken(sysUser.getId(), TokenService.TYPE_PERSONNEL);
                    return R.success(jwtToken);
                } else {
                    return R.fail("密码错误");
                }
            } else {
                return R.fail("账号不存在");
            }
        } else {
            return R.fail(HttpCode.BAD_REQUEST, "账号或密码不能为空");
        }
    }
}
