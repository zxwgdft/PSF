package com.paladin.framework.web;

import com.paladin.framework.web.convert.DateFormatter;
import com.paladin.framework.web.convert.Integer2EnumConverterFactory;
import com.paladin.framework.web.convert.String2EnumConverterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "paladin", value = "web-enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebProperties.class)
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private WebProperties webProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String filePath = webProperties.getFilePath();
        String staticPath = webProperties.getStaticPath();
        String faviconPath = webProperties.getFaviconPath();

        registry.addResourceHandler("/static/**").addResourceLocations(staticPath);
        registry.addResourceHandler("/file/**").addResourceLocations(filePath);
        registry.addResourceHandler("/favicon.ico").addResourceLocations(faviconPath);

        log.info("文件资源存放地址：" + filePath);
        log.info("静态资源存放地址：" + staticPath);
        log.info("favicon存放地址：" + faviconPath);

        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        String rootView = webProperties.getRootView();
        if (rootView != null && rootView.length() > 0) {
            registry.addViewController("/").setViewName(rootView);
        }

        List<UrlForwardOption> forwards = webProperties.getForwards();
        if (forwards != null) {
            for (UrlForwardOption forward : forwards) {
                registry.addViewController(forward.getFrom()).setViewName(forward.getTo());
            }
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(Date.class, new DateFormatter());
        registry.addConverterFactory(new Integer2EnumConverterFactory());
        registry.addConverterFactory(new String2EnumConverterFactory());
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/static/html/404.html"));
        factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/static/html/401.html"));
        factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/html/500.html"));
        return factory;
    }


}
