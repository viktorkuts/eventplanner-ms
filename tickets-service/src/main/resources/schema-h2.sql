DROP TABLE tickets IF EXISTS ;

CREATE TABLE IF NOT EXISTS tickets (
                         id INT NOT NULL AUTO_INCREMENT,
                         ticketid VARCHAR(40),
                         purchasetime DATE,
                         validated BOOLEAN,
                         userid VARCHAR(50),
                         PRIMARY KEY(id)
);