package com.kozel.bookstore.config;

import com.kozel.bookstore.config.properties.WebAuthenticationFilterProperties;
import com.kozel.bookstore.config.properties.WebAuthorizationFilterProperties;
import com.kozel.bookstore.web.filter.WebAuthenticationFilter;
import com.kozel.bookstore.web.filter.WebAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class for registering custom web filters.
 * <p>
 * This class registers and defines the order of security-related filters in the
 * servlet container. It provides a centralized location for managing the application's
 * security filter chain. Each filter's registration includes setting its filter instance,
 * URL patterns, and execution order.
 * </p>
 *
 * @see WebAuthenticationFilter
 * @see WebAuthorizationFilter
 */
@Configuration
public class WebFilterConfig {

    /**
     * Registers the authentication filter.
     * <p>
     * This bean configures the {@link WebAuthenticationFilter} with URL patterns
     * from its properties class. The order is set to 1, which places it early in
     * the filter chain to handle user authentication before other security checks.
     * </p>
     *
     * @param filter     The {@link WebAuthenticationFilter} bean.
     * @param properties The {@link WebAuthenticationFilterProperties} bean.
     * @return A registration bean for the authentication filter.
     */
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

    /**
     * Registers the authorization filter.
     * <p>
     * This bean configures the {@link WebAuthorizationFilter} with URL patterns
     * from its properties class. The order is set to 2, ensuring it executes
     * after the authentication filter to enforce access control rules.
     * </p>
     *
     * @param filter     The {@link WebAuthorizationFilter} bean.
     * @param properties The {@link WebAuthorizationFilterProperties} bean.
     * @return A registration bean for the authorization filter.
     */
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
