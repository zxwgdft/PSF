package com.paladin.supervise.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TontoZhou
 * @since 2019/12/27
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "supervise")
public class SuperviseProperties {


}
