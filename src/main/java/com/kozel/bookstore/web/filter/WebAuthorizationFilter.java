package com.kozel.bookstore.web.filter;

import com.kozel.bookstore.config.properties.WebAuthorizationFilterProperties;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * A servlet filter for enforcing role-based access control to web resources.
 * <p>This filter intercepts incoming requests and, based on a set of configured
 * rules, either grants or denies access to the resource. The rules are defined
 * by URL patterns and associated user roles, which are loaded from a properties file.
 * The filter supports three main types of rules:</p>
 * <ul>
 * <li><b>Denied Paths:</b> Explicitly denies access to a path for specific roles.</li>
 * <li><b>Allowed Paths:</b> Explicitly allows access to a path for specific roles.
 * If a path is in this category, only the specified roles can access it.</li>
 * <li><b>Guest Allowed Paths:</b> Permits access to unauthenticated users.</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebAuthorizationFilter
        extends HttpFilter implements PathMatcher {

    private final WebAuthorizationFilterProperties properties;

    private final Map<Pattern, Set<UserSessionDto.Role>> allowedRoleConstraints = new HashMap<>();
    private final Map<Pattern, Set<UserSessionDto.Role>> deniedRoleConstraints = new HashMap<>();
    private final List<Pattern> guestAllowedPaths = new ArrayList<>();

    /**
     * Initializes the filter by compiling URL patterns and role-based access rules.
     * <p>This method is called after the bean is constructed and populates the
     * internal maps and lists with rules defined in the application properties.</p>
     */
    @PostConstruct
    public void init() {
        if (properties.getAllowedPaths() != null) {
            properties.getAllowedPaths().forEach((roleName, paths) -> {
                Set<UserSessionDto.Role> roles = getRoles(roleName);
                paths.forEach(path -> allowedRoleConstraints.put(
                        Pattern.compile(path), roles));
            });
        }

        if (properties.getDeniedPaths() != null) {
            properties.getDeniedPaths().forEach((roleName, paths) -> {
                Set<UserSessionDto.Role> roles = getRoles(roleName);
                paths.forEach(path -> deniedRoleConstraints.put(
                        Pattern.compile(path), roles));
            });
        }

        if (properties.getGuestAllowedPaths() != null) {
            properties.getGuestAllowedPaths().forEach(
                    path -> guestAllowedPaths.add(Pattern.compile(path)));
        }

        if (allowedRoleConstraints.isEmpty()
                && deniedRoleConstraints.isEmpty()
                && guestAllowedPaths.isEmpty()) {
            log.warn("No authorization rules configured for WebAuthorizationFilter");
        }
    }

    /**
     * Filters incoming HTTP requests to enforce authorization rules.
     * <p>The authorization logic is applied in a specific order:</p>
     * <ol>
     * <li>Check against <b>denied paths</b>: If the path matches a denied pattern for the user's role,
     * access is immediately forbidden (403 Forbidden).</li>
     * <li>Check against <b>guest-allowed paths</b>: If the user is unauthenticated and the path is
     * guest-allowed, access is granted.</li>
     * <li>Handle unauthenticated users: If the user is not authenticated, and the path is not
     * guest-allowed, the user is redirected to the login page.</li>
     * <li>Check against <b>allowed paths</b>: If the path matches an allowed pattern, access is
     * granted only if the user's role is in the list of allowed roles. If the path is protected
     * by an allowed rule but the user's role is not a match, access is forbidden.</li>
     * <li>Default behavior: If the path is not protected by any allowed rule, access is granted.</li>
     * </ol>
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param chain The FilterChain for passing the request to the next filter.
     * @throws IOException If an I/O error occurs.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        UserSessionDto user = (session != null) ? (UserSessionDto) session.getAttribute("user") : null;

        for (Map.Entry<Pattern, Set<UserSessionDto.Role>> entry : deniedRoleConstraints.entrySet()) {
            if (entry.getKey().matcher(path).matches()) {
                if (user != null && entry.getValue().contains(user.getRole())) {
                    accessDeniedLog(user, path);
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        }

        boolean isGuestAllowedPath = isMatchAny(path, guestAllowedPaths);

        if (user == null && isGuestAllowedPath) {
            log.debug("Granting access to unauthenticated user for guest-allowed path: {}", path);
            chain.doFilter(request, response);
            return;
        }

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean isAuthorized = false;
        boolean pathProtected = false;

        for (Map.Entry<Pattern, Set<UserSessionDto.Role>> entry : allowedRoleConstraints.entrySet()) {
            if (entry.getKey().matcher(path).matches()) {
                pathProtected = true;
                if (entry.getValue().contains(user.getRole())) {
                    isAuthorized = true;
                    break;
                }
            }
        }

        if (!pathProtected || isAuthorized) {
            chain.doFilter(request, response);
            return;
        } else {
            accessDeniedLog(user, path);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

    }

    /**
     * Retrieves the set of roles corresponding to a given role name from the properties.
     *
     * @param roleName The name of the role group (e.g., "admins").
     * @return A set of UserSessionDto.Role objects.
     */
    private Set<UserSessionDto.Role> getRoles(String roleName) {
        return properties.getRoleMapping().getOrDefault(roleName, Collections.emptySet());
    }

    /**
     * Logs access denied warning message.
     *
     * @param user The user who was denied access.
     * @param path The path they attempted to access.
     */
    private void accessDeniedLog(UserSessionDto user, String path) {
        log.warn("Access denied for {} (role {}) to {}",
                user.getLogin(), user.getRole(), path);
    }
}
