Drop DATABASE `mybank` ;

CREATE DATABASE `mybank` ;

USE `mybank` ;

CREATE TABLE `users` (
  `username` varchar(45) NOT NULL PRIMARY KEY,
  `email` varchar(80) NOT NULL,
  `password` text NOT NULL,
  `amount` float unsigned DEFAULT 0,
  `user_type` ENUM('user', 'admin') NOT NULL DEFAULT 'user'
) DEFAULT CHARSET=latin1;



CREATE TABLE `transfers`(
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sender` varchar(45) NOT NULL,
    `receiver` varchar(45) NOT NULL,
    `title` varchar(100),
    `amount` float unsigned,
    `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) DEFAULT CHARSET=latin1;

