<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Internal Server Error</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="img-align text-center my-4">
        <img src="/images/icons/error_500_icon.png"
             width="130"
             height="130"
             alt="Server Error" />
    </div>

    <div class="container text-center my-4">
        <h1 class="display-4 mb-4">Error :(</h1>
        <div class="error-details mb-4">
            <p class="lead">Something went wrong with the server:</p>
            <p class="lead"><strong>Status:</strong> ${status}</p>
            <c:if test="${not empty reason}">
                <p class="lead"><strong>Reason:</strong> ${reason}</p>
            </c:if>
        </div>
        <a href="/" class="btn btn-secondary btn-lg mt-3">
            Back to mainpage
        </a>
    </div>
</body>
</html>