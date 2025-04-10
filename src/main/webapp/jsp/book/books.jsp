<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Books</title>
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

    <div class="container text-center my-5">
      <p class="display-4">Books list</p>
      <p class="lead">The catalog includes a list of books.</p>
      <a href="controller?command=creatingBook" class="btn btn-primary btn-lg">Add Book</a>
    </div>


    <table class="table table-position">
      <thead class="table-light">
        <tr>
          <th>#</th>
          <th>Name</th>
          <th>Author</th>
          <th>Published Year</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${books}" var="book">
          <tr>
            <td>
              <a href="controller?command=book&id=${book.id}">${book.id}</a>
            </td>
            <td>${book.name}</td>
            <td>${book.author}</td>
            <td>${book.publishedYear}</td>
            <td>
               <a class="btn btn-primary btn-sm" href="controller?command=editBook&id=${book.id}">Edit</button>
            </td>
            <td>
               <a class="btn btn-secondary btn-sm" href="controller?command=deleteBook&id=${book.id}">Delete</button>
            </td>

          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="container text-center my-5">
      <a href="/" class="btn btn-secondary btn-lg button-margin"
        >Back to mainpage</a
      >
    </div>
  </body>
</html>