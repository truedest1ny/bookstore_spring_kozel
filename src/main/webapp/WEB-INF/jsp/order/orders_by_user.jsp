<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Filter Orders</title>
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
      <p class="display-4">Orders list</p>
      <p class="lead">The catalog includes a list of orders made by user <b><i>${login}</i></b>.</p>
    </div>

    <table class="table table-position">
      <thead class="table-light">
        <tr>
          <th>#</th>
          <th>Date</th>
          <th>User Login</th>
          <th>Total Price</th>
          <th>Status</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${orders}" var="order" varStatus="counter">
          <tr>
            <td>${order.id}</td>
            <td>${order.date}</td>
            <td>${order.userLogin}</td>
            <td>${order.totalPrice}</td>
            <td>${order.status}</td>
            <td>
              <a class="btn btn-primary btn-sm" href="/orders/${order.id}">Details</button>
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