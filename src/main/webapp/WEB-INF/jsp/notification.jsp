<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty success}">
        <div class="toast-container position-fixed top-3 end-0 p-3" style="z-index: 1050;">
            <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true" style="animation: fadeOut 5s ease-in forwards;">
                <div class="toast-header bg-success text-white">
                    <strong class="me-auto">Success!</strong>
                </div>
                <div class="toast-body">
                    ${success}
                </div>
            </div>
        </div>
</c:if>

<c:if test="${not empty message}">
    <div class="toast-container position-fixed top-3 end-0 p-3" style="z-index: 1050;">
        <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true" style="animation: fadeOut 5s ease-in forwards;">
            <div class="toast-header bg-danger text-white">
                <strong class="me-auto">Error!</strong>
            </div>
            <div class="toast-body">${message}</div>
        </div>
    </div>
</c:if>


