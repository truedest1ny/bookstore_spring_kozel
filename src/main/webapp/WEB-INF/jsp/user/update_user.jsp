<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <c:choose>
        <c:when test="${isOwnerProfile}">
            <title>Edit profile</title>
        </c:when>
        <c:otherwise>
            <title>Edit user #<c:out value="${user.id}"/></title>
        </c:otherwise>
    </c:choose>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container mt-4 container-position label-text-size">
        <c:choose>
            <c:when test="${isOwnerProfile}">
                <p class="display-4">Edit profile</p>
                <form action="/profile/edit" method="post">
            </c:when>
            <c:otherwise>
                <p class="display-4">Edit User [ID: <c:out value="${user.id}"/>]</p>
                <form action="/users/edit/<c:out value='${user.id}'/>" method="post">
            </c:otherwise>
        </c:choose>

        <input type="hidden" name="id" value="${user.id}">

        <div class="form-group row element-padding">
            <label for="firstName" class="col-sm-2 col-form-label"><b><i>First Name</i></b></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="firstName" name="firstName"
                    value="<c:out value='${user.firstName}'/>" placeholder="Input first name...">
            </div>
        </div>

        <div class="form-group row element-padding">
            <label for="lastName" class="col-sm-2 col-form-label"><b><i>Last Name</i></b></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="lastName" name="lastName"
                    value="<c:out value='${user.lastName}'/>" placeholder="Input last name...">
            </div>
        </div>

        <div class="form-group row element-padding">
            <label for="email" class="col-sm-2 col-form-label"><b><i>E-Mail</i></b></label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" name="email"
                    value="<c:out value='${user.email}'/>" placeholder="Input email..." required>
                <small class="form-text text-muted help-text-size">Enter the valid E-Mail</small>
            </div>
        </div>

        <div class="form-group row element-padding">
            <label for="login" class="col-sm-2 col-form-label"><b><i>Login</i></b></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="login" name="login"
                    value="<c:out value='${user.login}'/>" readonly>
                <small class="form-text text-muted help-text-size">
                    Login is set once during registration and will remain unchanged.
                </small>
            </div>
        </div>

        <c:if test="${not isOwnerProfile}">
            <fieldset class="form-group element-padding">
                <div class="row">
                    <legend class="col-form-label col-sm-2 pt-0"><b><i>Role</i></b></legend>
                    <div class="col-sm-10">
                        <div class="d-flex flex-column">
                            <c:forEach items="${roles}" var="role">
                                <div class="form-check align-items-start">
                                    <input class="form-check-input" type="radio" name="role"
                                        id="<c:out value='${role}'/>" value="<c:out value='${role}'/>"
                                        ${user.role eq role ? 'checked' : ''}>
                                    <label class="form-check-label" for="<c:out value='${role}'/>">
                                        <c:out value="${role}"/>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </fieldset>
        </c:if>

        <c:if test="${isOwnerProfile}">
            <div class="form-group row element-padding">
                <label for="password" class="col-sm-2 col-form-label"><b><i>Password</i></b></label>
                <div class="col-sm-10 text-left">
                    <a href="/profile/edit/password" class="btn btn-primary btn-password">Change</a>
                </div>
            </div>
        </c:if>

        <div class="box button-padding">
            <button type="submit" class="btn btn-primary">Update</button>
            <c:choose>
                <c:when test="${isOwnerProfile}">
                    <a href="/profile" class="btn btn-secondary box-margin">Back to profile</a>
                </c:when>
                <c:otherwise>
                    <a href="/users" class="btn btn-secondary box-margin">Back to users page</a>
                </c:otherwise>
            </c:choose>
        </div>
        </form>
    </div>
</body>
</html>