<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Not Found!</title>
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
       Requested resource User with ID ${id} was not found. Please adjust your
        request.
      </p>
      <div class="box">
        <a
          href="controller?command=users"
          class="btn btn-primary btn-lg button-margin">
          Back to users page
          </a>

        <a
          href="/"
          class="btn btn-secondary btn-lg button-margin box-margin">
          Back to mainpage
          </a>
      </div>
    </div>
  </body>
</html>