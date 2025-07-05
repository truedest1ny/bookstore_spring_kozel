<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Change Password</title>
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
        .error-message {
            color: #dc3545;
            font-size: 0.9rem;
            margin-top: 0.25rem;
        }
        .form-group {
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
        }
        .password-rules {
            font-size: 0.6rem;
            color: #6c757d;
            margin-top: 0.25rem;
        }
        .form-label {
            min-width: 180px;
            margin-bottom: 0;
            padding-right: 15px;
        }
        .form-control-container {
            flex: 1;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container mt-4 container-position label-text-size">
        <p class="display-4">Change Password</p>
        <p class="display-4 login-error-style lead">
             <c:out value="${message}" />
        </p>

        <form action="/profile/edit/password" method="post">
            <div class="form-group">
                <label for="currentPassword" class="form-label"><b><i>Current Password:</i></b></label>
                <div class="form-control-container">
                    <input type="password" class="form-control" id="currentPassword"
                           name="currentPassword" required placeholder="Enter your current password">
                </div>
            </div>

            <div class="form-group">
                <label for="newPassword" class="form-label"><b><i>New Password:</i></b></label>
                <div class="form-control-container">
                    <input type="password" class="form-control" id="newPassword"
                           name="newPassword" required
                           pattern="[A-Za-z0-9]{4,15}"
                           placeholder="Enter new password"
                           title="4-15 alphanumeric characters">
                    <div class="password-rules">
                        Password must contain 4-15 alphanumeric characters
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="confirmPassword" class="form-label"><b><i>Confirm Password:</i></b></label>
                <div class="form-control-container">
                    <input type="password" class="form-control" id="confirmPassword"
                           name="confirmPassword" required
                           placeholder="Confirm your new password">
                </div>
            </div>

            <div class="box button-padding">
                <button type="submit" class="btn btn-primary">Change Password</button>
                <a href="/profile" class="btn btn-secondary box-margin">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>