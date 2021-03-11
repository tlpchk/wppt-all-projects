use `db-electronics`;
drop procedure if exists zad5b;
delimiter $$

create procedure zad5b (IN indx int)
begin
	declare max_diagonal decimal;
    
	select max(Przekatna) into max_diagonal from Matryce join Laptopy on laptopy.Matryca=matryce.ID  and laptopy.Producent=indx;
	select model from laptopy join Matryce on laptopy.Matryca=matryce.ID  and laptopy.Producent=indx and matryce.Przekatna = max_diagonal;
end$$
delimiter ;

use `db-electronics`;
call zad5b(5);