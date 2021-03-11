drop database if exists `tk-soft`;
create database `tk-soft`;
use `tk-soft`;
CREATE TABLE `clients` (
  `Mail` varchar(30),
  `Name` varchar(20) DEFAULT NULL,
  `Surname` varchar(20) DEFAULT NULL,
  `Phone_number` int DEFAULT NULL,
  PRIMARY KEY (`Mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employees` (
  `Login` varchar(20) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Surname` varchar(20) NOT NULL,
  `Post` enum('Worker','Admin') NOT NULL,
  `Mail` varchar(30) NOT NULL,
  `Tel_Num` varchar(12) NOT NULL,
  `Birthday` date DEFAULT NULL,
  PRIMARY KEY (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `producers` (
  `Name` varchar(20),
  `Country` varchar(30) NOT NULL,
  `Contact` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `products` (
  `Name` varchar(20) NOT NULL,
  `Producer_Name` varchar(20) NOT NULL,
  `Count_keys` int(11) NOT NULL,
  `Price` float NOT NULL,
  PRIMARY KEY (`Name`),
  FOREIGN KEY (`Producer_Name`) REFERENCES `producers` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `keys_tab` (
  `ID` int(11) NOT NULL auto_increment,
  `Product_Name` varchar(20) NOT NULL,
  `Key_col` varchar(30) NOT NULL,
  `Status` enum('Used','Unused') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Product_Name` (`Product_Name`),
  CONSTRAINT `keys_tab_ibfk_1` FOREIGN KEY (`Product_Name`) REFERENCES `products` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orders` (
  `ID` int(11) NOT NULL auto_increment,
  `Product_Name` varchar(20) NOT NULL,
  `Status` enum('Delivered','Undelivered') DEFAULT 'Undelivered',
  `Client_Email` varchar(30) NOT NULL,
  `Count` int(11) DEFAULT NULL,
  `Details` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`Product_Name`) REFERENCES `products` (`Name`),
  FOREIGN KEY (`Client_Email`) REFERENCES `clients` (`Mail`)
) ;

CREATE TABLE `passwords` (
  `Login` varchar(20) DEFAULT NULL,
  `Password` varchar(16) DEFAULT NULL,
  KEY `Login` (`Login`),
  CONSTRAINT `passwords_ibfk_1` FOREIGN KEY (`Login`) REFERENCES `employees` (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TRIGGER IF EXISTS count_check;
DELIMITER $$
CREATE TRIGGER count_check 
    BEFORE UPDATE ON ORDERS
    FOR EACH ROW 
BEGIN
    IF((SELECT COUNT(keys_tab.ID) FROM keys_tab WHERE keys_tab.Product_Name = orders.Name)<1) THEN SIGNAL SQLSTATE '45000';
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS pass_check;
DELIMITER $$
CREATE TRIGGER pass_check 
    BEFORE INSERT ON passwords
    FOR EACH ROW 
BEGIN
    IF(length(New.`Password`)<8 || length(New.`Password`)>16) THEN SIGNAL SQLSTATE '45000';
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS keys_count;
DELIMITER $$
CREATE TRIGGER keys_count 
    AFTER INSERT ON keys_tab
    FOR EACH ROW 
BEGIN
    UPDATE products SET Count_keys = (SELECT COUNT(keys_tab.ID) FROM keys_tab WHERE keys_tab.Product_Name = products.Name);
END$$
DELIMITER ;
DROP PROCEDURE IF EXISTS keys_proc;
/*DELIMITER //
CREATE PROCEDURE keys_proc()
   BEGIN
		set @a := 1;
		while @a<30 do
			set @a:=@a+1;
            INSERT INTO keys_tab (Product_NAME, key_col) values ('Avast Premium', concat('XXXXXXX',@a+100));
		end while;
   END //
 DELIMITER ;
 call keys_proc();*/

DROP TRIGGER IF EXISTS mail_check;
DELIMITER $$
CREATE TRIGGER mail_check 
    BEFORE INSERT ON clients
    FOR EACH ROW 
BEGIN
    IF(length(NEW.mail)<10 || length(NEW.mail)>25 || NEW.mail NOT LIKE '_%_%_@_%._%') THEN SIGNAL SQLSTATE '45000';
    END IF;
END$$
DELIMITER ;


DELIMITER $$

create procedure addClient(`Name` varchar(20), Surname varchar(20), Mail varchar(30),Phone_number int)
BEGIN
	insert into clients (Name,Surname,Mail,Phone_number) values (Name,Surname,Mail,Phone_number);
END$$

DELIMITER ;

DELIMITER $$

create procedure addOrder(Pr_Name varchar(20), Mail varchar(30), c int,det varchar(300))
BEGIN
	insert into orders(Product_Name,Client_Email,`count`,details) values (Pr_Name, Mail,c,det);
END$$

DELIMITER ;
