<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add User</title>
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
<p class="display-4">Add User</p>
<form action="/register" method="post">

  <div class="form-group row element-padding">
    <label for="email" class="col-sm-2 col-form-label"><i><b>E-Mail</i></b></label>
    <div class="col-sm-10">
       <input type="text" class="form-control" id="email" name="email" pattern="^\S+@\S+\.\S+$" placeholder="Email..." required>
       <small id="emailHelp" class="form-text text-muted help-text-size">Enter the valid E-Mail</small>
    </div>
  </div>

  <div class="form-group row element-padding">
      <label for="login" class="col-sm-2 col-form-label"><b><i>Login</i></b></label>
      <div class="col-sm-10">
         <input type="text" class="form-control" id="login" name="login" pattern="[A-Za-z0-9]{4,20}" placeholder="Login..." required>
         <small id="loginHelp" class="form-text text-muted help-text-size">Enter the login. EN letters, from 4 to 20 symbols</small>
      </div>
    </div>

    <div class="form-group row element-padding">
      <label for="password" class="col-sm-2 col-form-label"><b><i>Password</i></b></label>
      <div class="col-sm-10">
         <input type="text" class="form-control" id="password" name="password" pattern="[A-Za-z0-9]{4,15}" placeholder="Password..." required>
         <small id="loginHelp" class="form-text text-muted help-text-size">Enter the password. EN letters, from 4 to 15 symbols</small>
      </div>
    </div>

    <div class="box button-padding">
         <button type="submit" class="btn btn-primary">Add</button>
         <a href="/users" class="btn btn-secondary box-margin">Back to users page</a>
    </div>

</form>
</div>
</body>
</html>