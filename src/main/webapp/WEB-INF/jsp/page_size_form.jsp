<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<form action="${pageUrl}" method="get" class="d-flex align-items-center">
    <label for="pageSize" class="me-2">Items per page:</label>

    <select name="size" id="pageSize" class="form-select w-auto">
        <option value="10" <c:if test="${page.size == 10}">selected</c:if>>10</option>
        <option value="20" <c:if test="${page.size == 20}">selected</c:if>>20</option>
        <option value="50" <c:if test="${page.size == 50}">selected</c:if>>50</option>
    </select>

    <button type="submit" class="btn btn-outline-primary ms-2">Go</button>

    <input type="hidden" name="page" value="0"/>
    <c:forEach items="${sortParams}" var="sortParam">
        <input type="hidden" name="sort" value="${sortParam}"/>
    </c:forEach>
</form>