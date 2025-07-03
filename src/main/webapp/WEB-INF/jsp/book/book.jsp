<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Book #${book.id}</title>
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

    <div class="container text-center my-5">
      <p class="display-4">Book #${book.id}</p>

      <table class="lead text-font-size">
        <thead>
        </thead>
        <tbody>
          <tr>
            <td>Name :</td>
            <td>${book.name}</td>
          </tr>

          <tr>
            <td>ISBN :</td>
            <td>${book.isbn}</td>
          </tr>

          <tr>
            <td>Cover :</td>
            <td>${book.cover}</td>
          </tr>

          <tr>
            <td>Author :</td>
            <td>${book.author}</td>
          </tr>

          <tr>
            <td>Published Year :</td>
            <td>${book.publishedYear}</td>
          </tr>

          <tr>
            <td>Price :</td>
            <td>$${book.price}</td>
          </tr>
        </tbody>
      </table>

      <div class="box">
        <a
          href="/books"
          class="btn btn-primary btn-lg button-margin"
          >Back to books page</a
        >

        <a
          href="/"
          class="btn btn-secondary btn-lg button-margin box-margin"
          >Back to mainpage</a
        >
      </div>
    </div>
  </body>
</html>