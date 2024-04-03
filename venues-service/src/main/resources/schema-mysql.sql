DROP TABLE IF EXISTS venues;

CREATE TABLE venues (
                         id INT AUTO_INCREMENT NOT NULL,
                         venueid VARCHAR(40),
                         availablestart DATE,
                         availableend DATE,
                         maxblockallocation INTEGER,
                         capacity INTEGER,
                         streetaddress	VARCHAR(50),
                         city			VARCHAR(50),
                         province		VARCHAR(50),
                         country			VARCHAR(50),
                         postalcode		VARCHAR(9),
                         PRIMARY KEY(id)
);