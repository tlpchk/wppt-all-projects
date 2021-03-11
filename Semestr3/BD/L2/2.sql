use `db-electronics`;

create table Procesory(
	ID int unsigned not null auto_increment,
    Model varchar(50) not null,
    Rdzenie int unsigned not null,
    Czestotliwosc float unsigned not null,
    primary key(ID)
);

create table Matryce(
	ID int unsigned not null auto_increment,
    Przekatna decimal not null,
    sRGB float unsigned not null , 
    aRGB float unsigned not null ,
	primary key(ID)
);

 create table Producenci(
	ID int unsigned not null auto_increment,
    Nazwa varchar(50),
    Siedziba char(20),
    primary key(ID)
);

create table Laptopy (
    Model varchar(30),
    Producent int unsigned,
    Procesor int unsigned,
    Matryca int unsigned,
    Typ enum('biurowy', 'gaming', 'ultrabook','inny'),
    primary key(Model),
    foreign key(Producent) references Producenci(ID),
    foreign key(Procesor) references Procesory(ID),
    foreign key(Matryca) references Matryce(ID)
);

alter table Procesory
auto_increment=3000;

delimiter $$
create trigger `check_values`
before insert on matryce
for each row
begin
    IF NEW.aRGB<0 or NEW.aRGB>100 or NEW.sRGB<0 or NEW.sRGB>100 or NEW.sRGB<NEW.aRGB THEN          
         signal sqlstate '12345'
		 set message_text = 'Niedozwolone liczby';
    end if;
end$$

delimiter ;