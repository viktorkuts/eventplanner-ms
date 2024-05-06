DROP TABLE venues IF EXISTS;

CREATE TABLE IF NOT EXISTS venues (
                        id INT NOT NULL AUTO_INCREMENT ,
                        venueid VARCHAR(40),
                        venuename VARCHAR(128),
                        availablestart TIMESTAMP,
                        availableend TIMESTAMP,
                        maxblockallocation INTEGER,
                        capacity INTEGER,
                        streetaddress	VARCHAR(50),
                        city			VARCHAR(50),
                        province		VARCHAR(50),
                        country			VARCHAR(50),
                        postalcode		VARCHAR(9),
                        PRIMARY KEY(id)
);