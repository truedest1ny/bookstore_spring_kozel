<c:if test="${not empty success}">
        <div class="toast-container position-fixed top-3 end-0 p-3" style="z-index: 1050;">
            <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true" style="animation: fadeOut 5s ease-in forwards;">
                <div class="toast-header bg-success text-white">
                    <strong class="me-auto">Success!</strong>
                </div>
                <div class="toast-body">
                    <c:out value="${success}"/>
                </div>
            </div>
        </div>
</c:if>

<c:if test="${not empty error}">
    <div class="toast-container position-fixed top-3 end-0 p-3" style="z-index: 1050;">
        <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true" style="animation: fadeOut 5s ease-in forwards;">
            <div class="toast-header bg-danger text-white">
                <strong class="me-auto">Error!</strong>
            </div>
            <div class="toast-body">
                <c:out value="${error}"/>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty warn}">
    <div class="toast-container position-fixed top-3 end-0 p-3" style="z-index: 1050;">
        <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true" style="animation: fadeOut 5s ease-in forwards;">
            <div class="toast-header bg-warning text-dark">
                <strong class="me-auto">Warning!</strong>
            </div>
            <div class="toast-body">
                <c:out value="${warn}"/>
            </div>
        </div>
    </div>
</c:if>


