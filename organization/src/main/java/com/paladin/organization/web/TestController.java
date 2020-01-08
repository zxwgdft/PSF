package com.paladin.organization.web;

import com.paladin.framework.common.R;
import com.paladin.framework.utils.uuid.UUIDUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TontoZhou
 * @since 2020/1/8
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/direct")
    public R<String> authenticateByAccount() {
        String messageId = UUIDUtil.createUUID();
        String messageData = "test message, hello!";
        String createTime = "2020-1-8";
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return R.success("OK");
    }

}
