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

There is also an *interface* `BookDao` that interacts with the database and includes CRUD and some other methods:

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

*Class* `BookDaoImpl` implements interface `BookDao` and carries out the methods described above.


## Hometask #1.2 (Service)

There is a *Data Transfer Object* `BookDto` Having a similar structure to `Book` data class and another *DTO* `BookDtoShowing` providing an abbreviated representation of a `Book` object for display to the user.

Also, there is an *interface* [BookService](src/main/java/com/kozel/bookstore/service/BookService.java) implementing basic CRUD operations and its implementation [BookServiceImpl](src/main/java/com/kozel/bookstore/service/impl/BookServiceImpl.java).

Interface `DataSource` has a method `getConnection()`, which is implemented by class: `DataSourceImpl`.

Connection parameters are read from the file `application.properties` (contains both parameters for the local and remote database) through the methods `getPropertiesLocal()` and `getPropertiesRemote()` of the `ConnectionProperties` class.

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

## Hometask #2.2 (Spring JDBC Template)

DAO classes have been redesigned: now they use `NamedParameterJdbcTemplate` and external `DataSource (Hikari Connection Pool)` optimizing code and performance.

## Hometask #2.3 (Order. Complex Business Tier)

Added [Order](src/main/java/com/kozel/bookstore/data/entity/Order.java) entity and auxiliary entity [OrderItem](src/main/java/com/kozel/bookstore/data/entity/OrderItem.java).

Added a _**Repository**_ layer (related to the Data layer), which is an abstraction over the DAO. The necessity of its use is caused by the appearance of a complex Order object, the creation of which requires the use of different DAOs:

- [BookRepository](src/main/java/com/kozel/bookstore/data/repository/BookRepository.java) - uses `BookDao`;
- [UserRepository](src/main/java/com/kozel/bookstore/data/repository/UserRepository.java) - uses `UserDao`;
- [OrderRepository](src/main/java/com/kozel/bookstore/data/repository/OrderRepository.java)- uses `BookDao`, `UserDao`, `OrderItemDao` and `OrderDao`.

Added **_DTO classes_** for the Data layer:

- `BookDto`;
- `OrderDto`;
- `OrderItemDto`;
- `UserDto`.

The DTO classes of the service layer have been renamed to eliminate duplicate class names.

Added **_commands_** for the controller to search for an order by ID, a list of all orders, and a list of all orders for a specific user:

- [OrderCommand](src/main/java/com/kozel/bookstore/controller/impl/order/OrderCommand.java) displays an order by its ID;
- [OrdersCommand](src/main/java/com/kozel/bookstore/controller/impl/order/OrdersCommand.java) displays a list of all orders;
- [FilterOrdersByUserCommand](src/main/java/com/kozel/bookstore/controller/impl/order/FilterOrdersByUserCommand.java) displays a form with a selection of the user whose orders will be displayed;
- [OrdersByUserCommand](src/main/java/com/kozel/bookstore/controller/impl/order/OrdersByUserCommand.java) displays the selected user's orders.

Added **_JSP pages_** to these commands:

- [order.jsp](src/main/webapp/jsp/order/order.jsp);
- [orders.jsp](src/main/webapp/jsp/order/orders.jsp);
- [orders_by_user.jsp](src/main/webapp/jsp/order/orders_by_user.jsp);
- [filter_orders_by_user.jsp](src/main/webapp/jsp/order/filter_orders_by_user.jsp);
- [order_not_found.jsp](src/main/webapp/jsp/error/404/order_not_found.jsp).

## Hometask #2.4 (ORM. JPA. Hibernate)

The project has been migrated to **_JPA (Hibernate)_** technology. Now Repository-layer will interact with the database directly, without using DAO-classes. 

Because of this, the _DAO_ layer has been removed, as well as the _DTOs_ used for passing between the DAO and Repository:

- ~~BookDto~~;
- ~~OrderDto~~;
- ~~OrderItemDto~~;
- ~~UserDto~~;
- **~~BookDao~~**;
- **~~UserDao~~**;
- **~~OrderItemDao~~**;
- **~~OrderDao~~**.

Added [persistence.xml](src/main/resources/META-INF/persistence.xml), which is the configuration file for JPA.

The **Hibernate** project uses **_Hikari Connection Pool_** to manage connections to the database.

To organise the structure of the displayed data in the Entity layer, the _‘Deleted’_ flag filter is used. Its declaration can be found in the file [package-info.java](src/main/java/com/kozel/bookstore/data/entity/package-info.java).