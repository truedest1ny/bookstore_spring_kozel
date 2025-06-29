<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Users</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
    <link href="/css/tablestyle.css" rel="stylesheet" />

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

    <div class="container text-center my-5">
      <p class="display-4">Users list</p>
      <p class="lead">The catalog includes a list of users.</p>
      <a href="/register" class="btn btn-primary btn-lg">Add User</a>
    </div>

    <table class="table table-position">
      <thead class="table-light">
        <tr>
          <th>#</th>
          <th>E-mail</th>
          <th>Login</th>
          <th>Role</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${users}" var="user" varStatus="counter">
          <tr>
            <td>
              <a href="/users/${user.id}">${user.id}</a>
            </td>
            <td>${user.email}</td>
            <td>${user.login}</td>
            <td>${user.role}</td>
            <td>
              <a class="btn btn-primary btn-sm" href="/users/edit/${user.id}">Edit</button>
            </td>
            <td>
              <form action="/users/delete/${user.id}" method="post" style="display: inline;">
              <button type="submit" class="btn btn-secondary btn-sm">Delete</button>
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