<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Order #<c:out value="${order.id}"/></title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <div class="container text-center my-5">
        <h1 class="display-4">Order #<c:out value="${order.id}"/></h1>

        <h2 class="display-5 mb-4"><i>Order Details:</i></h2>
        <div class="order-info mb-5">
            <table class="table table-position table-hover mx-auto" style="max-width: 500px;">
                <thead class="table-light">
                    <tr>
                        <th style="width: 30%;">Detail</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="font-weight-bold">Date:</td>
                        <td><c:out value='${order.formattedDate}'/></td>
                    </tr>
                    <c:if test="${isEmployee}">
                        <tr>
                            <td class="font-weight-bold">User login:</td>
                            <td><a href="/users/<c:out value='${order.user.id}'/>">
                                    <c:out value="${order.user.login}"/>
                                </a>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="font-weight-bold">Status:</td>
                        <td><c:out value="${order.status}"/></td>
                    </tr>
                    <tr>
                        <td class="font-weight-bold">Total price:</td>
                        <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencyCode="USD"/></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <h2 class="display-5 mb-4"><i>Items in order:</i></h2>

         <div class="d-flex justify-content-end mb-4 order-item-form-margin-right">
               <%@ include file="/WEB-INF/jsp/page_size_form.jsp" %>
         </div>

        <table class="table table-position table-hover">
            <thead class="table-light">
                <tr>
                    <th>#</th>
                    <th style="width: 50%;">Book Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.content}" var="item" varStatus="counter">
                    <tr>
                        <td>
                            <a href="/books/<c:out value='${item.book.id}'/>">
                                <c:out value="${page.number * page.size + counter.index + 1}"/>
                            </a>
                        </td>
                        <td><c:out value="${item.book.name}"/></td>
                        <td><c:out value="${item.quantity}"/></td>
                        <td><fmt:formatNumber value="${item.price}" type="currency" currencyCode="USD"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

         <c:if test="${page.totalPages > 1}">
            <%@ include file="/WEB-INF/jsp/pagination_panel.jsp" %>
         </c:if>

        <div class="action-buttons mt-5">
            <c:choose>
                <c:when test="${isEmployee}">
                    <a href="/orders" class="btn btn-primary btn-lg button-margin">Back to Orders</a>
                </c:when>
                <c:otherwise>
                    <a href="/ordered" class="btn btn-primary btn-lg button-margin">Back to Orders</a>
                </c:otherwise>
            </c:choose>

                <c:if test=
                      "${order.status.name() eq 'CANCELLED'
                                && sessionScope.user.role.name() ne 'MANAGER'
                                && sessionScope.user.role.name() ne 'CUSTOMER'}">
                     <form action="/orders/archive/<c:out value="${order.id}"/>"
                          method="post" style="display:inline;">
                            <button type="submit" class="btn btn-danger btn-lg button-margin">
                                    Archive order
                            </button>
                     </form>
                 </c:if>

                <c:if test="${order.status.name() eq 'PENDING'
                                && sessionScope.user.role.name() ne 'CUSTOMER'}">
                     <form action="/orders/approve/<c:out value="${order.id}"/>"
                          method="post" style="display:inline;">
                             <button type="submit" class="btn btn-success btn-lg button-margin">
                                    Approve order
                             </button>
                     </form>
                </c:if>

                <c:if test="${order.user.login eq sessionScope.user.login
                                && order.status.name() eq 'PENDING'}">
                     <form action="/ordered/cancel/<c:out value="${order.id}"/>"
                           method="post" style="display:inline;">
                             <button type="submit" class="btn btn-danger btn-lg button-margin">
                                    Cancel order
                             </button>
                     </form>
                </c:if>

            <a href="/" class="btn btn-secondary btn-lg button-margin">Back to Home</a>
        </div>
    </div>
</body>
</html>