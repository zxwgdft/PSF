package com.paladin.framework.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.paladin.framework.common.CodeEnum;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * 解决 spring cloud json 转枚举问题
 *
 * @author xxxx
 * 2018年6月13日 下午5:30:02
 */
public class CodeEnumDeserializer extends JsonDeserializer<CodeEnum> {

    @Override
    public CodeEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String currentName = jp.currentName();
        Object currentValue = jp.getCurrentValue();
        Class enumClazz = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        if (enumClazz.isEnum()) {
            CodeEnum[] enumConstants = (CodeEnum[]) enumClazz.getEnumConstants();
            for (CodeEnum e : enumConstants) {
                if (e.getCode() == (int) currentValue) {
                    return e;
                }
            }
        }
        return null;
    }


}