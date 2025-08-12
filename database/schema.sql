--
-- Table: covers
-- Description: Stores a dictionary of book cover types.
--
CREATE TABLE IF NOT EXISTS covers (

    id BIGSERIAL PRIMARY KEY,
    enum_value VARCHAR(63) UNIQUE NOT NULL

);

--
-- Table: books
-- Description: The main table for storing book information.
--
CREATE TABLE IF NOT EXISTS books (

	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	isbn VARCHAR(31) NOT NULL,
	cover_id BIGINT NOT NULL REFERENCES covers,
	author VARCHAR (255),
	published_year INT,
	price DECIMAL(15,2),
	is_deleted BOOLEAN DEFAULT FALSE
);

--
-- Index: unique_isbn_book_deleted
-- Description: Ensures that only one active book with a given ISBN can exist.
--
CREATE UNIQUE INDEX unique_isbn_book_deleted
ON books (isbn)
WHERE is_deleted = false;


--
-- Table: roles
-- Description: Stores a dictionary of user roles (e.g., 'SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CUSTOMER').
--
CREATE TABLE IF NOT EXISTS roles (

    id BIGSERIAL PRIMARY KEY,
    enum_value VARCHAR(63) UNIQUE NOT NULL

);

--
-- Table: users
-- Description: Stores information about users of the system.
--
CREATE TABLE IF NOT EXISTS users (

	id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	email VARCHAR(255),
	login VARCHAR(255) NOT NULL,
	role_id BIGINT NOT NULL REFERENCES roles,
	is_deleted BOOLEAN DEFAULT FALSE

);

--
-- Index: unique_login_user_deleted
-- Description: Ensures that only one active user with a given login can exist.
--
CREATE UNIQUE INDEX unique_login_user_deleted
ON users (login)
WHERE is_deleted = false;

--
-- Table: user_hash
-- Description: Stores password hashes and salts for users. Separated from the users table for security.
--
CREATE TABLE IF NOT EXISTS user_hash (
    user_id BIGINT PRIMARY KEY REFERENCES users(id),
    salt VARCHAR(255),
    hashed_password VARCHAR(255)
);

--
-- Table: statuses
-- Description: Stores a dictionary of order statuses.
--
CREATE TABLE IF NOT EXISTS statuses(

    id BIGSERIAL PRIMARY KEY,
    enum_value VARCHAR(63) UNIQUE NOT NULL

);

--
-- Table: orders
-- Description: Stores information about orders placed by users.
--
CREATE TABLE IF NOT EXISTS orders (

	id BIGSERIAL PRIMARY KEY,
	date TIMESTAMP,
	user_id BIGINT NOT NULL REFERENCES users,
	status_id BIGINT NOT NULL REFERENCES statuses,
	price DECIMAL(15,2)
);

--
-- Table: order_items
-- Description: Stores the details of each item within an order.
--
CREATE TABLE IF NOT EXISTS order_items (

	id BIGSERIAL PRIMARY KEY,
	quantity INT,
	price DECIMAL(15,2),
	order_id BIGINT NOT NULL REFERENCES orders
);

--
-- Table: carts
-- Description: Stores information about user shopping carts. Each user has one unique cart.
--
CREATE TABLE IF NOT EXISTS carts (

	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT UNIQUE REFERENCES users,
	total_price DECIMAL(15,2)
);

--
-- Table: cart_items
-- Description: Stores the list of items inside a shopping cart.
--
CREATE TABLE IF NOT EXISTS cart_items (

	id BIGSERIAL PRIMARY KEY,
	book_id BIGINT NOT NULL REFERENCES books,
	quantity INT,
	price DECIMAL(15,2),
	cart_id BIGINT NOT NULL REFERENCES carts
);

--
-- Table: ordered_books
-- Description: A snapshot of book data at the time of an order.
-- Prevents data loss if a book is later modified or deleted.
--
CREATE TABLE IF NOT EXISTS ordered_books (
    order_item_id BIGINT PRIMARY KEY REFERENCES order_items,
    original_book_id BIGINT NOT NULL REFERENCES books,
    name VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(255),
    published_year INT,
    price_at_order NUMERIC(15, 2)
);












