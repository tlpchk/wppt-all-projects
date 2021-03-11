USE lab3;

DELIMITER $$

CREATE PROCEDURE dodaj_ludzie_i_pracownicow(IN ilosc int,IN dataurod date,IN zaw varchar(50),IN def_PESEL char(11))
BEGIN
	declare iter int;
    declare wzrst float;
    declare wga float;
    declare but int;
    declare pens float;
     
    set iter = 0;
    set pens = RAND()*5000;
    
	WHILE iter<ilosc 
    DO
		set def_PESEL=def_PESEL+1;
		set dataurod = DATE_ADD(dataurod,INTERVAL 3 DAY);
		set wzrst = (RAND()*40)+160;
		set wga = (RAND()*40)+60;
		set but = FLOOR((RAND()*6))+39;
        
        insert into ludzie (PESEL,imię,nazwisko,`data urodzenia`,wzrost,waga,`rozmiar buta`) values
		(def_PESEL,concat("Imię_",zaw,"a",iter),concat("Nazwisko_",zaw,"a",iter),dataurod,wzrst,wga,but);
        
        insert into pracownicy (PESEL,zawod,pensja) values(def_PESEL,zaw,(2*pens+RAND()*pens));
		
        set iter=iter+1;
    END WHILE;
END$$
DELIMITER ;

call dodaj_ludzie_i_pracownicow(1,"1990-03-03","Malarz","90030356789");
call dodaj_ludzie_i_pracownicow(5,"1988-02-02","Piłkarz","88020254321");
call dodaj_ludzie_i_pracownicow(13,"1997-01-01","Informatyk","97010124321");
call dodaj_ludzie_i_pracownicow(20,"1993-01-09","Reporter","93010951234");
call dodaj_ludzie_i_pracownicow(77,"1985-05-08","Sprzedawca","85050891234");
