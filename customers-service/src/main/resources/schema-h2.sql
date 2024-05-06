DROP TABLE customers IF EXISTS CASCADE;
DROP TABLE customer_phonenumbers IF EXISTS CASCADE;
CREATE TABLE IF NOT EXISTS customers (
                           id				INT NOT NULL AUTO_INCREMENT,
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
                                      type			VARCHAR(50) DEFAULT 'HOME',
                                      number			VARCHAR(50)
);
