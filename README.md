# Bookstore application

### Launch instructions

You must have a **database**, that meets  the following *criteria*:

- The connection is made using the *protocol* `jdbc:postgresql` and *port* ***5432***;
- *Database name*: `bookstore_bh`;
- *Login* : `postgres`, *password*: see in a file `application.properties`.


### Application description

## Hometask #1.1 (Data Access Object)

There is a *data class* [Book](src/main/java/com/kozel/bookstore/data/entity/Book.java) used in application, which contains following fields:

- (required) *Long* field `id`;
- (required) *String* field `name`;
- *String* field `isbn`;
- *Enum* field `cover`;
- (required) *String* field `author`;
- (required) *Integer* field `published_year`;
- *BigDecimal* field `price`.

There is also an *interface* [BookDao](src/main/java/com/kozel/bookstore/data/dao/BookDao.java) that interacts with the database and includes CRUD and some other methods:

- `Long addBook(Book book)` that adds a book to a database;
- `Book getById(Long id)` that finds a book by its *id* in a database;
- `Book getByIsbn(String isbn)` that finds a book by its *isbn* in a database;
- `List<Book> findByAuthor(String author)` that finds a **group of books** by their *author* in a database;
- `Book update(Book book)` that updates a selected book in a database;
- `boolean deleteById (Long id)` that deletes a book from a database;
- `long countAll()` that counts number of books in a database;
- `boolean updateRS(Book book)` that updates a selected book in a database using ***ResultSet*** methods;
- `void createRS(Book book)` that adds a book to a database using ***ResultSet*** methods;
- `void printTableInfo()` that displays information of a database table ***books***;

*Class* [BookDaoImpl](src/main/java/com/kozel/bookstore/data/dao/impl/BookDaoImpl.java) implements interface `BookDao` and carries out the methods described above.


## Hometask #1.2 (Service)

There is a *Data Transfer Object* [BookDto](src/main/java/com/kozel/bookstore/service/dto/BookDto.java) Having a similar structure to `Book` data class and another *DTO* [BookDtoShowing](src/main/java/com/kozel/bookstore/service/dto/BookDtoShowing.java) providing an abbreviated representation of a `Book` object for display to the user.

Also, there is an *interface* [BookService](src/main/java/com/kozel/bookstore/service/BookService.java) implementing basic CRUD operations and its implementation [BookServiceImpl](src/main/java/com/kozel/bookstore/service/impl/BookServiceImpl.java).

Interface [DataSource](src/main/java/com/kozel/bookstore/data/connection/DataSource.java) has a method `getConnection()`, which is implemented by class: [DataSourceImpl](src/main/java/com/kozel/bookstore/data/connection/impl/DataSourceImpl.java).

Connection parameters are read from the file [application.properties](src/main/resources/application.properties) (contains both parameters for the local and remote database) through the methods `getPropertiesLocal()` and `getPropertiesRemote()` of the `ConnectionProperties` class.

## Hometask #1.3 (User)

Added entity [User](src/main/java/com/kozel/bookstore/data/entity/User.java) and created all classes, Data and DTO similar to entity `Book`.

The project now uses *Maven* for build. Its code is in [pom.xml](pom.xml) file where its configuration settings are described and all dependencies necessary for the project to work are included.

*Logging* is connected to the project `(Log4j2)`. Its configuration settings are described in the [log4j2.xml](src/main/resources/log4j2.xml) file.

## Hometask #1.4 (Web HTTP)

There is a Controller layer implemented in next classes:
- `MainPageController`,being a servlet to the server's home page;
- `BookController`, which is a book information servlet;
- `UserController` is a user information servlet;
- `BooksController` is a servlet of list of all books;
- `UsersController` is a servlet of list of all users.

All these classes extend `HttpServlet` class and override the `doGet()` method. Servlets are registered using the `@WebServlet()` annotation.

The project is packed into a `war` archive, as a result of which it can be deployed on the server (`Tomcat` in this case).

## Hometask #1.5 (Patterns)

Previous controller classes were reworked: the *Front Controller* design pattern was applied.

A `FrontController` class of the same name was created, which is the only servlet of the application, the remaining classes were reworked into commands:

1. **Book** entity:
   - [BookCommand](src/main/java/com/kozel/bookstore/controller/impl/book/BookCommand.java) processes requests for information about a specific book;
   - [BookCreateCommand](src/main/java/com/kozel/bookstore/controller/impl/book/BookCreateCommand.java) creates a new book;
   - [BookCreatingCommand](src/main/java/com/kozel/bookstore/controller/impl/book/BookCreatingCommand.java) redirects to the page for creating a new book;
   - [BookDeleteCommand](src/main/java/com/kozel/bookstore/controller/impl/book/BookDeleteCommand.java) deletes a book;
   - [BooksCommand](src/main/java/com/kozel/bookstore/controller/impl/book/BooksCommand.java) displays a list of all books;
   - [EditBookCommand](src/main/java/com/kozel/bookstore/controller/impl/book/EditBookCommand.java) directs to the book information change page;
   - [UpdateBookCommand](src/main/java/com/kozel/bookstore/controller/impl/book/UpdateBookCommand.java) modifies information about the book.


2. The commands for the **User** entity are similar to the commands for the **Book** entity:

   - [EditUserCommand](src/main/java/com/kozel/bookstore/controller/impl/user/EditUserCommand.java);
   - [UpdateUserCommand](src/main/java/com/kozel/bookstore/controller/impl/user/UpdateUserCommand.java);
   - [UserCommand](src/main/java/com/kozel/bookstore/controller/impl/user/UserCommand.java);
   - [UserCreateCommand](src/main/java/com/kozel/bookstore/controller/impl/user/UserCreateCommand.java);
   - [UserCreatingCommand](src/main/java/com/kozel/bookstore/controller/impl/user/UserCreatingCommand.java);
   - [UserDeleteCommand](src/main/java/com/kozel/bookstore/controller/impl/user/UserDeleteCommand.java);
   - [UsersCommand](src/main/java/com/kozel/bookstore/controller/impl/user/UsersCommand.java).

The creation of all the main components of the application takes place in the `CommandFactory` class, which implements two design patterns at once: `Factory` and `Singleton`.

## Hometask #2.1 (Spring Framework)

The project has been migrated to Spring Framework. The framework is configured using a Java class [AppConfig](src/main/java/com/kozel/bookstore/AppConfig.java) and Annotation `@Component (@Repository, @Service, @Controller)`.
Removed unused classes:
- `CommandFactory`;
- `ConnectionProperties` and its implementation `ConnectionPropertiesImpl`;
- `IllegalCommandException`.