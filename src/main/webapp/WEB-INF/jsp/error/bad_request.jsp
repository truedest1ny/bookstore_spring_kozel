<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bad Request</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="img-align">
        <img src="/images/icons/error_400_icon.png"
             width="130"
             height="130"
             alt="Bad Request Icon" />
    </div>

    <div class="container text-center my-5">
        <p class="display-1">Bad Request :(</p>
        <p class="display-6">
            The request <span class="text-primary"><c:out value="${url}"/></span> contains invalid data.
            Please try again.
        </p>
        <a href="/" class="btn btn-secondary btn-lg mt-3">
            Return to Homepage
        </a>
    </div>
</body>
</html>