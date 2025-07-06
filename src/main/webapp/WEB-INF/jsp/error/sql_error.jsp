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
             width="170"
             height="170"
             alt="Server Error Icon" />
    </div>

    <div class="container text-center my-4">
        <h1 class="display-4 mb-4">Error :(</h1>
        <p class="lead mb-4">
            There seems to be a problem connecting to the database.<br>
            Please try your request again later.
        </p>
        <a href="/" class="btn btn-secondary btn-lg">
            Back to mainpage
        </a>
    </div>
</body>
</html>