package com.kozel.bookstore.web.filter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * A functional interface for path matching utility methods.
 */
public interface PathMatcher {

    /**
     * Checks if a given path string matches any of the provided regex patterns.
     *
     * @param path The path to check.
     * @param patterns A list of compiled regex patterns.
     * @return True if a match is found, false otherwise.
     */
    default boolean isMatchAny(String path, List<Pattern> patterns) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(path).matches()) {
                return true;
            }
        }
        return false;
    }
}
