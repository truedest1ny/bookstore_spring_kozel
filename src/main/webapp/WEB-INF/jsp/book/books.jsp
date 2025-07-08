<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Books Catalog</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <c:set var="isEmployee"
        value="${not empty sessionScope.user && sessionScope.user.role.name() ne 'CUSTOMER'}"/>

    <div class="container my-5">
        <div class="text-center">
            <h1 class="display-4 mb-3">Books Catalog</h1>
            <p class="lead text-muted mb-4">List of available books in our library</p>

            <c:if test="${isEmployee}">
                <a href="/books/add" class="btn btn-primary btn-lg mb-4">
                    Add New Book
                </a>
            </c:if>
        </div>

        <table class="table table-position wide-table">
            <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Year</th>
                    <c:if test="${isEmployee}">
                        <th colspan="2">Actions</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td><a href="/books/<c:out value='${book.id}'/>"><c:out value="${book.id}"/></a></td>
                        <td><c:out value="${book.name}"/></td>
                        <td><c:out value="${book.author}"/></td>
                        <td><c:out value="${book.publishedYear}"/></td>

                        <c:if test="${isEmployee}">
                            <td>
                                <a href="/books/edit/<c:out value='${book.id}'/>" class="btn btn-sm btn-outline-primary">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <form action="/books/delete/<c:out value='${book.id}'/>" method="post" class="d-inline">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="text-center mt-4">
            <a href="/" class="btn btn-secondary btn-lg">
                Back to Home
            </a>
        </div>
    </div>
</body>
</html>