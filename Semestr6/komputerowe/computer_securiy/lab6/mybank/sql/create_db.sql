Drop DATABASE `mybank` ;

CREATE DATABASE `mybank` ;

USE `mybank` ;

CREATE TABLE `users` (
  `username` varchar(100) NOT NULL PRIMARY KEY,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `amount` float(8) unsigned DEFAULT 0
) DEFAULT CHARSET=latin1;

CREATE TABLE `transfers`(
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sender` varchar(100) NOT NULL,
    `receiver` varchar(100) NOT NULL,
    `title` varchar(100),
    `amount` float(8) unsigned,
    `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) DEFAULT CHARSET=latin1;

