USE lab3;
DROP PROCEDURE IF exists payment;
DROP FUNCTION if Exists pay_a_person;
DELIMITER $$

CREATE FUNCTION pay_a_person(pes char(11)) RETURNS CHAR(22) DETERMINISTIC
BEGIN
		return CONCAT('********',SUBSTRING(pes,9,3),', wypłacono');
		
END$$

CREATE PROCEDURE payment(IN budget FLOAT,IN zaw varchar(50))
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE paidSallary FLOAT;
    DECLARE pesel_of_person varchar(11);
	DECLARE myCursor1 CURSOR FOR SELECT pensja FROM pracownicy p WHERE p.zawod=zaw;
	DECLARE myCursor2 CURSOR FOR SELECT PESEL FROM pracownicy p WHERE p.zawod=zaw;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN myCursor1;
	OPEN myCursor2;
    
    SET autocommit = 0;
	
	START TRANSACTION;
    CREATE TABLE wypłaty( info varchar(22) );
		REPEAT
			FETCH myCursor1 INTO paidSallary;
			FETCH myCursor2 INTO pesel_of_person;
			IF NOT done THEN
				SET budget=budget-paidSallary;
                insert into wypłaty (info) value (pay_a_person(pesel_of_person));
			END IF;
            IF (budget<0) THEN
				ROLLBACK;
                
			END IF;
		UNTIL done END REPEAT;
    COMMIT;
    
    SELECT * FROM wypłaty;
    DROP TABLE wypłaty;
    
    CLOSE myCursor1;
    CLOSE myCursor2;
END$$
DELIMITER $$

call payment(1000000,'Piłkarz');