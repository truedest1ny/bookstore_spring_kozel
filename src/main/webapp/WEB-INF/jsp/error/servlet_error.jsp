<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
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

    <div class="img-align">
        <img src="/images/icons/error_500_icon.png"
             width="130"
             height="130"
             alt="Server Error" />
    </div>

    <div class="container text-center my-5">
        <p class="display-1">Error :(</p>
        <p class="display-6">
            Something went wrong with the server. Please try again later.
        </p>
        <c:if test="${not empty status}">
            <p class="lead"><strong>Status:</strong> <c:out value="${status}"/></p>
        </c:if>
        <c:if test="${not empty reason}">
            <p class="lead"><strong>Reason:</strong> <c:out value="${reason}"/></p>
        </c:if>

        <a href="/" class="btn btn-secondary btn-lg mt-3">
            Back to mainpage
        </a>
    </div>
</body>
</html>