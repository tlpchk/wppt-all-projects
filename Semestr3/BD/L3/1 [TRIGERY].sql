USE lab3;

DELIMITER $$

create trigger `birthday_insert_check` before insert on pracownicy for each row
begin
    declare data_ur date;
    set data_ur = (SELECT `data urodzenia` FROM Ludzie WHERE NEW.Pesel=Ludzie.PESEL);
    
    IF ((YEAR(CURRENT_DATE) - YEAR(data_ur))-
		(DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(data_ur, '%m%d')))<18 THEN          
			signal sqlstate '12345'
			set message_text = 'Niepownoletni pracownik';
    end if;
end$$

create trigger `birthday_update_check` before update on pracownicy for each row
begin
	declare data_ur date;
    set data_ur = (SELECT `data urodzenia` FROM Ludzie WHERE NEW.Pesel=Ludzie.PESEL);
    
    IF ((YEAR(CURRENT_DATE) - YEAR(data_ur))-
		(DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(data_ur, '%m%d')))<18 THEN          
			signal sqlstate '12345'
			set message_text = 'Niepownoletni pracownik';
    end if;
end $$

create trigger `pesel_people_insert_check` before insert on ludzie for each row
begin
	DECLARE r_pesel varchar(2);
	DECLARE m_pesel varchar(2);
	DECLARE d_pesel varchar(2);
    
    SET r_pesel=substring(PESEL,1,2);
    SET m_pesel=substring(PESEL,3,2);
    SET d_pesel=substring(PESEL,5,2);
    
    IF (LENGTH(NEW.PESEL))!=11 THEN          
			signal sqlstate '12345'
			set message_text = 'Niedozwolony format PESEL';
	ELSEIF(	substring(NEW.`data urodzenia`,3,2)!=r_pesel
		AND substring(NEW.`data urodzenia`,6,2)!=m_pesel
        AND substring(NEW.`data urodzenia`,9,2)!=d_pesel) THEN
			signal sqlstate '12345'
			set message_text = 'Niedozwolona data urodzenia PESEL';
    end if;
end$$

create trigger `pesel_people_update_check` before update on ludzie for each row
begin
    IF (LENGTH(NEW.PESEL))!=11 THEN          
			signal sqlstate '12345'
			set message_text = 'Niedozwolony format PESEL';
    end if;
end$$

create trigger `pesel_workers_insert_check` before insert on Pracownicy for each row
begin
    IF (LENGTH(NEW.PESEL))!=11 THEN          
			signal sqlstate '12345'
			set message_text = 'Niedozwolony format PESEL';
		
    end if;
end$$

create trigger `pesel_workers_update_check` before update on Pracownicy for each row
begin
    IF (LENGTH(NEW.PESEL))!=11 THEN          
			signal sqlstate '12345'
			set message_text = 'Niedozwolony format PESEL';
    end if;
end$$

create trigger `sallary_insert_check` before insert on Pracownicy for each row
begin
    IF ((SELECT MIN(pensja) FROM Pracownicy WHERE zawod="Informatyk")*3<(NEW.pensja)) AND (NEW.zawod)="Informatyk" THEN          
			signal sqlstate '12345'
			set message_text = 'Informatyk zbyt dużo zarabia';
    end if;
end$$

create trigger `sallary_update_check` before update on Pracownicy for each row
begin
    IF ((SELECT MIN(pensja) FROM Pracownicy WHERE zawod="Informatyk")*3<(NEW.pensja)) AND (NEW.zawod)="Informatyk" THEN          
			signal sqlstate '12345'
			set message_text = 'Informatyk zbyt dużo zarabia';
    end if;
end$$

create trigger `age_insert_workers_check` before insert on Pracownicy for each row
begin
	declare wiek int;
    declare data_urodzenia date;
    
    set data_urodzenia=(SELECT `data urodzenia` FROM Ludzie l WHERE l.PESEL=NEW.PESEL);
    set wiek = (YEAR(CURRENT_DATE) - YEAR(data_urodzenia))-
				(DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(data_urodzenia, '%m%d'));
    IF wiek>65 AND NEW.zawod="Sprzedawca" THEN          
			signal sqlstate '12345'
			set message_text = 'Stary sprzedawca';
    end if;
end$$

create trigger `age_update_workers_check` before update on Pracownicy for each row
begin
    declare wiek int;
    declare data_urodzenia date;
    
    set data_urodzenia=(SELECT `data urodzenia` FROM Ludzie l WHERE l.PESEL=NEW.PESEL);
    set wiek = (YEAR(CURRENT_DATE) - YEAR(data_urodzenia))-
				(DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(data_urodzenia, '%m%d'));
    IF wiek>65 AND NEW.zawod="Sprzedawca" THEN          
			signal sqlstate '12345'
			set message_text = 'Stary sprzedawca';
    end if;
end$$

create trigger `age_insert_people_check` before insert on Ludzie for each row
begin
	declare wiek int;
    
    set wiek = (YEAR(CURRENT_DATE) - YEAR(NEW.`data urodzenia`))-
				(DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(NEW.`data urodzenia`, '%m%d'));
    IF (wiek>65) AND (SELECT zawod FROM pracownicy p WHERE p.PESEL=NEW.PESEL)="Sprzedawca" THEN          
			signal sqlstate '12345'
			set message_text = 'Stary sprzedawca';
    end if;
end$$

create trigger `age_update_people_check` before update on Ludzie for each row
begin
    declare wiek int;
    
    set wiek = (YEAR(CURRENT_DATE) - YEAR(NEW.`data urodzenia`))-
				(DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(NEW.`data urodzenia`, '%m%d'));
    IF (wiek>65) AND (SELECT zawod FROM pracownicy p WHERE p.PESEL=NEW.PESEL)="Sprzedawca" THEN          
			signal sqlstate '12345'
			set message_text = 'Stary sprzedawca';
    end if;
end$$

DELIMITER ;