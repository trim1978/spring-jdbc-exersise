DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS BOOKS;
CREATE TABLE GENRES(ID INT PRIMARY KEY, NAME VARCHAR(255));
CREATE TABLE AUTHORS(ID INT PRIMARY KEY, NAME VARCHAR(255));
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY, NAME VARCHAR(255), AUTHOR INT references AUTHORS(ID),GENRE INT references GENRES(ID));

insert into GENRES (id, name) values (1, 'adventures');
insert into GENRES (id, name) values (2, 'horror');
insert into GENRES (id, name) values (3, 'fantasy');
insert into GENRES (id, name) values (4, 'lyric');
