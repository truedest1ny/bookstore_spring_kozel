package com.kozel.bookstore.web.filter;

import java.util.List;
import java.util.regex.Pattern;

public interface PathMatcher {

    default boolean isMatchAny(String path, List<Pattern> patterns) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }
}
