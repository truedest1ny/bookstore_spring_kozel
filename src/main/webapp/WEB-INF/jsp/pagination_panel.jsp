<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<div class="d-flex justify-content-center align-items-center mt-4">
    <nav aria-label="Page navigation">
        <ul class="pagination mb-0">
            <li class="page-item <c:if test='${page.first}'>disabled</c:if>">
                <c:url value='${pageUrl}' var="prevPageUrl">
                    <c:param name='page' value='${page.number - 1}'/>
                    <c:param name='size' value='${page.size}'/>
                    <c:forEach items="${sortParams}" var="sortParam">
                         <c:param name='sort' value='${sortParam}'/>
                    </c:forEach>
                </c:url>
                <a class="page-link" href="${prevPageUrl}">Previous</a>
            </li>

            <c:if test="${page.totalPages > 0 && page.number > 2}">
                <li class="page-item">
                    <c:url value='${pageUrl}' var="firstPageUrl">
                        <c:param name='page' value='0'/>
                        <c:param name='size' value='${page.size}'/>
                    <c:forEach items="${sortParams}" var="sortParam">
                         <c:param name='sort' value='${sortParam}'/>
                    </c:forEach>
                    </c:url>
                    <a class="page-link" href="${firstPageUrl}">1</a>
                </li>
            </c:if>

            <c:if test="${page.number > 3}">
                <li class="page-item disabled"><span class="page-link">...</span></li>
            </c:if>

            <c:set var="startPage" value="${page.number > 2 ? page.number - 2 : 0}"/>
            <c:set var="endPage" value="${page.number < page.totalPages - 3 ? page.number + 2 : page.totalPages - 1}"/>

            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                <li class="page-item <c:if test="${page.number eq i}">active</c:if>">
                    <c:url value='${pageUrl}' var="midPageUrl">
                        <c:param name='page' value='${i}'/>
                        <c:param name='size' value='${page.size}'/>
                    <c:forEach items="${sortParams}" var="sortParam">
                         <c:param name='sort' value='${sortParam}'/>
                    </c:forEach>
                    </c:url>
                    <a class="page-link" href="${midPageUrl}">${i + 1}</a>
                </li>
            </c:forEach>

            <c:if test="${page.number < page.totalPages - 4}">
                <li class="page-item disabled"><span class="page-link">...</span></li>
            </c:if>

            <c:if test="${page.totalPages > 1 && page.number < page.totalPages - 3}">
                <li class="page-item <c:if test="${page.number eq page.totalPages - 1}">active</c:if>">
                    <c:url value='${pageUrl}' var="lastPageUrl">
                        <c:param name='page' value='${page.totalPages - 1}'/>
                        <c:param name='size' value='${page.size}'/>
                    <c:forEach items="${sortParams}" var="sortParam">
                         <c:param name='sort' value='${sortParam}'/>
                    </c:forEach>
                    </c:url>
                    <a class="page-link" href="${lastPageUrl}">${page.totalPages}</a>
                </li>
            </c:if>

            <li class="page-item <c:if test="${page.last}">disabled</c:if>">
                <c:url value='${pageUrl}' var="nextPageUrl">
                    <c:param name='page' value='${page.number + 1}'/>
                    <c:param name='size' value='${page.size}'/>
                    <c:forEach items="${sortParams}" var="sortParam">
                         <c:param name='sort' value='${sortParam}'/>
                    </c:forEach>
                </c:url>
                <a class="page-link" href="${nextPageUrl}">Next</a>
            </li>
        </ul>
    </nav>
</div>