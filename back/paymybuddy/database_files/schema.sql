DROP DATABASE paymybuddy;
CREATE DATABASE paymybuddy;
USE paymybuddy;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `user_id` int NOT NULL,
  `friend_id` int NOT NULL,
  UNIQUE KEY `UKk3jl1difk6e2tixicas048c9o` (`user_id`,`friend_id`),
  KEY `FKqhlqyd2eb3nmk9hvrfqslw918` (`friend_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `amount_received` double NOT NULL,
  `amount_sent` double NOT NULL,
  `date` datetime NOT NULL,
  `type` varchar(255) NOT NULL,
  `recipient_id` int NOT NULL,
  `sender_id` int NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FKqnjjtp92qdclfkgp1qmi5nxm4` (`recipient_id`),
  KEY `FKjpter5yuohdb58gyg6k5nympt` (`sender_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Table structure for table `transfer`
--

DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer` (
  `transfer_id` int NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`transfer_id`),
  KEY `FKddbjk8kle2s7siw04lua30sjl` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `unverified_user`
--

DROP TABLE IF EXISTS `unverified_user`;
CREATE TABLE `unverified_user` (
  `unverified_user_id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `validation_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`unverified_user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

