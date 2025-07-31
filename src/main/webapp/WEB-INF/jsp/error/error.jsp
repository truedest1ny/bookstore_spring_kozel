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

    <div class="error-container text-center">
        <div class="img-align my-4">
            <img src="/images/icons/error_500_icon.png"
                 width="130"
                 height="130"
                 alt="Server Error Icon" />
        </div>

        <div class="container">
            <h1 class="display-1 text-margin mb-4">Error :(</h1>
            <p class="display-6 mb-5">
                Something went wrong with the server. Please try your request again later.
            </p>
            <a href="/" class="btn btn-secondary btn-lg">
                Back to mainpage
            </a>
        </div>
    </div>
</body>
</html>