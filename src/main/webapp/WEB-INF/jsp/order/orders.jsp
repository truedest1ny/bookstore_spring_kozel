<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Orders</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <div class="container text-center my-5">
        <p class="display-4">Orders list</p>
        <p class="lead">The catalog includes a list of orders made by users.</p>

        <c:if test="${not empty sessionScope.user
                        && sessionScope.user.role.name() ne 'CUSTOMER'}">
            <a href="/orders/find_by_user" class="btn btn-primary btn-lg">
                Find orders by user
            </a>
        </c:if>
    </div>

    <table class="table table-position">
        <thead class="table-light">
            <tr>
                <th>#</th>
                <th>Date</th>
                <th>User Login</th>
                <th>Total Price</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="order" varStatus="counter">
                <tr>
                    <td><c:out value="${counter.index + 1}"/></td>
                    <td><c:out value="${order.date}"/></td>
                    <td><c:out value="${order.userLogin}"/></td>
                    <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencyCode="USD"/></td>
                    <td><c:out value="${order.status}"/></td>
                    <td>
                        <a href="/orders/<c:out value='${order.id}'/>" class="btn btn-outline-primary btn-sm">
                            Details
                        </a>

                        <c:if test="${order.status.name() eq 'PENDING'}">
                            <form action="/orders/approve/<c:out value="${order.id}"/>"
                                    method="post" style="display:inline;">
                                <button type="submit" class="btn btn-outline-success btn-sm">
                                    Approve order
                                </button>
                            </form>
                        </c:if>

                        <c:if test=
                            "${order.status.name() eq 'CANCELLED'
                                    && sessionScope.user.role.name() ne 'MANAGER'}">
                            <form action="/orders/archive/<c:out value="${order.id}"/>"
                                    method="post" style="display:inline;">
                                <button type="submit" class="btn btn-outline-danger btn-sm">
                                    Archive order
                                </button>
                            </form>
                        </c:if>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="container text-center my-5">
        <a href="/" class="btn btn-secondary btn-lg button-margin">
            Back to mainpage
        </a>
    </div>
</body>
</html>