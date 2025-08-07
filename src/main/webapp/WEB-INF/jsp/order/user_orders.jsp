<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Orders List</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <div class="container text-center my-5">
        <h1 class="display-4">Orders List</h1>
        <c:if test="${isEmployee}">
             <p class="lead">
                Showing orders for user
                    <span class="font-weight-bold font-italic">${order.userLogin}</span>
             </p>
        </c:if>
    </div>

    <div class="d-flex justify-content-end mb-4 order-form-margin-right">
        <%@ include file="/WEB-INF/jsp/page_size_form.jsp" %>
    </div>

    <table class="table table-position">
        <thead class="table-light">
            <tr>
                <th>#</th>
                <th>Date</th>
                <c:if test="${isEmployee}">
                    <th>User</th>
                </c:if>
                <th>Total</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${page.content}" var="order" varStatus="counter">
                <tr>
                    <td><c:out value="${page.number * page.size + counter.index + 1}"/></td>
                    <td><c:out value="${order.date}"/></td>
                    <c:if test="${isEmployee}">
                        <td><c:out value="${order.userLogin}"/></td>
                    </c:if>
                    <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencyCode="USD"/></td>
                    <td><c:out value="${order.status}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${isEmployee}">
                                <a href="/orders/<c:out value='${order.id}'/>" class="btn btn-outline-primary btn-sm">
                                    Details
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="/ordered/<c:out value='${order.id}'/>" class="btn btn-outline-primary btn-sm">
                                    Details
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${order.userLogin eq sessionScope.user.login && order.status.name() eq 'PENDING'}">
                            <form action="/ordered/cancel/<c:out value="${order.id}"/>"
                                    method="post" style="display:inline;">
                                    <input type="hidden" name="page" value="${page.number}"/>
                                    <input type="hidden" name="size" value="${page.size}"/>
                                    <c:forEach items="${sortParams}" var="sortParam">
                                         <input type="hidden" name='sort' value='${sortParam}'/>
                                    </c:forEach>
                                <button type="submit" class="btn btn-outline-danger btn-sm">
                                    Cancel order
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${page.totalPages > 1}">
        <%@ include file="/WEB-INF/jsp/pagination_panel.jsp" %>
    </c:if>

    <div class="container text-center my-5">
        <a href="/" class="btn btn-secondary btn-lg">Back to Home</a>
    </div>
</body>
</html>