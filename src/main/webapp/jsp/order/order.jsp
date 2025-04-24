<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Order #${order.id}</title>
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
      <p class="display-4">Order #${order.id}</p>

      <table class="lead text-font-size">
        <thead>
        </thead>
        <tbody>
          <tr>
            <td>Date :</td>
            <td>${order.date}</td>
          </tr>

          <tr>
            <td>User login :</td>
            <td><a href="controller?command=user&id=${order.user.id}">${order.user.login}</a></td>
          </tr>

          <tr>
            <td>Status :</td>
            <td>${order.status}</td>
          </tr>

          <tr>
            <td>Total price :</td>
            <td>${order.totalPrice}</td>
          </tr>
        </tbody>
      </table>
      <div class="container text-center my-5">
        <p class="display-4 top-text-size"><i>Included in the order:</i></p>
      </div>

      <table class="table table-position">
      <thead class="table-light">
        <tr>
          <th>Book</th>
          <th>Value</th>
          <th>Price</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${items}" var="item" varStatus="counter">
                <tr>
                  <td>${item.book.name}</td>
                  <td>${item.quantity}</td>
                  <td>${item.price}</td>
                </tr>
              </c:forEach>
      </tbody>
      </table>
      <div class="box">
        <a
          href="controller?command=orders"
          class="btn btn-primary btn-lg button-margin"
          >Back to Orders page</a
        >

        <a
          href="controller"
          class="btn btn-secondary btn-lg button-margin box-margin"
          >Back to mainpage</a
        >
      </div>
    </div>
  </body>
</html>