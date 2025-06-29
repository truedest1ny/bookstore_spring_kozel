<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Incorrect input</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
    <link href="/css/tablestyle.css" rel="stylesheet" />
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
      <img src="/images/icons/error_400_icon.png" width="130px" height="130px " />
    </p>

    <div class="container text-center my-5">
      <p class="display-1 text-margin">Error :(</p>
      <p class="display-6">
       Request <b style="color:hsla(200,100%,40%,0.9)">${url}</b> is incorrect. Please try your request again.
      </p>
        <a
          href="/"
          class="btn btn-secondary btn-lg">
          Back to mainpage
          </a>
      </div>
  </body>
</html>