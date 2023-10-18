package com.wasupstudio.config;

import com.wasupstudio.interceptor.LicenseInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WebCorsConfiguration 跨域配置
 *
 * @author Paul
 */
@Slf4j
@Configuration
public class WebCorsConfig implements WebMvcConfigurer {
    @Value("${spring.profiles.active}")
    private String env; //當前配置文件
    private final InterceptorConfig interceptorConfig;

    private final LicenseInterceptor licenseInterceptor;

    @Autowired
    public WebCorsConfig(InterceptorConfig interceptorConfig, LicenseInterceptor licenseInterceptor) {
        this.interceptorConfig = interceptorConfig;
        this.licenseInterceptor = licenseInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 签名拦截
        String[] signExcludes = interceptorConfig.getLicense().toArray(new String[0]);
        registry.addInterceptor(licenseInterceptor).excludePathPatterns(signExcludes).order(0);

        log.info("======== 攔截器例外清單 ======== start");
        List<String> result = Arrays.stream(signExcludes).map(element -> {
            // 在这里执行操作
            log.info(element);
            return element; // 返回处理后的元素
        }).collect(Collectors.toList());

        log.info("======== 攔截器例外清單 ======== end");



    }

    /**
     * 設置swagger為默認主頁
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*")); //不可跟 config.setAllowCredentials(true) 同時使用
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        // 暴露 header 中的其他屬性給客戶端應用程序
        config.setExposedHeaders(Arrays.asList("Authorization", "X-Total-Count", "Link", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
