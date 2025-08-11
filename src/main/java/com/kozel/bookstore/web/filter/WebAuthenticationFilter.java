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

/**
 * A servlet filter for handling web authentication and access control based on URL patterns.
 * This filter intercepts incoming requests and categorizes them into public, private,
 * static, or authentication-specific resources. It ensures that only authenticated
 * users can access private resources, and prevents authenticated users from
 * accessing authentication-related pages like login.
 */
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

    /**
     * Initializes the filter by compiling regular expression patterns from properties.
     * This method runs after the bean has been constructed and populates the
     * lists of patterns for different resource types.
     */
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

    /**
     * Filters incoming HTTP requests to enforce access control.
     * The method checks the request path against defined patterns and performs
     * the appropriate action:
     * <ul>
     * <li>Allows access to static resources without further checks.</li>
     * <li>Redirects authenticated users away from authentication pages.</li>
     * <li>Allows public resources to be accessed by anyone.</li>
     * <li>Redirects unauthenticated users attempting to access private resources to the login page.</li>
     * <li>For undefined resources, forwards the request to a "not found" page.</li>
     * </ul>
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

    /**
     * Compiles an array of string patterns into a list of Java Pattern objects.
     *
     * @param patterns An array of regular expression strings.
     * @return A list of compiled Pattern objects.
     */
    private List<Pattern> compilePatterns(String[] patterns) {
        if (patterns == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(patterns)
                .map(Pattern::compile)
                .toList();
    }

    /**
     * Checks if the given path matches any of the public resource patterns.
     *
     * @param path The request path.
     * @return True if the path is a public resource, false otherwise.
     */
    private boolean isPublicResource(String path){
        return isMatchAny(path, publicPathPatterns);
    }

    /**
     * Checks if the given path matches any of the private resource patterns.
     *
     * @param path The request path.
     * @return True if the path is a private resource, false otherwise.
     */
    private boolean isPrivateResource(String path){
        return isMatchAny(path, privatePathPatterns);
    }

    /**
     * Checks if the given path matches any of the static resource patterns.
     *
     * @param path The request path.
     * @return True if the path is a static resource, false otherwise.
     */
    private boolean isStaticResource(String path){
        return isMatchAny(path, staticResourcePatterns);
    }

    /**
     * Checks if the given path matches any of the authentication-related resource patterns.
     *
     * @param path The request path.
     * @return True if the path is an authentication resource, false otherwise.
     */
    private boolean isAuthResource(String path){
        return isMatchAny(path, authPatterns);
    }

    /**
     * Forwards the request to a custom 404 "not found" page.
     * This method logs a warning and sets the response status to 404.
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param path The path that was not found.
     * @throws ServletException If a servlet-specific error occurs during the forward.
     * @throws IOException If an I/O error occurs during the forward.
     */
    private void moveToNotFoundPage(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        log.warn("Attempt to access undefined resource: {}", path);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.setAttribute("authFilterUrl", path);
        request.getRequestDispatcher("/not_found").forward(request, response);
    }
}
