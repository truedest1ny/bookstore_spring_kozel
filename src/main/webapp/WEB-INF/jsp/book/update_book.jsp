<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit book #<c:out value="${book.id}"/></title>
    <link rel="shortcut icon" href="/images/icons/brand_icon.png" />
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/navbar.jsp" %>

    <div class="container mt-4 container-position label-text-size">
        <p class="display-4">Edit Book #<c:out value="${book.id}"/></p>
        <form action="/books/edit/<c:out value='${book.id}'/>" method="post">

            <input type="hidden" name="id" value="<c:out value='${book.id}'/>">

            <div class="form-group row element-padding">
                <label for="name" class="col-sm-2 col-form-label"><b><i>Name</i></b></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" name="name"
                           value="<c:out value='${book.name}'/>" placeholder="Input title..." required>
                </div>
            </div>

            <div class="form-group row element-padding">
                <label for="isbn" class="col-sm-2 col-form-label"><i><b>ISBN</i></b></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="isbn" name="isbn"
                           value="<c:out value='${book.isbn}'/>" readonly>
                    <small class="form-text text-muted help-text-size">
                        ISBN - a unique number - remains constant the entire time the book is in the catalog
                    </small>
                </div>
            </div>

            <div class="form-group row element-padding">
                <label for="author" class="col-sm-2 col-form-label"><b><i>Author</i></b></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="author" name="author"
                           value="<c:out value='${book.author}'/>" placeholder="Input author..." required>
                </div>
            </div>

            <div class="form-group row element-padding">
                <label for="publishedYear" class="col-sm-2 col-form-label"><b><i>Published Year</i></b></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="publishedYear" name="publishedYear"
                           pattern="[0-9]{4}" title="Enter a 4-digit positive number"
                           value="<c:out value='${book.publishedYear}'/>"
                           placeholder="Input published year..." required>
                </div>
            </div>

            <div class="form-group row element-padding">
                <label for="price" class="col-sm-2 col-form-label"><b><i>Price</i></b></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="price" name="price"
                           value="<c:out value='${book.price}' />"
                           placeholder="Input price..."
                           pattern="^(0|[1-9][0-9]*)(.[0-9]{1,2})?$"
                           title="Enter a positive number (up to 2 decimal places)" required>
                </div>
            </div>

            <fieldset class="form-group element-padding">
                <div class="row">
                    <legend class="col-form-label col-sm-2 pt-0"><b><i>Cover</i></b></legend>
                    <div class="col-sm-10">
                        <div class="d-flex flex-column">
                            <c:forEach items="${covers}" var="cover">
                                <div class="form-check align-items-start">
                                    <input class="form-check-input" type="radio"
                                           name="cover" id="<c:out value='${cover}'/>" value="<c:out value='${cover}'/>"
                                           ${book.cover == cover ? 'checked' : ''}>
                                    <label class="form-check-label" for="<c:out value='${cover}'/>">
                                        <c:out value="${cover}"/>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </fieldset>

            <div class="box button-padding">
                <button type="submit" class="btn btn-primary">Update</button>
                <a href="/books" class="btn btn-secondary box-margin">Back to books page</a>
            </div>
        </form>
    </div>
</body>
</html>