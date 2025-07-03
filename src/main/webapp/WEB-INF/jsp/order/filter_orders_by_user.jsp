<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Find Order</title>
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

<div class="container mt-4 container-position label-text-size">
<p class="display-4">Find order by user</p>

<form action="/orders/find_by_user" method="post">

  <div class="form-group row">
    <input type="hidden" name="command" value="ordersByUser">
  </div>

  <div class="form-group">
      <label for="userFilter">Choose User:</label>
      <select class="form-control" id="userFilter" name="userFilter">

      <c:forEach items="${users}" var="user" varStatus="counter">
        <option value="${user.login}">
            ${counter.index + 1}. Login - ${user.login}. Email - ${user.email}.
        </option>
      </c:forEach>
      </select>
    </div>

        <div class="box button-padding">
                <button type="submit" class="btn btn-primary">Find</button>
                <a
                href="/orders"
                class="btn btn-secondary box-margin"
                >Back to orders page</a>
        </div>
    </div>
  </fieldset>
</form>
</div>
</body>
</html>