use `db-electronics`;

drop trigger if exists zad8;

delimiter $$
create trigger zad8 after delete on laptopy
for each row
begin
	if (select count(*) from laptopy where laptopy.Procesor=OLD.Procesor)=0 then
		delete from procesory where procesory.ID=OLD.Procesor;
    end if;
end$$
delimiter ;