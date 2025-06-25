<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Bookstore</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
    <link href="/css/tablestyle.css" rel="stylesheet" />
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

     <%@ include file="\WEB-INF\jsp\navbar.jsp" %>

    <p class="img-align">
      <img src="/images/icons/brand_icon.png" width="200px" height="200px " />
    </p>
    <div class="container text-center my-5">
      <p class="display-4 top-text-size">
        You have reached the page of the book catalog "<i style="color:hsla(200,100%,40%,0.9)"">Bookstore</i>"!
        Congratulations :)
      </p>

      <p class="lead">The catalog includes a list of available books and catalog users. Added list of orders :)</p>

      <div class="box">

      <div class="media position-relative">
        <img src="/images/icons/book_icon.png" class="mr-3" alt="Book icon" width="100px" height="100px"/>
        <div class="media-body">
          <h5 class="mt-0">Books</h5>
          <p>
            Current books catalog.
          </p>
          <a href="/books" class="stretched-link text-size">Go!</a>
        </div>
      </div>

      <div class="media position-relative box-margin">
        <img src="/images/icons/user_icon.png" class="mr-3" alt="Book icon" width="100px" height="100px"/>
        <div class="media-body">
          <h5 class="mt-0">Users</h5>
          <p>
            Actual users catalog.
          </p>
          <a href="/users" class="stretched-link text-size">Go!</a>
        </div>
      </div>

      <div class="media position-relative box-margin">
         <img src="/images/icons/order_icon.png" class="mr-3" alt="Order icon" width="100px" height="100px"/>
         <div class="media-body">
             <h5 class="mt-0">Orders</h5>
               <p>
                 Actual orders catalog.
               </p>
                <a href="/orders" class="stretched-link text-size">Go!</a>
         </div>
      </div>
    </div>
    </div>
  </body>
</html>
