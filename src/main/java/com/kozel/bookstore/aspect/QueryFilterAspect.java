package com.kozel.bookstore.aspect;

import com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class QueryFilterAspect {

    @PersistenceContext
    private EntityManager manager;

    @Pointcut("@annotation(com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter)")
    public void filter(){}

    @Around("filter()")
    public Object manageDeletedFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = manager.unwrap(Session.class);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        QueryDeletedFilter filterAnnotation =
                signature.getMethod().getAnnotation(QueryDeletedFilter.class);

        boolean isDeleted = filterAnnotation.isDeleted();

        try {
            session.enableFilter("isDeletedFilter").setParameter("isDeleted", isDeleted);
            return joinPoint.proceed();
        } finally {
            session.disableFilter("isDeletedFilter");
        }
    }
}
