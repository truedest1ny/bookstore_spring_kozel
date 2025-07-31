<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Your Cart</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <div class="container text-center my-5">
        <h1 class="display-4">Your Shopping Cart</h1>

        <c:choose>
            <c:when test="${empty sessionScope.sessionCart.items}">
                <p class="lead">Your cart is empty. Start shopping now!</p>
                <a href="/books" class="btn btn-primary btn-lg mt-3">Go to Books Catalog</a>
            </c:when>
            <c:otherwise>
                <h2 class="display-5 mb-4"><i>Items in your cart:</i></h2>

                <table class="table table-position table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>#</th> <%-- New column for serial number --%>
                            <th>Book</th>
                            <th>Author</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Subtotal</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.sessionCart.items}" var="item" varStatus="loop">
                            <tr>
                                <td><a href="/books/${item.book.id}"><c:out value="${loop.index + 1}"/></a></td> <%-- Serial number as a link --%>
                                <td><c:out value="${item.book.name}"/></td>
                                <td><c:out value="${item.book.author}"/></td>
                                <td><c:out value="${item.quantity}"/></td>
                                <td><fmt:formatNumber value="${item.book.price}" type="currency" currencyCode="USD"/></td>
                                <td><fmt:formatNumber value="${item.price}" type="currency" currencyCode="USD"/></td>
                                <td>
                                    <form action="/cart/remove" method="post" class="d-inline">
                                        <input type="hidden" name="bookId" value="${item.book.id}" />
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="5" class="text-end">Total Price:</td>
                            <td>
                                <fmt:formatNumber value="${sessionScope.sessionCart.totalPrice}" type="currency" currencyCode="USD"/>
                            </td>
                            <td class="clear-cart-btn-cell">
                                <form action="/cart/clear" method="post" class="d-inline">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Clear cart</button>
                                </form>
                            </td>
                        </tr>
                    </tfoot>
                </table>

                <div class="action-buttons-container">
                    <a href="/books" class="btn btn-primary">Go to Books Catalog</a>
                    <form action="/ordered/add" method="post" class="d-inline">
                        <button type="submit" class="btn btn-success">Make an order</button>
                    </form>
                    <a href="/" class="btn btn-secondary">Go to Home Page</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>