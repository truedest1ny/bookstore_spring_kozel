package com.kozel.bookstore.config.properties;

import com.kozel.bookstore.web.filter.WebAuthenticationFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A properties class that holds configuration for the web authentication filter.
 * This class is annotated with {@link ConfigurationProperties @ConfigurationProperties}
 * to bind properties from the application configuration files (e.g., application.yml or application.properties)
 * that have the prefix {@code "filter.authentication"}.
 * <p>
 * It defines URL patterns and specific path lists to configure the security filter chain.
 * </p>
 *
 * @see WebAuthenticationFilter
 */
@Component
@ConfigurationProperties(prefix = "filter.authentication")
@Getter
@Setter
public class WebAuthenticationFilterProperties {

    /**
     * A list of URL patterns to which the authentication filter should be applied.
     * For example, it might be set to {@code ["/*"]} to apply the filter to all requests.
     */
    private List<String> urlPatterns;

    /**
     * An array of paths that are publicly accessible without any authentication.
     */
    private String[] publicPaths;

    /**
     * An array of paths that require authentication for access.
     */
    private String[] privatePaths;

    /**
     * An array of paths related to the authentication process (e.g., login, logout).
     */
    private String[] authPaths;

    /**
     * An array of paths for static resources (e.g., CSS, JavaScript, images) that are publicly available.
     */
    private String[] staticResources;
}
