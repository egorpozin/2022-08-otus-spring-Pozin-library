DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    id                  BIGINT   PRIMARY KEY,
    name                VARCHAR(255)
);
DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR
(
    id                   BIGINT   PRIMARY KEY,
    full_name            VARCHAR(255)
);
DROP TABLE IF EXISTS BOOK;
CREATE TABLE book
(
    id                   BIGINT   PRIMARY KEY,
    author_id            BIGINT,
    title                VARCHAR(255),
    genre_id             BIGINT,
    FOREIGN KEY (author_id) REFERENCES AUTHOR(id),
    FOREIGN KEY (genre_id) REFERENCES GENRE(id)
);