<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <c:choose>
        <c:when test="${isOwnerProfile}">
            <title>Profile</title>
        </c:when>
        <c:otherwise>
            <title>User #<c:out value="${user.id}"/></title>
        </c:otherwise>
    </c:choose>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>
    <%@ include file="/WEB-INF/jsp/notification.jsp" %>

    <div class="profile-container">
        <c:choose>
            <c:when test="${isOwnerProfile}">
                <h1 class="display-4 mb-4">My Profile</h1>
            </c:when>
            <c:otherwise>
                <h1 class="display-4 mb-4">User Profile [ID: <c:out value="${user.id}"/>]</h1>
            </c:otherwise>
        </c:choose>

        <table class="profile-table">
            <tbody>
                <tr>
                    <td class="profile-label">Name:</td>
                    <td class="profile-value"><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">Email:</td>
                    <td class="profile-value"><c:out value="${user.email}"/></td>
                </tr>
                <tr>
                    <td class="profile-label">Login:</td>
                    <td class="profile-value"><c:out value="${user.login}"/></td>
                </tr>
                <c:if test="${not isOwnerProfile}">
                <tr>
                    <td class="profile-label">Role:</td>
                    <td class="profile-value"><c:out value="${user.role}"/></td>
                </tr>
                </c:if>
            </tbody>
        </table>

        <div class="mt-4">
            <c:if test="${isOwnerProfile}">
                <a href="/profile/edit" class="btn btn-primary button-margin">Edit Profile</a>
            </c:if>
            <c:if test="${not isOwnerProfile}">
                <a href="/users" class="btn btn-primary button-margin">Back to Users</a>
            </c:if>
            <a href="/" class="btn btn-secondary button-margin">Back to Home</a>
        </div>
    </div>
</body>
</html>