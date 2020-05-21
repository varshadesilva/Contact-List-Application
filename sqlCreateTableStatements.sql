CREATE TABLE `address` (
  `Address_id` int NOT NULL AUTO_INCREMENT,
  `Contact_id` int NOT NULL,
  `Address_type` varchar(25) DEFAULT NULL,
  `Address` varchar(256) DEFAULT NULL,
  `City` varchar(25) DEFAULT NULL,
  `State` varchar(10) DEFAULT NULL,
  `Zip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Address_id`),
  KEY `fk_address_contact` (`Contact_id`),
  KEY `address_idx` (`Address_type`,`Address`,`City`,`State`,`Zip`),
  CONSTRAINT `fk_address_contact` FOREIGN KEY (`Contact_id`) REFERENCES `contact` (`Contact_id`)
) 


CREATE TABLE `contact` (
  `Contact_id` int NOT NULL AUTO_INCREMENT,
  `Fname` varchar(32) DEFAULT NULL,
  `Mname` varchar(32) DEFAULT NULL,
  `Lname` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`Contact_id`),
  KEY `contact_idx` (`Fname`,`Mname`,`Lname`)
) 

CREATE TABLE `dates` (
  `Date_id` int NOT NULL AUTO_INCREMENT,
  `Contact_id` int NOT NULL,
  `Date_type` varchar(25) NOT NULL,
  `calendar_date` date NOT NULL,
  PRIMARY KEY (`Date_id`),
  KEY `fk_dates_contact` (`Contact_id`),
  CONSTRAINT `fk_dates_contact` FOREIGN KEY (`Contact_id`) REFERENCES `contact` (`Contact_id`)
) 

CREATE TABLE `phone` (
  `Phone_id` int NOT NULL AUTO_INCREMENT,
  `Contact_id` int NOT NULL,
  `Phone_type` varchar(25) NOT NULL,
  `Area_code` varchar(10) NOT NULL,
  `Phone_number` varchar(25) NOT NULL,
  PRIMARY KEY (`Phone_id`),
  KEY `fk_phone_contact` (`Contact_id`),
  KEY `phone_idx` (`Phone_type`,`Area_code`,`Phone_number`),
  CONSTRAINT `fk_phone_contact` FOREIGN KEY (`Contact_id`) REFERENCES `contact` (`Contact_id`)
) 