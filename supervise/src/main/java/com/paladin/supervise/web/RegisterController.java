package com.paladin.supervise.web;

import com.paladin.framework.service.UserSession;
import com.paladin.framework.service.UserSessionThreadManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TontoZhou
 * @since 2019/11/1
 */
@RestController
@RequestMapping("/supervise/register")
public class RegisterController {

    @GetMapping("/do")
    public Object findPerson() {
        UserSession userSession = UserSessionThreadManager.getCurrentUserSession();
        return userSession;
    }

}
