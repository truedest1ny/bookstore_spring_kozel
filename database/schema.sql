
CREATE TABLE IF NOT EXISTS covers (

    id BIGSERIAL PRIMARY KEY,
    enum_value VARCHAR(63) UNIQUE NOT NULL

);


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

CREATE UNIQUE INDEX unique_isbn_book_deleted
ON books (isbn)
WHERE is_deleted = false;



CREATE TABLE IF NOT EXISTS roles (

    id BIGSERIAL PRIMARY KEY,
    enum_value VARCHAR(63) UNIQUE NOT NULL

);

CREATE TABLE IF NOT EXISTS users (

	id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	email VARCHAR(255),
	login VARCHAR(255) NOT NULL,
	role_id BIGINT NOT NULL REFERENCES roles,
	is_deleted BOOLEAN DEFAULT FALSE

);

CREATE TABLE IF NOT EXISTS user_hash (
    user_id BIGINT PRIMARY KEY REFERENCES users(id),
    salt VARCHAR(255),
    hashed_password VARCHAR(255)
);

CREATE UNIQUE INDEX unique_login_user_deleted
ON users (login)
WHERE is_deleted = false;


CREATE TABLE IF NOT EXISTS statuses(

    id BIGSERIAL PRIMARY KEY,
    enum_value VARCHAR(63) UNIQUE NOT NULL

);

CREATE TABLE IF NOT EXISTS orders (

	id BIGSERIAL PRIMARY KEY,
	date TIMESTAMP,
	user_id BIGINT NOT NULL REFERENCES users,
	status_id BIGINT NOT NULL REFERENCES statuses,
	price DECIMAL(15,2)
);

CREATE TABLE IF NOT EXISTS order_items (

	id BIGSERIAL PRIMARY KEY,
	quantity INT,
	price DECIMAL(15,2),
	order_id BIGINT NOT NULL REFERENCES orders
);

CREATE TABLE IF NOT EXISTS carts (

	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT UNIQUE REFERENCES users,
	total_price DECIMAL(15,2)
);

CREATE TABLE IF NOT EXISTS cart_items (

	id BIGSERIAL PRIMARY KEY,
	book_id BIGINT NOT NULL REFERENCES books,
	quantity INT,
	price DECIMAL(15,2),
	cart_id BIGINT NOT NULL REFERENCES carts
);

CREATE TABLE IF NOT EXISTS ordered_books (
    order_item_id BIGINT PRIMARY KEY REFERENCES order_items,
    original_book_id BIGINT NOT NULL REFERENCES books,
    name VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(255),
    published_year INT,
    price_at_order NUMERIC(15, 2)
);












