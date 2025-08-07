<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
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
    <c:set var="isNotManager" value=
        "${empty sessionScope.user || (not empty sessionScope.user && sessionScope.user.role.name() ne 'MANAGER')}"/>
    <c:set var="pageUrl" value="/books" />

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

        <div class="d-flex justify-content-end mb-4 books-form-margin-right">
            <%@ include file="/WEB-INF/jsp/page_size_form.jsp" %>
        </div>

        <table class="table table-position wide-table">
            <thead class="table-light">
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Year</th>
                    <th>Price</th>
                    <th colspan="3">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="book" varStatus="counter">
                    <tr>
                        <td>
                            <a href="/books/<c:out value='${book.id}'/>">
                                <c:out value="${page.number * page.size + counter.index + 1}"/>
                            </a>
                        </td>
                        <td><c:out value="${book.name}"/></td>
                        <td><c:out value="${book.author}"/></td>
                        <td><c:out value="${book.publishedYear}"/></td>
                        <td><fmt:formatNumber value="${book.price}" type="currency" currencyCode="USD"/></td>

                        <c:if test="${isNotManager}">
                            <td>
                                <form action="/cart/add" method="post" class="d-inline">
                                    <input type="hidden" name="bookId" value="<c:out value='${book.id}'/>"/>
                                    <input type="hidden" name="page" value="${page.number}"/>
                                    <input type="hidden" name="size" value="${page.size}"/>
                                    <c:forEach items="${sortParams}" var="sortParam">
                                         <input type="hidden" name='sort' value='${sortParam}'/>
                                    </c:forEach>
                                    <button type="submit" class="btn btn-sm btn-outline-success">
                                        Add to Cart
                                    </button>
                                </form>
                            </td>
                        </c:if>

                        <c:if test="${isEmployee}">
                            <td>
                                <a href="/books/edit/<c:out value='${book.id}'/>" class="btn btn-sm btn-outline-primary">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <form action="/books/delete/<c:out value='${book.id}'/>" method="post" class="d-inline">
                                    <input type="hidden" name="page" value="${page.number}"/>
                                    <input type="hidden" name="size" value="${page.size}"/>
                                    <c:forEach items="${sortParams}" var="sortParam">
                                         <input type="hidden" name='sort' value='${sortParam}'/>
                                    </c:forEach>
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

        <c:if test="${page.totalPages > 1}">
            <%@ include file="/WEB-INF/jsp/pagination_panel.jsp" %>
        </c:if>

        <div class="text-center mt-4">
            <a href="/" class="btn btn-secondary btn-lg">
                Back to Home
            </a>
        </div>
    </div>
</body>
</html>