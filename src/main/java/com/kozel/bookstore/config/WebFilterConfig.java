package com.kozel.bookstore.config;

import com.kozel.bookstore.config.properties.WebAuthenticationFilterProperties;
import com.kozel.bookstore.config.properties.WebAuthorizationFilterProperties;
import com.kozel.bookstore.web.filter.WebAuthenticationFilter;
import com.kozel.bookstore.web.filter.WebAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {
    @Bean
    public FilterRegistrationBean<WebAuthenticationFilter> authenticationFilter(
            WebAuthenticationFilter filter,
            WebAuthenticationFilterProperties properties) {

        FilterRegistrationBean<WebAuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setUrlPatterns(properties.getUrlPatterns());
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<WebAuthorizationFilter> authorizationFilter(
            WebAuthorizationFilter filter,
            WebAuthorizationFilterProperties properties) {

        FilterRegistrationBean<WebAuthorizationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setUrlPatterns(properties.getUrlPatterns());
        registration.setOrder(2);
        return registration;
    }
}
