package com.kozel.bookstore.web.filter;

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
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class WebAuthenticationFilter extends HttpFilter {

    private List<Pattern> publicPathPatterns = new ArrayList<>();
    private List<Pattern> privatePathPatterns = new ArrayList<>();
    private List<Pattern> staticResourcePatterns = new ArrayList<>();
    private List<Pattern> authPaths = new ArrayList<>();

    @Override
    public void init(FilterConfig config) throws ServletException {
        initPatterns(config, "publicPaths", publicPathPatterns);
        initPatterns(config, "privatePaths", privatePathPatterns);
        initPatterns(config, "authPaths", authPaths);
        initPatterns(config, "staticResources", staticResourcePatterns);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        boolean isAuthenticated =
                session != null && session.getAttribute("user") != null;

        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (isAuthResource(path)) {
            if (isAuthenticated) {
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                chain.doFilter(request, response);
            }
            return;
        }

        if (isPublicResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (isPrivateResource(path)) {
            if (isAuthenticated) {
                chain.doFilter(request, response);
            } else {
                HttpSession messageSession = request.getSession(true);
                messageSession.setAttribute("loginMessage",
                        "Authorization is required to access resources. Please Sign in");
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return;
        }
        moveToNotFoundPage(request, response, path);
    }



    private void initPatterns(FilterConfig config, String paramName, List<Pattern> patterns) {
        String paramValue = config.getInitParameter(paramName);
        if (paramValue != null) {
            for (String pattern : paramValue.split("\\s*,\\s*")) {
                patterns.add(Pattern.compile(pattern));
            }
        }
    }

    private boolean isMatchAny(String path, List<Pattern> patterns) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPublicResource(String path){
        return isMatchAny(path, publicPathPatterns);
    }

    private boolean isPrivateResource(String path){
        return isMatchAny(path, privatePathPatterns);
    }

    private boolean isStaticResource(String path){
        return isMatchAny(path, staticResourcePatterns);
    }

    private boolean isAuthResource(String path){
        return isMatchAny(path, authPaths);
    }

    private void moveToNotFoundPage(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        log.warn("Attempt to access undefined resource: {}", path);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.setAttribute("authFilterUrl", path);
        request.getRequestDispatcher("/not_found").forward(request, response);
    }
}
