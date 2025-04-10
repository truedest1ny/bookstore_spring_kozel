<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Invalid command</title>
    <link rel="shortcut icon" href="images/icons/brand_icon.png" />
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
    <link href="css/tablestyle.css" rel="stylesheet" />
    <style>
      body {
        background-image: url(images/background.jpg);
        background-repeat: no-repeat;
        background-position: center center;
        background-attachment: fixed;
        background-size: auto;
      }
    </style>
  </head>
  <body>
    <%@ include file="\jsp\navbar.jsp" %>

    <p class="img-align">
      <img src="images/icons/error_404_icon.png" width="130px" height="130px " />
    </p>

    <div class="container text-center my-5">
      <p class="display-1 text-margin">Error :(</p>
      <p class="display-6">
       Requested resource with URL <b style="color:hsla(200,100%,40%,0.9)">[/controller?command=${command}]</b> was not found. Please adjust your
        request.
      </p>

        <a
          href="/"
          class="btn btn-secondary btn-lg button-margin">
          Back to mainpage
          </a>
    </div>
  </body>
</html>