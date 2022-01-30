DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS AUTHORS;

CREATE TABLE GENRES(ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NAME VARCHAR(255), CONSTRAINT GenreName UNIQUE (NAME));
CREATE TABLE AUTHORS(ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NAME VARCHAR(255), CONSTRAINT AuthorName UNIQUE (NAME));
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, TITLE VARCHAR(255), AUTHOR INT references AUTHORS(ID), GENRE INT references GENRES(ID), CONSTRAINT AuthorTitle UNIQUE (TITLE, AUTHOR));

insert into GENRES (id, name) values (1, 'adventures');
insert into GENRES (id, name) values (2, 'horror');
insert into GENRES (id, name) values (3, 'fantasy');
insert into GENRES (id, name) values (4, 'lyric');
insert into GENRES (id, name) values (5, 'thriller');
insert into GENRES (id, name) values (6, 'drama');
insert into GENRES (id, name) values (7, 'comedy');

