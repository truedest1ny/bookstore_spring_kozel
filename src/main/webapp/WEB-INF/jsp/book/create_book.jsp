<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add book</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
    <link href="/css/form_style.css" rel="stylesheet" />
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

<%@ include file="\WEB-INF\jsp\navbar.jsp" %>

<div class="container mt-4 container-position label-text-size">
<p class="display-4">Create Book</p>
<form action="/books/add" method="post">


  <div class="form-group row element-padding">
    <label for="name" class="col-sm-2 col-form-label"><b><i>Name</i></b></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name" placeholder="Name..." required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="isbn" class="col-sm-2 col-form-label"><i><b>ISBN</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="isbn" name="isbn" pattern="[0-9]{13}" placeholder="ISBN..." required>
       <small id="isbnHelp" class="form-text text-muted help-text-size">Enter the 13-digit code without spaces</small>
    </div>

  </div>

  <div class="form-group row element-padding">
    <label for="author" class="col-sm-2 col-form-label"><b><i>Author</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="author" name="author" placeholder="Author..." required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="publishedYear" class="col-sm-2 col-form-label"><b><i>Published Year</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="publishedYear" name="publishedYear" pattern="[0-9]{4}" placeholder="Year..." required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="price" class="col-sm-2 col-form-label"><b><i>Price</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="price" name="price" pattern="\d+(\.\d{2})?" placeholder="Price ($)..." required>
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
                <button type="submit" class="btn btn-primary">Add</button>
                <a
                href="/books"
                class="btn btn-secondary box-margin"
                >Back to books page</a>
        </div>
    </div>
 </fieldset>

</form>
</div>
</body>
</html>