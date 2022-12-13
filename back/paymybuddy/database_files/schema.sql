DROP DATABASE IF EXISTS paymybuddy;
CREATE DATABASE paymybuddy;
USE paymybuddy;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `friends` (
  `user_id` int NOT NULL,
  `friend_id` int NOT NULL,
  UNIQUE KEY `UKk3jl1difk6e2tixicas048c9o` (`user_id`,`friend_id`),
  KEY `FKqhlqyd2eb3nmk9hvrfqslw918` (`friend_id`),
  CONSTRAINT `FKivlhvh7odettdl818ml67apb9` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKqhlqyd2eb3nmk9hvrfqslw918` FOREIGN KEY (`friend_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `amount_received` double NOT NULL,
  `amount_sent` double NOT NULL,
  `date` datetime NOT NULL,
  `type` varchar(255) NOT NULL,
  `recipient_id` int DEFAULT NULL,
  `sender_id` int DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FKqnjjtp92qdclfkgp1qmi5nxm4` (`recipient_id`),
  KEY `FKjpter5yuohdb58gyg6k5nympt` (`sender_id`),
  CONSTRAINT `FKjpter5yuohdb58gyg6k5nympt` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKqnjjtp92qdclfkgp1qmi5nxm4` FOREIGN KEY (`recipient_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `unverified_user` (
  `unverified_user_id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `validation_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`unverified_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;