package com.kozel.bookstore.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "filter.authentication")
@Getter
@Setter
public class WebAuthenticationFilterProperties {
    private List<String> urlPatterns;
    private String[] publicPaths;
    private String[] privatePaths;
    private String[] authPaths;
    private String[] staticResources;
}
