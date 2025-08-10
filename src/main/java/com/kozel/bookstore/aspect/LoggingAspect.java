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

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(public * com.kozel.bookstore.service..*.*(..))")
    public void service(){}

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
