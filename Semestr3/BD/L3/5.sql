use lab3;

-- a
SELECT MIN(s) FROM (SELECT SUM(pensja) s FROM pracownicy GROUP by zawod) a;

-- najmłodsze pracownicy
DELIMITER $$
CREATE procedure young()
BEGIN
	DECLARE max_rocznik varchar(2);
    SET max_rocznik = substring((SELECT MAX(PESEL) FROM pracownicy),1,2);
	SELECT AVG(pensja) FROM pracownicy WHERE PESEL LIKE CONCAT(max_rocznik,'%');
END$$

DELIMITER ;

call young();