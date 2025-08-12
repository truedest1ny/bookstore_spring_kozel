# Bookstore Web Application

## Overview

This is a web application for a bookstore, built with **Spring MVC** and **JSP**. The application provides a platform for managing books, user accounts, and orders, with a focus on clean architecture, security, and maintainability. It features a robust **role-based access control (RBAC)** system implemented with custom servlet filters and a soft-delete mechanism for key entities.

### Key Features

* **User Management**: Role-based user accounts for Super Admin, Admin, Manager, and Customer.
* **Book Catalog**: A comprehensive catalog of books with details like author, price, and cover type.
* **Shopping Cart**: Functionality for adding books to a cart and managing order items.
* **Order Processing**: Users can place orders, and managers can approve or archive them.
* **Security**: Custom authentication and authorization filters to secure access to different parts of the application. Passwords are stored securely using salted hashing.
* **Database**: The application uses a PostgreSQL database with a normalized schema, including foreign keys and unique indexes for data integrity.

-----

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

* **Java Development Kit (JDK) 8 or higher**
* **Apache Maven**
* **PostgreSQL database**
* An IDE (e.g., IntelliJ IDEA, Eclipse) for running the application.

### Installation and Setup

1.  **Clone the repository**:

```bash
    git clone https://github.com/truedest1ny/bookstore_spring_kozel.git
    cd bookstore
```

2.  **Set up the Database**:

   * Create a new database in your PostgreSQL instance.
   * Execute the SQL scripts located in the project's root directory. These scripts create the necessary tables, indexes, and populate the initial data.
      * The provided SQL file ([schema.sql](database/schema.sql)) should be run to create the tables, indexes; [data.sql](database/data.sql) would insert the initial data.
```sql 
      -- data.sql
      
      -- User 1: superAdmin
      -- Login: 'superAdmin', Password: 'superAdmin', Role: SUPER_ADMIN
      
      INSERT INTO users (first_name, last_name, email, login, role_id, is_deleted) VALUES
        ('Super', 'Admin', 'super.admin@bookstore.com', 'superAdmin', 1, FALSE);
        
      INSERT INTO user_hash (user_id, salt, hashed_password) VALUES
        (LASTVAL(), 'ejsb0o7Tve36m5zmH0G76Q==', '48H2/FhYfXcatEt0xk9dju4rlpI1d9/XKncxzfs2B/M=');
```

3.  **Configure the Application**:

Open [application.yml](src/main/resources/application.yml) and verify that the database connection properties are correct.
Update the `spring.datasource` section with your PostgreSQL username and password if they differ from the defaults.

   ```yaml
      #application.yml

      spring:
        datasource:
          url: jdbc:postgresql://localhost:5432/bookstore_bh
          username: postgres
          password: root
   ```    

4.  **Run the Application**:

   * Using your IDE, run the `Main` class.
   * Alternatively, use Maven to build and run the application:

```bash
    mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

-----

## Application Architecture

The application follows a layered, service-oriented architecture:

* **Controllers**: Handle incoming HTTP requests and manage the flow of data between the web layer and the service layer.
* **Services**: Contain the core business logic of the application. They are responsible for transactions and orchestrating interactions with repositories.
* **Repositories**: Interact with the database. They use JPA (Hibernate) to perform CRUD operations on the data models.
* **Filters**: Custom `WebAuthenticationFilter` and `WebAuthorizationFilter` intercept requests to enforce security policies.
* **Views**: The presentation layer is built with JSP (JavaServer Pages) and uses a custom view resolver to locate templates.

## Database Schema

The database schema is designed for a relational bookstore model, with several key tables:

* **`books`**: Main table for books, with a `is_deleted` flag for soft deletion.
* **`users`**: Stores user information with a foreign key to the `roles` table. Also uses soft deletion.
* **`user_hash`**: A separate table for storing user password hashes and salts, improving security by separating sensitive data.
* **`carts`** and **`cart_items`**: Manages the shopping cart for each user.
* **`orders`** and **`order_items`**: Manages orders placed by users.
* **`ordered_books`**: A crucial table that stores a snapshot of a book's information at the time of an order, ensuring historical data integrity even if the original book record is modified or deleted.
* **`covers`**, **`roles`**, **`statuses`**: Dictionary tables for lookup values, ensuring data consistency.

The full database schema with descriptions is available in the provided SQL script.

-----

## Documentation

The codebase is thoroughly documented with Javadoc for all public classes and methods. This documentation provides a clear understanding of the application's design, purpose, and functionality, making it easier to navigate and maintain the code.

```java

/**
 * A servlet filter for enforcing role-based access control to web resources.
 * This filter intercepts incoming requests and, based on a set of configured
 * rules, either grants or denies access to the resource.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebAuthorizationFilter extends HttpFilter implements PathMatcher
```

-----

## Contributing

If you'd like to contribute, please fork the repository and submit a pull request.

-----

## Contact

For any questions or feedback, please contact [sensitiveperson@mail.ru](mailto:sensitiveperson@mail.ru).
