use `db-electronics`;
alter table producenci drop liczba_modeli;
alter table producenci add liczba_modeli INT;
-- 

use `db-electronics`;
drop procedure if exists zad11;

delimiter $$
create procedure zad11 () 
begin
	declare suma INT;
    declare id1 INT;
    declare i INT default 0;
    declare lim INT;
    set lim = (select count(*) from producenci where producenci.liczba_modeli is null);
    while i<lim do
		set  id1=(select ID from producenci where producenci.liczba_modeli is null order by ID ASC limit 1);
		set suma=(select count(*) from laptopy where laptopy.Producent=id1);
		update producenci set liczba_modeli=suma where ID=id1;
        set i = i+1;
	end while;
end$$


delimiter ;
-- 
use `db-electronics`;
call zad11;
--
delimiter $$
create trigger trigger_11a after insert on laptopy
for each row
begin
	update producenci set liczba_modeli=liczba_modeli+1 where ID=NEW.Producent and liczba_modeli is not null;
    update producenci set liczba_modeli=1 where ID=NEW.Producent and liczba_modeli is null;
end$$

create trigger trigger_11b after delete on laptopy
for each row
begin
	update producenci set liczba_modeli=liczba_modeli-1 where ID=OLD.Producent;
end$$

create trigger trigger_11c after update on laptopy
for each row
begin
	update producenci set liczba_modeli=liczba_modeli-1 where ID=OLD.Producent;
    update producenci set liczba_modeli=liczba_modeli+1 where ID=NEW.Producent;
end$$

delimiter ;