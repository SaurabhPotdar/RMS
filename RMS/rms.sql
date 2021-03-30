-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.62 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table rms.company
CREATE TABLE IF NOT EXISTS `company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_address` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `email_id` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  KEY `FKomqgqf7c205nh472fs61uokpb` (`logo`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table rms.company: 2 rows
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`company_id`, `company_address`, `company_name`, `created_by`, `created_date`, `email_id`, `modified_by`, `modified_date`, `password`, `role`, `logo`) VALUES
	(1, 'ABC', 'Flipkart', 'DIN19000798/192.168.0.106', '2020-11-19 14:23:14', 'saurabh2@gmail.com', 'DIN19000798/192.168.0.106', '2020-11-19 14:23:14', 'RMSsau@123', NULL, NULL),
	(2, 'Banglore', 'Amazon', 'DIN19000798/192.168.0.106', '2021-03-25 18:05:43', 'saura11@gmail.com', 'DIN19000798/192.168.0.106', '2021-03-25 18:05:43', 'RMSsau@123', NULL, NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Dumping structure for table rms.files
CREATE TABLE IF NOT EXISTS `files` (
  `id` varchar(255) NOT NULL,
  `data` longblob,
  `file_name` varchar(255) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table rms.files: 0 rows
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
/*!40000 ALTER TABLE `files` ENABLE KEYS */;

-- Dumping structure for table rms.job
CREATE TABLE IF NOT EXISTS `job` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `minimum_experience` int(11) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `qualification` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  KEY `FK5q04favsasq8y70bsei7wv8fc` (`company_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table rms.job: 7 rows
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` (`job_id`, `company_name`, `created_by`, `created_date`, `designation`, `minimum_experience`, `location`, `modified_by`, `modified_date`, `qualification`, `salary`, `company_id`) VALUES
	(1, 'Flipkart', 'DIN19000798/192.168.0.106', '2020-11-19 14:24:22', 'DEV', 0, 'PUNE', 'DIN19000798/192.168.0.106', '2020-11-19 14:24:22', NULL, 0, 1),
	(2, 'Flipkart', 'DIN19000798/192.168.0.106', '2020-11-19 14:26:11', 'DEV', 0, 'MUMBAI', 'DIN19000798/192.168.0.106', '2020-11-19 14:26:11', NULL, 0, 1),
	(3, 'Flipkart', 'DIN19000798/192.168.0.106', '2021-03-25 18:02:06', 'SOFTWARE DEVELOPER', 2, 'BANGLORE', 'DIN19000798/192.168.0.106', '2021-03-25 18:02:06', 'B.Tech', 50000, 1),
	(4, 'Flipkart', 'DIN19000798/192.168.0.106', '2021-03-25 18:02:26', 'SOFTWARE DEVELOPER', 4, 'PUNE', 'DIN19000798/192.168.0.106', '2021-03-25 18:02:26', 'B.Tech', 50000, 1),
	(5, 'Amazon', 'DIN19000798/192.168.0.106', '2021-03-25 18:06:50', 'DATA SCIENTIST', 2, 'PUNE', 'DIN19000798/192.168.0.106', '2021-03-25 18:06:50', 'B.Tech', 100000, 2),
	(6, 'Flipkart', 'DIN19000798/192.168.0.106', '2021-03-25 18:08:26', 'DATA ANALYST', 0, 'BANGLORE', 'DIN19000798/192.168.0.106', '2021-03-25 18:08:26', NULL, 0, 1),
	(7, 'Amazon', 'DIN19000798/192.168.0.106', '2021-03-25 18:09:15', 'DATA ANALYST', 0, 'MUMBAI', 'DIN19000798/192.168.0.106', '2021-03-25 18:09:15', NULL, 0, 2);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;

-- Dumping structure for table rms.qualification
CREATE TABLE IF NOT EXISTS `qualification` (
  `qualification_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cgpa` float DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`qualification_id`),
  KEY `FKd8sa91pko8gn9i81xnehbhwrp` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table rms.qualification: 0 rows
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;

-- Dumping structure for table rms.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `email_id` varchar(255) DEFAULT NULL,
  `years_of_experience` int(11) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `file_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKr4b9pnvewx61bnm9r70s9yfoi` (`file_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table rms.user: 3 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `created_by`, `created_date`, `email_id`, `years_of_experience`, `first_name`, `modified_by`, `modified_date`, `password`, `phone_number`, `position`, `role`, `file_id`) VALUES
	(1, 'DIN19000798/192.168.0.106', '2020-11-19 13:15:53', 'saura@gmail.com', 2, 'Saurabh', 'DIN19000798/192.168.0.106', '2020-11-19 13:15:53', 'RMSsau@123', '9999999999', 'SOFTWARE DEVELOPER', 'User', NULL),
	(4, 'DIN19000798/192.168.0.106', '2020-11-19 14:18:28', 'saurabh1@gmail.com', 2, 'Saurabh', 'DIN19000798/192.168.0.106', '2020-11-19 14:18:28', 'RMSsau@123', '9999999999', 'SOFTWARE DEVELOPER', 'User', NULL),
	(3, 'DIN19000798/192.168.0.106', '2020-11-19 13:18:56', 'saurabh@gmail.com', 2, 'Saurabh', 'DIN19000798/192.168.0.106', '2020-11-19 13:18:56', 'RMSsau@123', '9999999999', 'WEB DEVELOPER', 'User', NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table rms.user_job
CREATE TABLE IF NOT EXISTS `user_job` (
  `user_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`job_id`),
  KEY `FKia2o1pm0plymfbt26ps56ox5l` (`job_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table rms.user_job: 2 rows
/*!40000 ALTER TABLE `user_job` DISABLE KEYS */;
INSERT INTO `user_job` (`user_id`, `job_id`) VALUES
	(4, 1),
	(4, 2);
/*!40000 ALTER TABLE `user_job` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
