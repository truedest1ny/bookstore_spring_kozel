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

@Component
@RequiredArgsConstructor
@Slf4j
public class WebAuthorizationFilter
        extends HttpFilter implements PathMatcher {

    private final WebAuthorizationFilterProperties properties;

    private final Map<Pattern, Set<UserSessionDto.Role>> allowedRoleConstraints = new HashMap<>();
    private final Map<Pattern, Set<UserSessionDto.Role>> deniedRoleConstraints = new HashMap<>();
    private final List<Pattern> guestAllowedPaths = new ArrayList<>();

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

    private Set<UserSessionDto.Role> getRoles(String roleName) {
        return properties.getRoleMapping().getOrDefault(roleName, Collections.emptySet());
    }

    private void accessDeniedLog(UserSessionDto user, String path) {
        log.warn("Access denied for {} (role {}) to {}",
                user.getLogin(), user.getRole(), path);
    }
}
