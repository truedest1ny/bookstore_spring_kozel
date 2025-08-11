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

/**
 * An aspect that dynamically enables and disables a Hibernate filter for soft-deleted entities.
 * This aspect is triggered by methods annotated with {@link QueryDeletedFilter}. It enables the
 * "isDeletedFilter" and sets its parameter based on the annotation's value before the method
 * execution. This ensures that queries are automatically filtered to include or exclude
 * soft-deleted entities. The filter is guaranteed to be disabled in a finally block
 * after the method completes, regardless of the outcome.
 *
 * <p>
 * This aspect relies on a Hibernate filter named "isDeletedFilter" being defined.
 * This filter is initialized at the package level in {@code com.kozel.bookstore.data.entity}
 * via the {@link org.hibernate.annotations.FilterDef} annotation.
 * </p>
 *
 * @see QueryDeletedFilter
 */
@Component
@Aspect
public class QueryFilterAspect {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Defines a pointcut for methods annotated with the {@link QueryDeletedFilter} annotation.
     * This pointcut identifies all methods where the soft-delete filter logic should be applied.
     */
    @Pointcut("@annotation(com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter)")
    public void filter(){}

    /**
     * Around advice that manages the Hibernate soft-delete filter.
     * Before the execution of the target method, this advice unwraps the EntityManager to a Hibernate Session,
     * enables the "isDeletedFilter", and sets its "isDeleted" parameter based on the annotation's value.
     * The original method is then executed. After the method completes (either successfully or with an exception),
     * the filter is reliably disabled in the finally block.
     *
     * @param joinPoint The join point object, which allows proceeding with the original method call.
     * @return The result of the original method execution.
     * @throws Throwable if the original method call throws an exception.
     */
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
