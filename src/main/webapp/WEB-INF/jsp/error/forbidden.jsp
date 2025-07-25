<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Access Forbidden</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="error-container text-center">
        <div class="img-align my-4">
            <img src="/images/icons/error_403_icon.png"
                 width="170"
                 height="170"
                 alt="Access Forbidden Icon"
                 class="img-fluid" />
        </div>

        <div class="container">
            <h1 class="display-1 text-margin mb-3">Access Denied</h1>
            <p class="display-6 text-muted mb-4">
                You don't have permission to access this page. Please contact your administrator.
            </p>
            <div class="d-grid gap-2 col-md-6 mx-auto">
                <a href="/" class="btn btn-secondary btn-lg">
                    Return to Homepage
                </a>
            </div>
        </div>
    </div>
</body>
</html>