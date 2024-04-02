DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS customer_phonenumbers;

CREATE TABLE customers (
	id				SERIAL,
	customerid		VARCHAR(50),
    customertype    VARCHAR(50) DEFAULT 'CUSTOMER',
	firstname		VARCHAR(50),
	lastname		VARCHAR(50),
	emailaddress	VARCHAR(50),
	streetaddress	VARCHAR(50),
	city			VARCHAR(50),
	province		VARCHAR(50),
	country			VARCHAR(50),
	postalcode		VARCHAR(9),
	
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS customer_phonenumbers(
	custinternalid	INTEGER,
	type			VARCHAR(50),
	number			VARCHAR(50)
);
