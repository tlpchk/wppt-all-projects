use `db-electronics`;

drop procedure if exists insert_new_laptop;
drop procedure if exists a_lot_of_laptops;

delimiter $$
create procedure insert_new_laptop ()
begin
	declare random_procesor INT;
	declare random_matryca INT;
	declare random_producent INT;
	declare random_typ INT;
    declare index_laptop INT;
    declare typ enum('biurowy', 'gaming', 'ultrabook','inny');
    
    set index_laptop = (select count(*) From laptopy);
	set random_procesor = floor(3000+(RAND()*15));
	set random_matryca = floor(1+(RAND()*15));
	set random_producent = floor(1+(RAND()*15));
    set random_typ = floor(rand()*4);
    case 
		WHEN random_typ = 0 THEN set typ = 'biurowy';
		WHEN random_typ = 1 THEN set typ = 'gaming';
		WHEN random_typ = 2 THEN set typ = 'ultrabook';
		ELSE set typ = 'inny';
	end case;
    
    insert into laptopy values (concat("RandLaptop",index_laptop),random_producent,random_procesor,random_matryca,typ);
end $$


create procedure a_lot_of_laptops ()
begin
	declare i int default 1200;
	while i>0 do
         call insert_new_laptop;
         set i=i-1;
	end while;
end
$$

DELIMITER ;
use `db-electronics`;
call a_lot_of_laptops;