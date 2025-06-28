<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">
      <img
        src="/images/icons/brand_icon.png"
        width="30"
        height="30"
        class="d-inline-block align-top"
        alt="BS"
      />
      Bookstore
    </a>
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto">
        <c:if test="${not empty sessionScope.user}">
          <li class="nav-item px-2">
            <a class="nav-link fs-5" href="/users">Users</a>
          </li>
        </c:if>
        <li class="nav-item px-2">
          <a class="nav-link fs-5" href="/books">Books</a>
        </li>
        <c:if test="${not empty sessionScope.user}">
          <li class="nav-item px-2">
            <a class="nav-link fs-5" href="/orders">Orders</a>
          </li>
        </c:if>
      </ul>

      <div class="d-flex">
        <ul class="navbar-nav ms-auto">
          <c:choose>
            <c:when test="${empty sessionScope.user}">
              <li class="nav-item px-3">
                <a class="nav-link fs-5" href="/login">Sign in
                <img
                        src="/images/icons/navbar_unauthorized_user.png"
                        width="30"
                        height="30"
                        class="d-inline-block align-top"
                        alt="BS"
                />
                </a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="nav-item px-3">
                <span class="nav-link text-dark fs-5">${sessionScope.user.login}
                <img
                        src="/images/icons/navbar_user_icon.png"
                        width="30"
                        height="30"
                        class="d-inline-block align-top"
                        alt="User Icon"
                      />
                </span>
              </li>
              <li class="nav-item px-3">
                <a class="nav-link fs-5" href="/logout">Logout</a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </div>
</nav>