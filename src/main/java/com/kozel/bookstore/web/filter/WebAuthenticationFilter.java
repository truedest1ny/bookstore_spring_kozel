package com.kozel.bookstore.web.filter;

import com.kozel.bookstore.config.properties.WebAuthenticationFilterProperties;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebAuthenticationFilter
        extends HttpFilter implements PathMatcher {

    private final WebAuthenticationFilterProperties properties;

    private final List<Pattern> publicPathPatterns = new ArrayList<>();
    private final List<Pattern> privatePathPatterns = new ArrayList<>();
    private final List<Pattern> staticResourcePatterns = new ArrayList<>();
    private final List<Pattern> authPatterns = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.publicPathPatterns.addAll(
                compilePatterns(properties.getPublicPaths()));
        this.privatePathPatterns.addAll(
                compilePatterns(properties.getPrivatePaths()));
        this.authPatterns.addAll(
                compilePatterns(properties.getAuthPaths()));
        this.staticResourcePatterns.addAll(
                compilePatterns(properties.getStaticResources()));
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



    private List<Pattern> compilePatterns(String[] patterns) {
        if (patterns == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(patterns)
                .map(Pattern::compile)
                .toList();
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
        return isMatchAny(path, authPatterns);
    }

    private void moveToNotFoundPage(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        log.warn("Attempt to access undefined resource: {}", path);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.setAttribute("authFilterUrl", path);
        request.getRequestDispatcher("/not_found").forward(request, response);
    }
}
