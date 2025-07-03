<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>User #${user.id}</title>
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
      <p class="display-4">User #${user.id}</p>

      <table class="lead text-font-size">
        <thead>
        </thead>
        <tbody>
          <tr>
            <td>Name :</td>
            <td>${user.firstName} ${user.lastName}</td>
          </tr>

          <tr>
            <td>E-mail :</td>
            <td>${user.email}</td>
          </tr>

          <tr>
            <td>Login :</td>
            <td>${user.login}</td>
          </tr>

          <tr>
            <td>Role :</td>
            <td>${user.role}</td>
          </tr>
        </tbody>
      </table>

      <div class="box">
        <a
          href="/users"
          class="btn btn-primary btn-lg button-margin"
          >Back to users page</a
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
