<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Users</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <c:set var="isNotManager" value="${sessionScope.user.role.name() ne 'MANAGER'}"/>

    <div class="container text-center my-5">
        <p class="display-4">Users list</p>
        <p class="lead">The catalog includes a list of users.</p>
        <c:if test="${isNotManager}">
            <a href="/users/add" class="btn btn-primary btn-lg">Add User</a>
        </c:if>
    </div>

    <table class="table table-position">
        <thead class="table-light">
            <tr>
                <th>#</th>
                <th>E-mail</th>
                <th>Login</th>
                <th>Role</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user" varStatus="counter">
                <tr>
                    <td><a href="/users/<c:out value='${user.id}'/>"><c:out value="${user.id}"/></a></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.role}"/></td>
                    <td>
                        <c:if test="${isNotManager}">
                            <a href="/users/edit/<c:out value='${user.id}'/>" class="btn btn-sm btn-outline-primary">Edit</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${sessionScope.user.role.name() eq 'SUPER_ADMIN'}">
                            <form action="/users/delete/<c:out value='${user.id}'/>" method="post" class="d-inline">
                                <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="container text-center mt-3 mb-5">
        <a href="/" class="btn btn-secondary btn-lg">Back to mainpage</a>
    </div>
</body>
</html>