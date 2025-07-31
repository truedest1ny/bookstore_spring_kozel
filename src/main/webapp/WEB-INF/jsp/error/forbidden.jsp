<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
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

    <div class="img-align">
        <img src="/images/icons/error_403_icon.png"
             width="130"
             height="130"
             alt="Access Forbidden Icon" />
    </div>

    <div class="container text-center my-5"> <%-- Установлен my-5 --%>
        <p class="display-1">Access Denied :(</p>
        <p class="display-6"> <%-- Заменен text-muted на display-6 --%>
            You don't have permission to access this page. Please contact your administrator.
        </p>
        <a href="/" class="btn btn-secondary btn-lg mt-3">
            Back to mainpage
        </a>
    </div>
</body>
</html>