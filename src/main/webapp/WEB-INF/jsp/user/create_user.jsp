<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                Sign Up
            </c:when>
            <c:otherwise>
                Add user
            </c:otherwise>
        </c:choose>
    </title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container mt-4 container-position label-text-size">
        <p class="display-4">
            <c:choose>
                <c:when test="${empty sessionScope.user}">Sign Up</c:when>
                <c:otherwise>Add user</c:otherwise>
            </c:choose>
        </p>

        <form action="<c:choose><c:when test="${empty sessionScope.user}">/register</c:when><c:otherwise>/users/add</c:otherwise></c:choose>" method="post">
            <div class="form-group row element-padding align-items-center">
                <label for="email" class="col-sm-2 col-form-label font-weight-bold font-italic">E-Mail</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="email" name="email"
                           pattern="^\S+@\S+\.\S+$" placeholder="Email..." required>
                    <small id="emailHelp" class="form-text text-muted help-text-size">Enter a valid email address</small>
                </div>
            </div>

            <div class="form-group row element-padding align-items-center">
                <label for="login" class="col-sm-2 col-form-label font-weight-bold font-italic">Login</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="login" name="login"
                           pattern="[A-Za-z0-9]{4,20}" placeholder="Login..." required>
                    <small id="loginHelp" class="form-text text-muted help-text-size">4-20 characters (letters and numbers only)</small>
                </div>
            </div>

            <div class="form-group row element-padding align-items-center">
                <label for="password" class="col-sm-2 col-form-label font-weight-bold font-italic">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="password" name="password"
                           pattern="[A-Za-z0-9]{4,15}" placeholder="Password..." required>
                    <small id="passwordHelp" class="form-text text-muted help-text-size">4-15 characters (letters and numbers only)</small>
                </div>
            </div>

            <div class="box button-padding">
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">Register</c:when>
                        <c:otherwise>Add</c:otherwise>
                    </c:choose>
                </button>
                <c:if test="${not empty sessionScope.user}">
                    <a href="/users" class="btn btn-secondary box-margin">Back to users</a>
                </c:if>
            </div>
        </form>
    </div>
</body>
</html>