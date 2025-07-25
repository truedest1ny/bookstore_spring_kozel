<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><c:out value="${book.name}"/></title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <c:set var="isNotManager" value=
          "${empty sessionScope.user || (not empty sessionScope.user && sessionScope.user.role.name() ne 'MANAGER')}"/>
    <c:set var="isEmployee"
            value="${not empty sessionScope.user && sessionScope.user.role.name() ne 'CUSTOMER'}"/>

    <div class="profile-container">
        <h1 class="display-4 mb-4">
            Book Details
                <c:if test="${isEmployee}">
                        [ID: <c:out value="${book.id}"/>]
                </c:if>
        </h1>

        <table class="profile-table">
            <tbody>
                <tr>
                    <td class="profile-label">Name:</td>
                    <td class="profile-value"><c:out value="${book.name}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">ISBN:</td>
                    <td class="profile-value"><c:out value="${book.isbn}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">Cover:</td>
                    <td class="profile-value"><c:out value="${book.cover}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">Author:</td>
                    <td class="profile-value"><c:out value="${book.author}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">Published Year:</td>
                    <td class="profile-value"><c:out value="${book.publishedYear}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">Price:</td>
                    <td class="profile-value">
                        <fmt:formatNumber value="${book.price}" type="currency" currencyCode="USD"/>
                    </td>
                </tr>
            </tbody>
        </table>

        <c:if test=
            "${isNotManager}">
                    <div class="order-form-section">
                        <h5>Order this book</h5>
                        <form action="/cart/add" method="post" class="form-inline-custom">
                            <input type="hidden" name="bookId" value="${book.id}" />
                            <label for="quantity" class="form-label-custom">Enter quantity</label>
                            <div class="form-group-custom">
                                <input type="number" id="quantity" name="quantity" class="form-control" value="1" min="1" required />
                            </div>
                            <button type="submit" class="btn btn-sm btn-outline-success">Add to cart</button>
                        </form>
                    </div>
                </c:if>

        <div class="mt-4">
            <a href="/books" class="btn btn-primary button-margin">Back to Books</a>
            <a href="/" class="btn btn-secondary button-margin">Back to Home</a>
        </div>
    </div>
</body>
</html>