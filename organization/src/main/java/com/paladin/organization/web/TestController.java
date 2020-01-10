package com.paladin.organization.web;

import com.paladin.framework.common.R;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.organization.model.AppResourceModel;
import com.paladin.organization.model.DynamicProperty;
import com.paladin.organization.service.AppResourceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private AppResourceService appResourceService;

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

    @GetMapping("/mongo")
    public R<List<AppResourceModel>> mongo() {

//        AppResourceModel model = new AppResourceModel();
//
//        model.setAppId("0001");
//        model.setName("菜单权限");
//
//        List<DynamicProperty> properties = new ArrayList<>();
//
//
//        DynamicProperty p1 = new DynamicProperty("url", "URL");
//        DynamicProperty p2 = new DynamicProperty("code", "编码");
//        properties.add(p1);
//        properties.add(p2);
//
//        model.setProperties(properties);
//
//        appResourceService.createResourceModel(model);


        return R.success(appResourceService.findAppResourceModels("0001"));
    }


    @PostMapping("/do")
    public Object doo(@RequestParam("json") String json) {


        return null;
    }

}
