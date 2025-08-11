package com.kozel.bookstore.service.annotation;

import com.kozel.bookstore.aspect.LoggingAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to mark methods or classes containing sensitive data,
 * such as passwords or personal information.
 * <p>
 * When this annotation is present, the {@link LoggingAspect} will log a simplified message,
 * omitting the method's arguments to prevent logging of sensitive data.
 * The annotation can be applied to an entire class to secure all its methods,
 * or to individual methods to secure only specific operations.
 * </p>
 *
 * @see LoggingAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SecuredLogging { }
