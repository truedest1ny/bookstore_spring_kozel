<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <div class="container text-center my-5">
        <p class="display-4">Orders list</p>
        <p class="lead">The catalog includes a list of orders made by users.</p>

        <c:if test="${not empty sessionScope.user and sessionScope.user.role.name() != 'CUSTOMER'}">
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
                    <td><c:out value="${order.id}"/></td>
                    <td><c:out value="${order.date}"/></td>
                    <td><c:out value="${order.userLogin}"/></td>
                    <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencyCode="USD"/></td>
                    <td><c:out value="${order.status}"/></td>
                    <td>
                        <a href="/orders/<c:out value='${order.id}'/>" class="btn btn-primary btn-sm">
                            Details
                        </a>
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