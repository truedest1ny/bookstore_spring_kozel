INSERT INTO covers (enum_value) VALUES

    ('HARD'),
    ('SOFT'),
    ('SPECIAL');


INSERT INTO books (name, isbn, cover_id, author, published_year, price) VALUES

	('Best practice of SQL', '9781212326534', (SELECT id FROM covers WHERE enum_value = 'SOFT'), 'Alex Rynkov', 2018, 10.06),
	('Naked Space', '9781212388512', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Steven Hawking', 2008, 39.99),
	('The Goat', '9783454327554', (SELECT id FROM covers WHERE enum_value = 'HARD'), 'Dean Cun', 2022, 44.55),
	('The best girlfriend', '9781332336537', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Alex Revva', 2011, 199.99),
	('Don Quixote', '9781732344511', (SELECT id FROM covers WHERE enum_value = 'SOFT'), 'Miguel de Cervantes', 2015, 40.02),
	('Alice Adventures in Wonderland', '9781213216516', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Lewis Carroll', 2016, 52.12),
	('The Adventures of Huckleberry Finn', '9784554327311', (SELECT id FROM covers WHERE enum_value = 'HARD'), 'Mark Twain', 2012, 79.99),
	('The Adventures of Tom Sawyerd', '9781332785321', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Mark Twain', 2004, 169.99),
	('Pride and Prejudice', '9781778731211', (SELECT id FROM covers WHERE enum_value = 'HARD'), 'Jane Austen', 2013, 12.16),
	('Wuthering Heights', '9781232452387', (SELECT id FROM covers WHERE enum_value = 'SOFT'), 'Emily Bronte', 1989, 7.99),
	('Jane Eyre', '9787855663312', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Charlotte Bronte', 2007, 12.14),
	('Gone with the Wind (part 1)', '9785665788799', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Margaret Mitchell', 2008, 16.74),
	('Moby Dick', '9781213561795', (SELECT id FROM covers WHERE enum_value = 'HARD'), 'Herman Melville', 2014, 13.13),
	('The Scarlet Letter', '9784564231667', (SELECT id FROM covers WHERE enum_value = 'HARD'), 'Nathaniel Hawthorne', 2010, 23.56),
	('Gulliver Travels', '9781364327811', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Jonathan Swift', 1999, 78.11),
	('The Pilgrim Progress', '9781732451222', (SELECT id FROM covers WHERE enum_value = 'HARD'), 'John Bunyan', 2000, 41.00),
	('A Christmas Carol', '9781213256452', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Charles Dickens', 2024, 67.76),
	('David Copperfield', '9784555633311', (SELECT id FROM covers WHERE enum_value = 'SOFT'), 'Charles Dickens', 2010, 123.00),
	('A Tale of Two Cities', '9781332745888', (SELECT id FROM covers WHERE enum_value = 'SPECIAL'), 'Charles Dickens', 1996, 71.12),
	('Treasure Island', '9784523996556', (SELECT id FROM covers WHERE enum_value = 'SOFT'), 'Robert Louis Stevenson', 2012, 34.56);

	-----------------------------------------------------------------------------------------------------------------------

	INSERT INTO roles (enum_value) VALUES

        ('SUPER_ADMIN'),
        ('ADMIN'),
        ('MANAGER'),
        ('CUSTOMER');


    INSERT INTO users (first_name, last_name, email, login, password, role_id) VALUES

    	('Donald', 'Roberson', 'donaldroberson@gmail.com', 'RoadTrip', '123456789', (SELECT id FROM roles WHERE enum_value = 'SUPER_ADMIN')),
    	('Clinton', 'Smith', 'clintonsmith@gmail.com', 'GhostRider', '342345', (SELECT id FROM roles WHERE enum_value = 'ADMIN')),
    	('Marion', 'Rowe', 'marionrowe1122@gmail.com', 'user001', '33355543', (SELECT id FROM roles WHERE enum_value = 'MANAGER')),
    	('Douglas', 'Brown', 'douglasbrown23@gmail.com', 'farmer', 'varsus34', (SELECT id FROM roles WHERE enum_value = 'MANAGER')),
        ('Kevin', 'Townsend', 'kevintownsendddd@gmail.com', 'ooofaloooo', 'frdewws34', (SELECT id FROM roles WHERE enum_value = 'MANAGER')),
        ('Eric', 'Johnson', 'ericjohnsoon@gmail.com', 'gefesting', 'oberst', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Sam', 'Moore', 'sammoore345@gmail.com', 'byte45676', 'bytresfvy', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Randall', 'Norton', 'randallnnnnorton@gmail.com', 'revolution64', 'fvfvfgtgr', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Albert', 'James', 'albertjames453@gmail.com', 'variable42', 'instant12', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Carl', 'Flores', 'carlflores321@gmail.com', 'Billion222', 'v5g52dcc', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Gerald', 'Morgan', 'geraldmorgan234@gmail.com', 'Vm443x', 'codepass12', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Donald', 'Rivera', 'donaldrivera@gmail.com', 'BYTEFACE445', 'gdfgdgd', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Richard', 'Guzman', 'richardguzman567@gmail.com', 'NUMLONGC122', 'bydfgdsf', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Harry', 'Ruiz', 'harryruizgbf@gmail.com', 'vebm765', '12345677789', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Allen', 'Yates', 'allenyates@gmail.com', 'MELENIAL', 'fgsddwwqqq', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('David', 'Wilson', 'davidwilson@gmail.com', 'NEMBERTATE', 'bnghdss4445', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Travis', 'Bailey', 'travis_b_ailey@gmail.com', 'TERRA756', '56563321', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Thomas', 'Ward', 'thomas_ward_556@gmail.com', 'LongBoy', '67884432', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('Tony', 'Ward', 'tonyqward322@gmail.com', 'MArriedLaStChance', '6743234566', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER')),
        ('David', 'Peterson', 'davidpeterson@gmail.com', 'sadyboyyyy', '56776644', (SELECT id FROM roles WHERE enum_value = 'CUSTOMER'));

