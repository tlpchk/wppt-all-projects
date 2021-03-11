use `db-electronics`;

drop trigger if exists zad6;

delimiter $$
create trigger zad6 before insert on laptopy
for each row
begin
	IF (select ID From producenci where ID=NEW.Producent) is null THEN
		insert into producenci (ID) values (NEW.Producent);
	END IF;
end$$
delimiter ;
use `db-electronics`;
insert into laptopy (Model,Producent,Procesor,Matryca,Typ) values ("NewLaptop1",70,3001,12,'inny');