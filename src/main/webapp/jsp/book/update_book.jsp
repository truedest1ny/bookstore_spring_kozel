<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit book #${book.id}</title>
    <link rel="shortcut icon" href="images/icons/brand_icon.png" />
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
    <link href="css/form_style.css" rel="stylesheet" />
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

<div class="container mt-4 container-position label-text-size">
<p class="display-4">Edit Book #${book.id}</p>
<form action="controller" method="post">

  <div class="form-group row">
    <input type="hidden" name="command" value="updateBook">
    <input type="hidden" name="id" value="${book.id}">
  </div>

  <div class="form-group row element-padding">
    <label for="name" class="col-sm-2 col-form-label"><b><i>Name</i></b></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name"
      value="${book.name}" placeholder="Now : ${book.name}" required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="isbn" class="col-sm-2 col-form-label"><i><b>ISBN</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="isbn" name="isbn" value="${book.isbn}" disabled>
       <small id="isbnHelp" class="form-text text-muted help-text-size">ISBN - a unique number - remains constant the entire time the book is in the catalog</small>
    </div>

  </div>

  <div class="form-group row element-padding">
    <label for="author" class="col-sm-2 col-form-label"><b><i>Author</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="author" name="author"
       value="${book.author}" placeholder="Now : ${book.author}" required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="published_year" class="col-sm-2 col-form-label"><b><i>Published Year</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="published_year" name="published_year"
       pattern="[0-9]{4}" title="Enter a 4-digit positive number"
       value="${book.publishedYear}"
       placeholder="Now : ${book.publishedYear}" required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="price" class="col-sm-2 col-form-label"><b><i>Price</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="price" name="price"
        value="${book.price}" placeholder=" Now : ${book.price}"
        pattern="^(0|[1-9][0-9]*)(.[0-9]{1,2})?$"
        title="Enter a positive number (up to 2 decimal places)" required>
    </div>
  </div>

  <fieldset class="form-group element-padding">
    <div class="row">
      <legend class="col-form-label col-sm-2 pt-0"><b><i>Cover</i></b></legend>

      <div class="col-sm-10">

        <c:forEach items="${covers}" var="cover">

        <div class="form-check">
          <input class="form-check-input" type="radio" name="cover" id="${cover}" value="${cover}" checked>
          <label class="form-check-label" for="${cover}">
            ${cover}
          </label>
        </div>

        </c:forEach>

        </div>
        <div class="box button-padding">
                <button type="submit" class="btn btn-primary">Update</button>
                <a
                href="controller?command=books"
                class="btn btn-secondary box-margin"
                >Back to books page</a>
        </div>
    </div>
  </fieldset>
</form>
</div>
</body>
</html>
