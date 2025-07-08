<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Filter Orders</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container text-center my-5">
        <h1 class="display-4">Orders List</h1>
        <p class="lead">Showing orders for user <span class="font-weight-bold font-italic">${login}</span></p>
    </div>

    <table class="table table-position">
        <thead class="table-light">
            <tr>
                <th>#</th>
                <th>Date</th>
                <th>User</th>
                <th>Total</th>
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
        <a href="/" class="btn btn-secondary btn-lg">Back to Home</a>
    </div>
</body>
</html>