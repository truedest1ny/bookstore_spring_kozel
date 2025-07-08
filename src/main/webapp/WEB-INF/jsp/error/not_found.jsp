<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Not Found</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="img-align">
        <img src="/images/icons/error_404_icon.png"
             width="130"
             height="130"
             alt="Not Found" />
    </div>

    <div class="container text-center my-5">
        <p class="display-1">Error :(</p>
        <p class="display-6">
            Requested resource <span class="text-primary"><c:out value="${url}"/></span> was not found.
            Please adjust your request.
        </p>
        <a href="/" class="btn btn-secondary btn-lg mt-3">
            Back to mainpage
        </a>
    </div>
</body>
</html>