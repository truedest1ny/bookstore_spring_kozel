<c:set var="cartTotalPrice" value="${0}" />

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
         <c:if test="${sessionScope.user.role.name() ne 'CUSTOMER'}">
          <li class="nav-item px-2">
            <a class="nav-link fs-5" href="/users">Users</a>
          </li>
         </c:if>
        </c:if>
        <li class="nav-item px-2">
          <a class="nav-link fs-5" href="/books">Books</a>
        </li>
        <c:if test="${not empty sessionScope.user}">
          <li class="nav-item px-2">
           <c:choose>
                <c:when test="${sessionScope.user.role.name() eq 'CUSTOMER'}">
                    <a class="nav-link fs-5" href="/ordered">Orders</a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link fs-5" href="/orders">Orders</a>
                </c:otherwise>
           </c:choose>
          </li>

          <c:if test="${sessionScope.user.role.name() eq 'ADMIN' || sessionScope.user.role.name() eq 'SUPER_ADMIN'}">
                <a class="nav-link fs-5" href="/ordered">Profile orders (debug)</a>
          </c:if>
        </c:if>
      </ul>

      <div class="d-flex">
        <ul class="navbar-nav ms-auto">
            <c:if test=
            "${empty sessionScope.user || (not empty sessionScope.user && sessionScope.user.role.name() ne 'MANAGER')}">
                <li class="nav-item px-3">
                    <a class="nav-link fs-5" href="/cart">
                        <c:if test="${not empty sessionScope.sessionCart}">
                              <c:set var="cartTotalPrice" value="${sessionScope.sessionCart.totalPrice}" />
                        </c:if>
                              <fmt:formatNumber value= "${cartTotalPrice}" type="currency" currencyCode="USD"/>

                    <img
                        src="/images/icons/navbar_cart_icon.png"
                        width="30"
                        height="30"
                        class="d-inline-block align-top"
                        alt="Cart"
                    />
                    </a>
               </li>
            </c:if>

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
              <li class="nav-item px-3 d-flex align-items-center">
                    <a class="nav-link text-dark fs-5 p-0" href="/profile">
                      <c:out value="${sessionScope.user.login}"/>
                      <img
                          src="/images/icons/navbar_user_icon.png"
                          width="30"
                          height="30"
                          class="d-inline-block align-top"
                          alt="User Icon"
                        />
                    </a>
              </li>
              <li class="nav-item px-3 d-flex align-items-center">
                         <form action="/logout" method="post" class="d-inline m-0">
                             <button type="submit"
                                class="btn nav-link fs-5 p-0 border-0 bg-transparent text-dark lh-1">
                                 Logout
                             </button>
                         </form>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </div>
</nav>