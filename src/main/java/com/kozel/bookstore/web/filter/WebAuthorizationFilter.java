package com.kozel.bookstore.web.filter;

import com.kozel.bookstore.service.dto.user.UserSessionDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class WebAuthorizationFilter
        extends HttpFilter implements PathMatcher {

    private final Map<Pattern, Set<UserSessionDto.Role>> allowedRoleConstraints = new HashMap<>();
    private final Map<Pattern, Set<UserSessionDto.Role>> deniedRoleConstraints = new HashMap<>();
    private final List<Pattern> guestAllowedPaths = new ArrayList<>();

    @Override
    public void init(FilterConfig config) throws ServletException {
        Enumeration<String> params = config.getInitParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();

            if (paramName.equals("guestAllowedPaths")) {
                String paths = config.getInitParameter(paramName);
                if (paths != null) {
                    for (String path : paths.split("\\s*,\\s*")) {
                        guestAllowedPaths.add(Pattern.compile(path.trim()));
                    }
                }
            } else if (paramName.endsWith("Paths")) {
                String roleType = paramName.replace("Paths", "");
                String rolesParam = config.getInitParameter(roleType + "Roles");

                if (rolesParam != null) {
                    Set<UserSessionDto.Role> roles = Arrays.stream(rolesParam.split("\\s*,\\s*"))
                            .map(String::trim)
                            .map(this::toRole)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    String paths = config.getInitParameter(paramName);
                    for (String path : paths.split("\\s*,\\s*")) {
                        Pattern pattern = Pattern.compile(path.trim());
                        if (roleType.startsWith("forbidden")) {
                            deniedRoleConstraints.put(pattern, roles);
                        } else {
                            allowedRoleConstraints.put(pattern, roles);
                        }
                    }
                }
            }
        }

        if (allowedRoleConstraints.isEmpty()
                && deniedRoleConstraints.isEmpty()
                && guestAllowedPaths.isEmpty()) {
            throw new ServletException("No authorization rules configured");
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


    private UserSessionDto.Role toRole(String roleName) {
        try {
            return UserSessionDto.Role.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            log.error("Unknown role: {}", roleName);
            return null;
        }
    }

    private void accessDeniedLog(UserSessionDto user, String path) {
        log.warn("Access denied for {} (role {}) to {}",
                user.getLogin(), user.getRole(), path);
    }
}
