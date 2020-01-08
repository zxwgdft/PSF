package com.paladin.framework.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.paladin.framework.common.CodeEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * 尝试扩展枚举反序列化
 * @author TontoZhou
 * @since 2020/1/3
 */
//@Configuration
//@ConditionalOnProperty(prefix = "paladin", value = "json-enabled", havingValue = "true", matchIfMissing = true)
public class JsonConfigurer {


    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule module = new SimpleModule();
        CodeEnumDeserializer deserialize = new CodeEnumDeserializer();
        module.addDeserializer(CodeEnum.class, deserialize);
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
