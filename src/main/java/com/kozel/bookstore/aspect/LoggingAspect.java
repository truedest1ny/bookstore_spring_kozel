package com.kozel.bookstore.aspect;

import com.kozel.bookstore.service.annotation.SecuredLogging;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * An aspect for logging method entry in the service layer.
 * This aspect provides comprehensive logging for methods within the service package.
 * It differentiates between secure and non-secure methods based on the {@link SecuredLogging} annotation,
 * preventing sensitive data from being logged in secure contexts.
 *
 * @see SecuredLogging
 */
@Component
@Aspect
@Slf4j
public class LoggingAspect {

    /**
     * Defines a pointcut for all public methods in any class within the service package.
     * This pointcut is used to apply the logging advice to the entire service layer.
     */
    @Pointcut("execution(public * com.kozel.bookstore.service..*.*(..))")
    public void service(){}

    /**
     * Logs the entry of a method before its execution.
     * The method determines if the target method or its declaring class is annotated with {@link SecuredLogging}.
     * If the method is marked as secure, it logs a simplified message without method arguments to prevent
     * logging of sensitive data like passwords. Otherwise, it logs the method name and all its arguments.
     *
     * @param joinPoint The join point object, which provides context about the advised method's execution.
     */
    @Before("service()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();

        Class<?> declaringClass = signature.getDeclaringType();

        boolean isSecure = signature.getMethod().isAnnotationPresent(SecuredLogging.class)
                || declaringClass.isAnnotationPresent(SecuredLogging.class);

        if (isSecure) {
            log.info("Entering secure method: {}.{}()", className, methodName);
        } else {
            Object[] args = joinPoint.getArgs();
            log.info("Entering method: {}.{}() with arguments: {}",
                    className, methodName, Arrays.toString(args));
        }
    }
}
