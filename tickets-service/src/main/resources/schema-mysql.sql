DROP TABLE IF EXISTS tickets;

CREATE TABLE tickets (
                         id INT AUTO_INCREMENT NOT NULL,
                         ticketid VARCHAR(40),
                         purchasetime DATE,
                         validated BOOLEAN,
                         userid VARCHAR(50),
                         PRIMARY KEY(id)
--                          FOREIGN KEY(userid) REFERENCES users(userid)
);