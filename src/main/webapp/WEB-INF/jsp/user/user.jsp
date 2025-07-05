<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <title>User #${user.id}</title>
        </c:otherwise>
    </c:choose>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
    <style>
        body {
            background-image: url(/images/background.jpg);
            background-repeat: no-repeat;
            background-position: center center;
            background-attachment: fixed;
            background-size: cover;
        }
        .profile-table {
            background-color: rgba(255, 255, 255, 0.95);
            width: 100%;
            max-width: 600px;
            margin: 2rem auto;
            border-collapse: collapse;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
            border-radius: 0.5rem;
            overflow: hidden;
        }
        .profile-table td {
            padding: 1rem;
            border-bottom: 1px solid rgba(0, 0, 0, 0.05);
        }
        .profile-table tr:last-child td {
            border-bottom: none;
        }
        .profile-label {
            font-weight: 600;
            color: #495057;
            width: 30%;
        }
        .profile-value {
            color: #212529;
        }
        .button-margin {
            margin: 0.5rem;
        }
        .profile-container {
            padding: 2rem;
            text-align: center;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="profile-container">
        <c:choose>
            <c:when test="${isOwnerProfile}">
                <h1 class="display-4 mb-4">My Profile</h1>
            </c:when>
            <c:otherwise>
                <h1 class="display-4 mb-4">User Profile [ID: ${user.id}]</h1>
            </c:otherwise>
        </c:choose>

        <table class="profile-table">
            <tbody>
                <tr>
                    <td class="profile-label">Name:</td>
                    <td class="profile-value">${user.firstName} ${user.lastName}</td>
                </tr>
                <tr>
                    <td class="profile-label">Email:</td>
                    <td class="profile-value">${user.email}</td>
                </tr>
                <tr>
                    <td class="profile-label">Login:</td>
                    <td class="profile-value">${user.login}</td>
                </tr>
                <c:if test="${not isOwnerProfile}">
                <tr>
                    <td class="profile-label">Role:</td>
                    <td class="profile-value">${user.role}</td>
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