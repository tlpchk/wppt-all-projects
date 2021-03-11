use lab3;

DROP PROCEDURE IF EXISTS zad3; 
DELIMITER $$
CREATE PROCEDURE zad3(IN agg char(5),IN kol varchar(20))

BEGIN
	IF (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ludzie' AND Column_Name = kol) is NOT NULL
	THEN
		IF ((agg='MIN' OR agg='MAX' OR agg='COUNT' OR agg='SUM' OR agg='AVG') )  then
			SET @sql = CONCAT('SELECT ',agg,'(`', kol,'`)', ' FROM ludzie');
			PREPARE stmt FROM @sql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
		ELSE
			signal sqlstate '12345'
			set message_text = 'Niema takiej funkcji agg';
		end if;
	ELSE
		leave zad3;
    end if;
END$$
DELIMITER ;
CALL zad3('MAX','rozmiar buta');
