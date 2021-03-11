-- DROP DATABASE data_logs;
CREATE DATABASE data_logs;
USE data_logs;
CREATE TABLE data_tracking(
	`tracking_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `PESEL` varchar(11) NOT NULL,
	`old_value` INT NOT NULL ,
	`new_value` INT NOT NULL ,
	`modified` DATETIME NOT NULL,
    `user`varchar(20) NOT NULL
);

USE lab3;
DROP TRIGGER IF exists `update_pensja`;
DELIMITER $$
CREATE TRIGGER `update_pensja` AFTER UPDATE ON pracownicy
FOR EACH ROW
BEGIN
    IF (NEW.pensja != OLD.pensja) THEN
        INSERT INTO data_logs.data_tracking (PESEL,old_value,new_value,modified,`user`) value (NEW.PESEL,OLD.pensja,NEW.pensja,NOW(),CURRENT_USER());
    END IF;
END$$

DELIMITER ;

use lab3;
update pracownicy
SET pensja = pensja + 100
WHERE pensja < 7000;