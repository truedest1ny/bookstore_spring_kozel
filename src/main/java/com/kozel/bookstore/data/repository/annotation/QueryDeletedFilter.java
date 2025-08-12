package com.kozel.bookstore.data.repository.annotation;

import com.kozel.bookstore.aspect.QueryFilterAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to mark repository methods that require the Hibernate soft-delete filter.
 * <p>
 * This annotation signals the {@link QueryFilterAspect} to automatically enable and configure
 * the "isDeletedFilter" for the duration of the method call. By default, it filters out
 * soft-deleted entities.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryDeletedFilter {

    /**
     * Determines whether soft-deleted entities should be included in the query results.
     * <ul>
     * <li>{@code false} (default): Filters out soft-deleted entities.</li>
     * <li>{@code true}: Includes only soft-deleted entities.</li>
     * </ul>
     *
     * @return {@code true} to include deleted entities, {@code false} otherwise.
     */
    boolean isDeleted() default false;
}
