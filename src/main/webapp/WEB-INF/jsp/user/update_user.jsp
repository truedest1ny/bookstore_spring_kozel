<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit user #${user.id}</title>
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

<%@ include file="/WEB-INF/jsp/navbar.jsp" %>

<div class="container mt-4 container-position label-text-size">
<p class="display-4">Edit User #${user.id}</p>
<form action="/users/edit/${user.id}" method="post">

  <div class="form-group row">
    <input type="hidden" name="command" value="updateUser">
    <input type="hidden" name="id" value="${user.id}">
  </div>

  <div class="form-group row element-padding">
    <label for="first_name" class="col-sm-2 col-form-label"><b><i>First Name</i></b></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="firstName" name="firstName"
      value="${user.firstName}" placeholder="Now : ${user.firstName}" required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="last_name" class="col-sm-2 col-form-label"><b><i>Last Name</i></b></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="lastName" name="lastName"
      value="${user.lastName}" placeholder="Now : ${user.lastName}" required>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="email" class="col-sm-2 col-form-label"><i><b>E-Mail</i></b></label>
    <div class="col-sm-10">
       <input type="email" class="form-control" id="email" name="email"
       value="${user.email}" placeholder="Now : ${user.email}" required>
       <small id="emailHelp" class="form-text text-muted help-text-size">Enter the valid E-Mail</small>
    </div>
  </div>

  <div class="form-group row element-padding">
    <label for="login" class="col-sm-2 col-form-label"><b><i>Login</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="login" name="login" value="${user.login}" readonly>
       <small id="loginHelp" class="form-text text-muted help-text-size">
          Login is set once during registration and will remain unchanged.
       </small>
    </div>
  </div>

  <fieldset class="form-group element-padding">
    <div class="row">
      <legend class="col-form-label col-sm-2 pt-0"><b><i>Role</i></b></legend>

      <div class="col-sm-10">

        <c:forEach items="${roles}" var="role">

        <div class="form-check">
          <input class="form-check-input" type="radio" name="role" id="${role}" value="${role}" checked>
          <label class="form-check-label" for="${role}">
            ${role}
          </label>
        </div>

        </c:forEach>

        </div>
        <div class="box button-padding">
                <button type="submit" class="btn btn-primary">Update</button>
                <a
                href="/users"
                class="btn btn-secondary box-margin"
                >Back to users page</a>
        </div>
    </div>
  </fieldset>
</form>
</div>
</body>
</html>