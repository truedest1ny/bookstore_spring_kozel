<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Order #${order.id}</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container text-center my-5">
        <h1 class="display-4">Order #${order.id}</h1>

        <div class="order-info mb-5">
            <table class="table table-bordered text-font-size mx-auto" style="max-width: 500px;">
                <tbody>
                    <tr>
                        <td class="font-weight-bold">Date:</td>
                        <td>${order.date}</td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">User login:</td>
                        <td><a href="/orders/${order.user.id}">${order.user.login}</a></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Status:</td>
                        <td>${order.status}</td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Total price:</td>
                        <td>${order.totalPrice}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <h2 class="display-5 mb-4"><i>Items in order:</i></h2>

        <table class="table table-position table-hover">
            <thead class="table-light">
                <tr>
                    <th>Book</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${items}" var="item" varStatus="counter">
                    <tr>
                        <td>${item.book.name}</td>
                        <td>${item.quantity}</td>
                        <td>${item.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="action-buttons mt-5">
            <a href="/orders" class="btn btn-primary btn-lg button-margin">Back to Orders</a>
            <a href="/" class="btn btn-secondary btn-lg button-margin">Back to Home</a>
        </div>
    </div>
</body>
</html>