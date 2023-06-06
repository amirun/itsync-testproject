CREATE TABLE IF NOT EXISTS BOOK
(
    id          INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    author      VARCHAR(255),
    genre       VARCHAR(255),
    description VARCHAR(2000),
    volume_count INT,
    create_date  TIMESTAMP,
    type        VARCHAR(255),
    CONSTRAINT pk_book PRIMARY KEY (id)
);