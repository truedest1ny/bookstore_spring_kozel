<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bookstore</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
    <style>
        .card-icon {
            margin-bottom: 1.5rem; /* Увеличенный отступ под иконкой */
        }
        .card-title {
            font-size: 1.25rem; /* Увеличенный размер текста */
            font-weight: 500;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <div class="text-center my-4">
        <img src="/images/icons/brand_icon.png" width="200" height="200" alt="Bookstore Logo" class="img-fluid">
    </div>

    <div class="container text-center my-5">
        <h1 class="display-4 top-text-size mb-4">
            You have reached the page of the book catalog "<span class="text-primary">Bookstore</span>"! Congratulations :)
        </h1>

        <c:if test="${empty sessionScope.user}">
            <p class="lead mb-5">
                The catalog includes a list of available books.
                To place an order you need to <a href="/login">authenticate yourself</a> :)
            </p>
        </c:if>

        <div class="d-flex justify-content-center flex-wrap gap-5">
            <div class="media position-relative" style="width: 180px;">
                <img src="/images/icons/book_icon.png" class="mr-3 card-icon" alt="Book icon" width="100" height="100">
                <div class="media-body text-left">
                    <h5 class="mt-0 card-title">Books</h5>
                    <a href="/books" class="stretched-link text-size"></a>
                </div>
            </div>

            <c:if test="${not empty sessionScope.user}">
                <div class="media position-relative" style="width: 180px;">
                    <img src="/images/icons/user_icon.png" class="mr-3 card-icon" alt="User icon" width="100" height="100">
                    <div class="media-body text-left">
                        <h5 class="mt-0 card-title">Users</h5>
                        <a href="/users" class="stretched-link text-size"></a>
                    </div>
                </div>

                <div class="media position-relative" style="width: 180px;">
                    <img src="/images/icons/order_icon.png" class="mr-3 card-icon" alt="Order icon" width="100" height="100">
                    <div class="media-body text-left">
                        <h5 class="mt-0 card-title">Orders</h5>
                        <a href="/orders" class="stretched-link text-size"></a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
