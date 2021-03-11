use `db-electronics`;

drop function if exists zad7;

delimiter $$
create function zad7 (indx INT) returns INT deterministic
begin
    return (select count(*) from laptopy Where laptopy.Procesor=indx);
end$$
delimiter ;

select zad7(3003);