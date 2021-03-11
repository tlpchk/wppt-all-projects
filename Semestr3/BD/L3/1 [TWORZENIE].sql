-- DROP database lab3;
CREATE DATABASE lab3;

use lab3;
CREATE TABLE Ludzie (
	PESEL char(11),
    imię varchar(30),
    nazwisko varchar(30),
    `data urodzenia` date,
	wzrost float unsigned,
    waga float unsigned,
    `rozmiar buta` int unsigned,
    PRIMARY KEY(PESEL)
);

CREATE TABLE Pracownicy(
	PESEL char(11),
    zawod varchar(50),
    pensja float unsigned,
    PRIMARY KEY(PESEL)
);
