use `db-electronics`;
drop function if exists zad5;
delimiter $$

create function zad5 (indx int) RETURNS VARCHAR(30) DETERMINISTIC
begin
	declare max_diagonal decimal;
	declare result varchar(30);
    
	select max(Przekatna) into max_diagonal from Matryce join Laptopy on laptopy.Matryca=matryce.ID  and laptopy.Producent=indx;
	select model into result from laptopy join Matryce on laptopy.Matryca=matryce.ID  and laptopy.Producent=indx and matryce.Przekatna = max_diagonal limit 1;
    return result;
end$$
delimiter ;

use `db-electronics`;
select zad5(8);