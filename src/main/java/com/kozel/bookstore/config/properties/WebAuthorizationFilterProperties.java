package com.kozel.bookstore.config.properties;

import com.kozel.bookstore.service.dto.user.UserSessionDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "filter.authorization")
@Getter
@Setter
public class WebAuthorizationFilterProperties {
    private List<String> urlPatterns;
    private Map<String, List<String>> allowedPaths;
    private Map<String, List<String>> deniedPaths;
    private List<String> guestAllowedPaths;

    private Map<String, Set<UserSessionDto.Role>> roleMapping;
}
