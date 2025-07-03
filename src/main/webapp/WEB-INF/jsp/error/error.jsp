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
    <style>
      body {
        background-image: url(/images/background.jpg);
        background-repeat: no-repeat;
        background-position: center center;
        background-attachment: fixed;
        background-size: auto;
      }
    </style>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <p class="img-align">
      <img src="/images/icons/error_500_icon.png" width="130px" height="130px " />
    </p>

    <div class="container text-center my-5">
      <p class="display-1 text-margin">Error :(</p>
      <p class="display-6">
       Something went wrong with the server. Please try your request again later.
      </p>
        <a
          href="/"
          class="btn btn-secondary btn-lg">
          Back to mainpage
          </a>
      </div>
  </body>
</html>