USE dbelectronics;

CREATE TABLE Laptopy (
    Model varchar(30) not null primary key,
    Producent int unsigned not null,
    Procesor int unsigned not null,
    Matryca int unsigned not null,
    Typ enum('biurowy', 'gaming', 'ultrabook','inny')
);

CREATE TABLE Procesory(
	ID int auto_increment primary key,
    Model varchar(50),
    Rdzenie int,
    Czestotliwosc float
);

CREATE TABLE Matryce(
	ID int auto_increment primary key,
    Przekatna decimal,
    sRGB float,
    aRGB float
);

 CREATE TABLE Producenci(
	ID int auto_increment primary key,
    Nazwa varchar(50),
    Siedziba char(20)
);