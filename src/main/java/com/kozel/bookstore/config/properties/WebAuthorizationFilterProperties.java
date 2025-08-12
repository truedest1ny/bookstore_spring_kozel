package com.kozel.bookstore.config.properties;

import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.web.filter.WebAuthorizationFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A properties class that holds configuration for the web authorization filter.
 * This class is annotated with {@link ConfigurationProperties @ConfigurationProperties}
 * to bind properties from the application configuration files (e.g., application.yml or application.properties)
 * that have the prefix {@code "filter.authorization"}.
 *
 * <p>
 * It defines URL patterns and specific path lists to configure the security filter chain's
 * authorization rules, mapping user roles to allowed or denied access for specific paths.
 * The paths are specified using regular expression patterns.
 * </p>
 *
 * @see WebAuthorizationFilter
 */
@Component
@ConfigurationProperties(prefix = "filter.authorization")
@Getter
@Setter
public class WebAuthorizationFilterProperties {

    /**
     * A list of URL patterns to which the authorization filter should be applied.
     * The filter will only process requests whose path matches one of these patterns.
     */
    private List<String> urlPatterns;

    /**
     * A map for role-based authorization rules. The keys of the map are logical role names (e.g., "admin", "manager")
     * which are mapped to a set of actual user roles via the {@link #roleMapping} property.
     * The values are lists of regex path patterns that are explicitly allowed for the corresponding roles.
     * For example, {@code allowed-paths.manager} defines which paths are accessible to managers.
     */
    private Map<String, List<String>> allowedPaths;

    /**
     * A map for defining explicit denial rules. The keys are logical role names (e.g., "manager-denied")
     * which are mapped to user roles via {@link #roleMapping}.
     * The values are lists of regex path patterns that are explicitly denied for the corresponding roles.
     */
    private Map<String, List<String>> deniedPaths;

    /**
     * A list of regex path patterns that are accessible to unauthenticated users ("guests").
     * These paths do not require an active user session.
     */
    private List<String> guestAllowedPaths;

    /**
     * A map that translates logical role names (used in {@link #allowedPaths} and {@link #deniedPaths})
     * into sets of actual {@link UserSessionDto.Role} enum values.
     */
    private Map<String, Set<UserSessionDto.Role>> roleMapping;
}
