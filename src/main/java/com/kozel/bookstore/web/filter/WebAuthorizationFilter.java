package com.kozel.bookstore.web.filter;

import com.kozel.bookstore.service.dto.UserSessionDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class WebAuthorizationFilter extends HttpFilter {

    private final Map<Pattern, Set<UserSessionDto.Role>> roleConstraints = new HashMap<>();

    @Override
    public void init(FilterConfig config) throws ServletException {
        Enumeration<String> params = config.getInitParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();

            if (paramName.endsWith("Paths")) {
                String roleType = paramName.replace("Paths", "");
                String rolesParam = config.getInitParameter(roleType + "Roles");

                if (rolesParam != null) {
                    Set<UserSessionDto.Role> roles = Arrays.stream(rolesParam.split(","))
                            .map(String::trim)
                            .map(this::toRole)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    String paths = config.getInitParameter(paramName);
                    for (String path : paths.split(",")) {
                        Pattern pattern = Pattern.compile(path.trim());
                        roleConstraints.put(pattern, roles);
                    }
                }
            }
        }

        if (roleConstraints.isEmpty()) {
            throw new ServletException("No authorization rules configured");
        }
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getServletPath();
        UserSessionDto user = (UserSessionDto) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean isAuthorized = false;
        boolean pathProtected = false;

        for (Map.Entry<Pattern, Set<UserSessionDto.Role>> entry : roleConstraints.entrySet()) {
            if (entry.getKey().matcher(path).matches()) {
                pathProtected = true;
                if (entry.getValue().contains(user.getRole())) {
                    isAuthorized = true;
                    break;
                }
            }
        }

        if (!pathProtected) {
            chain.doFilter(request, response);
            return;
        }

        if (isAuthorized) {
            chain.doFilter(request, response);
        } else {
            log.warn("Access denied for {} (role {}) to {}",
                    user.getLogin(), user.getRole(), path);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
}
