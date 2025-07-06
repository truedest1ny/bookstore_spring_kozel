<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Book #${book.id}</title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="profile-container">
        <h1 class="display-4 mb-4">Book Details [ID: ${book.id}]</h1>

        <table class="profile-table">
            <tbody>
                <tr>
                    <td class="profile-label">Name:</td>
                    <td class="profile-value">${book.name}</td>
                </tr>
                <tr>
                    <td class="profile-label">ISBN:</td>
                    <td class="profile-value">${book.isbn}</td>
                </tr>
                <tr>
                    <td class="profile-label">Cover:</td>
                    <td class="profile-value">${book.cover}</td>
                </tr>
                <tr>
                    <td class="profile-label">Author:</td>
                    <td class="profile-value">${book.author}</td>
                </tr>
                <tr>
                    <td class="profile-label">Published Year:</td>
                    <td class="profile-value">${book.publishedYear}</td>
                </tr>
                <tr>
                    <td class="profile-label">Price:</td>
                    <td class="profile-value">$${book.price}</td>
                </tr>
            </tbody>
        </table>

        <div class="mt-4">
            <a href="/books" class="btn btn-primary button-margin">Back to Books</a>
            <a href="/" class="btn btn-secondary button-margin">Back to Home</a>
        </div>
    </div>
</body>
</html>