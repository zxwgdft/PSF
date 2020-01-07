package com.paladin.supervise.configuration;

import com.paladin.framework.service.UserSessionThreadManager;
import com.paladin.framework.web.convert.DateFormatter;
import com.paladin.framework.web.convert.Integer2EnumConverterFactory;
import com.paladin.framework.web.convert.String2EnumConverterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Configuration
@EnableConfigurationProperties(SuperviseWebProperties.class)
public class SuperviseWebConfigurer implements WebMvcConfigurer {

    @Resource
    private SuperviseWebProperties webProperties;

    @Autowired
    private UserSessionThreadManager userSessionThreadManager;

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
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(Date.class, new DateFormatter());
        registry.addConverterFactory(new Integer2EnumConverterFactory());
        registry.addConverterFactory(new String2EnumConverterFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userSessionThreadManager).addPathPatterns("/**");
    }




}
