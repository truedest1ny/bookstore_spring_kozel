<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sign in</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>

<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container mt-4 container-position label-text-size">
        <p class="display-4">Sign in</p>

        <form action="/login" method="post">
            <c:if test="${not empty message}">
                <p class="login-error-style lead mb-4">
                    <c:out value="${message}" />
                </p>
            </c:if>

            <div class="form-group row element-padding">
                <label for="login" class="col-sm-2 col-form-label font-italic font-weight-bold">Login</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="login" name="login"
                           pattern="[A-Za-z0-9]{4,20}" placeholder="Login..." required>
                </div>
            </div>

            <div class="form-group row element-padding">
                <label for="password" class="col-sm-2 col-form-label font-italic font-weight-bold">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="password" name="password"
                           pattern="[A-Za-z0-9]{4,15}" placeholder="Password..." required>
                </div>
            </div>

            <div class="box button-padding">
                <button type="submit" class="btn btn-primary">Login</button>
                <a href="/" class="btn btn-secondary box-margin">Back to main page</a>
            </div>

            <p class="sign-up-label lead mt-3">
                Do not have an account? <a href="/register">Sign up.</a>
            </p>
        </form>
    </div>
</body>
</html>