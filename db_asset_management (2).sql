-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 01, 2019 at 11:13 AM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_asset_management`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_cart` (IN `employeeid` VARCHAR(25), IN `assetid` VARCHAR(25))  NO SQL
BEGIN
	DECLARE datedelete DATETIME;
    
    SET datedelete = (SELECT DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i'), INTERVAL 2 MINUTE));

	INSERT tb_tr_cart (delete_date,asset,employee) VALUES (datedelete,assetid,employeeid);

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_detail_asset` (IN `name` VARCHAR(50), IN `datein` DATETIME, IN `penaltycost` INT(11), IN `asset` VARCHAR(10), IN `status` VARCHAR(10))  BEGIN
 DECLARE new_id int;
 DECLARE kode VARCHAR(10); 
 SELECT MAX(RIGHT(id, 4)) INTO new_id FROM tb_tr_asset_detail;
 
 IF new_id < 10 THEN SET kode = CONCAT(asset,'/000',new_id+1);
 ELSEIF new_id < 100 THEN SET kode = CONCAT(asset,'/00',new_id+1);
 ELSEIF new_id < 1000 THEN SET kode = CONCAT(asset,'/0',new_id+1);
 END IF;
 INSERT INTO tb_tr_asset_detail (id,name,date_in,penalty_cost,asset,status) VALUES(kode,name,datein,penaltycost,asset,status);
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_request` (IN `note` VARCHAR(100), IN `assetid` VARCHAR(10), IN `typeid` VARCHAR(10), IN `requesterid` VARCHAR(10), IN `reportid` VARCHAR(25))  BEGIN
 DECLARE years int;
 DECLARE months int;
 DECLARE bln CHAR(2);
 DECLARE kode VARCHAR(25);
 DECLARE kd int;
 DECLARE rep VARCHAR(25);
 
 SET years = (SELECT YEAR(CURRENT_DATE()));
 SET months = (SELECT MONTH(CURRENT_DATE()));
 
 SELECT MAX(id) INTO kode FROM tb_tr_request;
 SET kd = (SELECT SUBSTRING(kode,13));
 
 IF reportid = '' THEN 
 INSERT INTO tb_tr_request (id,request_date,note,current_status,asset,type,requester,report) VALUES(CONCAT(typeid,'/',years,'/',LPAD(months,2,0),'/',kd+1),SYSDATE(),note,'0',assetid,typeid,requesterid,CONCAT('',null));
 ELSE INSERT INTO tb_tr_request (id,request_date,note,current_status,asset,type,requester,report) VALUES(CONCAT(typeid,'/',years,'/',LPAD(months,2,0),'/',kd+1),SYSDATE(),note,'0',assetid,typeid,requesterid,CONCAT('',reportid));
 END IF;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_request_item` (IN `reqid` VARCHAR(25), IN `emplid` VARCHAR(25))  NO SQL
BEGIN
	
    INSERT INTO tb_tr_request_item (request, asset)
    SELECT reqid, asset FROM tb_tr_cart WHERE employee = emplid;
    DELETE FROM tb_tr_cart WHERE employee = emplid;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_status_request` (IN `catatan` TEXT, IN `statusid` VARCHAR(10), IN `requestid` VARCHAR(25), IN `approver` VARCHAR(10))  NO SQL
BEGIN
DECLARE years int;
 DECLARE months int;
 DECLARE bln CHAR(2);
 DECLARE kode VARCHAR(25);
 DECLARE kd int;
 
 SET years = (SELECT YEAR(CURRENT_DATE()));
 SET months = (SELECT MONTH(CURRENT_DATE())); 
 
 SELECT MAX(id) INTO kode FROM tb_tr_request_status;
 SET kd = (SELECT SUBSTRING(kode,9));
 
 INSERT INTO tb_tr_request_status VALUES (CONCAT(years,'/',LPAD(months,2,0),'/',kd+1), SYSDATE(),catatan,statusid,requestid,approver);
 
 UPDATE tb_tr_request SET current_status = statusid WHERE id = requestid;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_stock_asset` (IN `assetid` VARCHAR(25))  NO SQL
BEGIN
	DECLARE param VARCHAR(25);
    
    SET param = (SELECT LEFT(assetid, 2));

	UPDATE tb_m_asset SET stock = stock+1 WHERE id = param;   

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_on_delete_cart` (IN `assetid` VARCHAR(25))  NO SQL
BEGIN
	DECLARE param VARCHAR(25);
    
    SET param = (SELECT LEFT(assetid, 2));

	UPDATE tb_tr_asset_detail SET status = '3' WHERE id = assetid;
    

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_schedule_delete` ()  NO SQL
BEGIN
   DELETE FROM tb_tr_cart WHERE delete_date = NOW();
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_status_after_returned` (IN `itemid` VARCHAR(25), IN `assetid` VARCHAR(25))  NO SQL
BEGIN
    DECLARE date_return DATETIME;
	
    SELECT return_date INTO date_return FROM tb_tr_request_item WHERE id = itemid;
    
    IF date_return IS NOT NULL THEN
    UPDATE tb_tr_asset_detail SET status = '3' WHERE id = assetid;  
	END IF;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_stock_asset` (IN `assetid` VARCHAR(25))  NO SQL
BEGIN
	DECLARE param VARCHAR(25);
    
    SET param = (SELECT LEFT(assetid, 2));

	UPDATE tb_m_asset SET stock = stock-1 WHERE id = param;  
    
    UPDATE tb_tr_asset_detail SET status = '2' WHERE id = assetid;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_approval_status`
--

CREATE TABLE `tb_m_approval_status` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_approval_status`
--

INSERT INTO `tb_m_approval_status` (`id`, `name`) VALUES
('0', 'Waiting Approval From Manager'),
('1', 'Accepted By Manager and Waiting Approval From GA'),
('2', 'Rejected By Manager'),
('3', 'Rejected By GA'),
('4', 'Request Approved'),
('5', 'Have Picked'),
('6', 'Request Reported');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_asset`
--

CREATE TABLE `tb_m_asset` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `stock` int(11) NOT NULL,
  `category` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_asset`
--

INSERT INTO `tb_m_asset` (`id`, `name`, `stock`, `category`) VALUES
('LP', 'Laptop', 13, '1'),
('PY', 'Proyektor', 13, '1');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_category`
--

CREATE TABLE `tb_m_category` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_category`
--

INSERT INTO `tb_m_category` (`id`, `name`) VALUES
('1', 'electronic'),
('2', 'mebel'),
('3', 'card');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_employee`
--

CREATE TABLE `tb_m_employee` (
  `id` varchar(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `birth_place` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `gender` enum('Male','Female') DEFAULT NULL,
  `nationality` enum('WNI','WNA') NOT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `manager` varchar(11) DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_employee`
--

INSERT INTO `tb_m_employee` (`id`, `first_name`, `last_name`, `email`, `birth_place`, `birth_date`, `gender`, `nationality`, `photo`, `manager`, `is_delete`) VALUES
('1', 'Super Admin', NULL, 'mii.bootcamp29@gmail.com', 'sumsel', '2019-09-09', 'Male', 'WNI', NULL, '5', 0),
('15231', 'Radian', 'Khrisna', 'khrisna@gmail.com', 'Klaten', '2019-09-03', 'Male', 'WNI', NULL, '2', 0),
('15232', 'Reza', 'Andhika', 'reza@gmail.com', 'Pemalang', '2019-09-01', 'Male', 'WNI', NULL, '15231', 0),
('2', 'Muhammad', 'Affanul Halim', 'wahyusukses28@gmail.com', 'Sumsesl', '2019-09-04', 'Male', 'WNI', NULL, '3', 0),
('3', 'Wirya', 'Likwan Ibrahim', 'wahyukncro@gmail.com', 'jakarta', '2019-09-03', 'Female', 'WNI', NULL, '1', 0),
('4', 'Yosua', 'Kurniawan', 'yosuak24@gmail.com', 'japan', '2019-09-04', 'Male', 'WNI', NULL, '3', 0),
('5', 'Wahyu', NULL, 'wahyukncro@gmail.com', 'jakart', '2019-09-03', NULL, 'WNI', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_request_type`
--

CREATE TABLE `tb_m_request_type` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_request_type`
--

INSERT INTO `tb_m_request_type` (`id`, `name`) VALUES
('REP', 'Report'),
('REQ', 'Request');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_room`
--

CREATE TABLE `tb_m_room` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_room`
--

INSERT INTO `tb_m_room` (`id`, `name`) VALUES
('1', 'Amsterdam'),
('2', 'Miami');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_status`
--

CREATE TABLE `tb_m_status` (
  `id` varchar(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_status`
--

INSERT INTO `tb_m_status` (`id`, `name`) VALUES
('-1', 'Not verified'),
('0', 'Verified'),
('1', 'Login Failed 1'),
('2', 'Login Failed 2'),
('3', 'Lock');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_status_asset`
--

CREATE TABLE `tb_m_status_asset` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_status_asset`
--

INSERT INTO `tb_m_status_asset` (`id`, `name`) VALUES
('1', 'In room'),
('2', 'In person'),
('3', 'Ready'),
('4', 'Broken'),
('5', 'Lost'),
('6', 'Returned');

-- --------------------------------------------------------

--
-- Table structure for table `tb_m_supplier`
--

CREATE TABLE `tb_m_supplier` (
  `id` varchar(25) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_m_supplier`
--

INSERT INTO `tb_m_supplier` (`id`, `name`) VALUES
('SP0001', 'PT ABC'),
('SP0002', 'PT BCD');

-- --------------------------------------------------------

--
-- Table structure for table `tb_tr_asset_detail`
--

CREATE TABLE `tb_tr_asset_detail` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `date_in` datetime DEFAULT NULL,
  `penalty_cost` int(11) NOT NULL,
  `asset` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL,
  `room` varchar(10) DEFAULT NULL,
  `supplier` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_tr_asset_detail`
--

INSERT INTO `tb_tr_asset_detail` (`id`, `name`, `date_in`, `penalty_cost`, `asset`, `status`, `room`, `supplier`) VALUES
('LP/0001', 'Laptop Lenovo 1', '2019-09-30 00:00:00', 2000000, 'LP', '3', NULL, 'SP0001'),
('PY/0002', 'Proyektor 2', '2019-09-30 00:00:00', 2000000, 'PY', '3', NULL, 'SP0002');

--
-- Triggers `tb_tr_asset_detail`
--
DELIMITER $$
CREATE TRIGGER `tr_update_stock_after_returned` AFTER UPDATE ON `tb_tr_asset_detail` FOR EACH ROW IF new.status = '3' THEN
	CALL sp_add_stock_asset(new.asset);
END IF
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_tr_cart`
--

CREATE TABLE `tb_tr_cart` (
  `id` int(11) NOT NULL,
  `delete_date` datetime DEFAULT NULL,
  `employee` varchar(25) NOT NULL,
  `asset` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `tb_tr_cart`
--
DELIMITER $$
CREATE TRIGGER `tr_update_asset_on_delete` AFTER DELETE ON `tb_tr_cart` FOR EACH ROW CALL sp_on_delete_cart(old.asset)
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tr_update_status_aseet_detail` AFTER INSERT ON `tb_tr_cart` FOR EACH ROW CALL sp_update_stock_asset(new.asset)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_tr_request`
--

CREATE TABLE `tb_tr_request` (
  `id` varchar(25) NOT NULL,
  `request_date` datetime NOT NULL,
  `note` text,
  `current_status` varchar(10) DEFAULT NULL,
  `type` varchar(10) NOT NULL,
  `current_approval` varchar(10) DEFAULT NULL,
  `requester` varchar(10) NOT NULL,
  `report` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `tb_tr_request`
--
DELIMITER $$
CREATE TRIGGER `tr_add_request_item` AFTER INSERT ON `tb_tr_request` FOR EACH ROW CALL sp_add_request_item(new.id, new.requester)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_tr_request_item`
--

CREATE TABLE `tb_tr_request_item` (
  `id` int(11) NOT NULL,
  `return_date` datetime DEFAULT NULL,
  `request` varchar(25) NOT NULL,
  `asset` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_tr_request_item`
--

INSERT INTO `tb_tr_request_item` (`id`, `return_date`, `request`, `asset`) VALUES
(5, '2019-10-01 00:00:00', ' REQ/2019/09/24/0001', 'PY/0002');

--
-- Triggers `tb_tr_request_item`
--
DELIMITER $$
CREATE TRIGGER `tr_update_status_returned` AFTER UPDATE ON `tb_tr_request_item` FOR EACH ROW CALL sp_update_status_after_returned(old.id,old.asset)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_tr_request_status`
--

CREATE TABLE `tb_tr_request_status` (
  `id` varchar(25) NOT NULL,
  `date_time` datetime NOT NULL,
  `note` text NOT NULL,
  `status` varchar(10) NOT NULL,
  `request` varchar(25) NOT NULL,
  `approver` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_m_approval_status`
--
ALTER TABLE `tb_m_approval_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_m_asset`
--
ALTER TABLE `tb_m_asset`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category`);

--
-- Indexes for table `tb_m_category`
--
ALTER TABLE `tb_m_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_m_employee`
--
ALTER TABLE `tb_m_employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `manager` (`manager`),
  ADD KEY `manager_2` (`manager`);

--
-- Indexes for table `tb_m_request_type`
--
ALTER TABLE `tb_m_request_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_m_room`
--
ALTER TABLE `tb_m_room`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_m_status`
--
ALTER TABLE `tb_m_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_m_status_asset`
--
ALTER TABLE `tb_m_status_asset`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_m_supplier`
--
ALTER TABLE `tb_m_supplier`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_tr_asset_detail`
--
ALTER TABLE `tb_tr_asset_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `status` (`status`),
  ADD KEY `asset` (`asset`),
  ADD KEY `room` (`room`),
  ADD KEY `supplier` (`supplier`);

--
-- Indexes for table `tb_tr_cart`
--
ALTER TABLE `tb_tr_cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employee` (`employee`),
  ADD KEY `asset` (`asset`);

--
-- Indexes for table `tb_tr_request`
--
ALTER TABLE `tb_tr_request`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `report` (`report`),
  ADD KEY `type` (`type`),
  ADD KEY `current_approval` (`current_approval`),
  ADD KEY `requester` (`requester`),
  ADD KEY `current_status` (`current_status`);

--
-- Indexes for table `tb_tr_request_item`
--
ALTER TABLE `tb_tr_request_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `asset` (`asset`),
  ADD KEY `request` (`request`);

--
-- Indexes for table `tb_tr_request_status`
--
ALTER TABLE `tb_tr_request_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `request` (`request`),
  ADD KEY `approver` (`approver`),
  ADD KEY `status` (`status`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_tr_cart`
--
ALTER TABLE `tb_tr_cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tb_tr_request_item`
--
ALTER TABLE `tb_tr_request_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_m_asset`
--
ALTER TABLE `tb_m_asset`
  ADD CONSTRAINT `tb_m_asset_ibfk_1` FOREIGN KEY (`category`) REFERENCES `tb_m_category` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tb_m_employee`
--
ALTER TABLE `tb_m_employee`
  ADD CONSTRAINT `tb_m_employee_ibfk_1` FOREIGN KEY (`manager`) REFERENCES `tb_m_employee` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tb_tr_asset_detail`
--
ALTER TABLE `tb_tr_asset_detail`
  ADD CONSTRAINT `tb_tr_asset_detail_ibfk_1` FOREIGN KEY (`room`) REFERENCES `tb_m_room` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_asset_detail_ibfk_2` FOREIGN KEY (`status`) REFERENCES `tb_m_status_asset` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_asset_detail_ibfk_3` FOREIGN KEY (`asset`) REFERENCES `tb_m_asset` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_asset_detail_ibfk_4` FOREIGN KEY (`supplier`) REFERENCES `tb_m_supplier` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tb_tr_cart`
--
ALTER TABLE `tb_tr_cart`
  ADD CONSTRAINT `tb_tr_cart_ibfk_1` FOREIGN KEY (`asset`) REFERENCES `tb_tr_asset_detail` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_cart_ibfk_2` FOREIGN KEY (`employee`) REFERENCES `tb_m_employee` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tb_tr_request`
--
ALTER TABLE `tb_tr_request`
  ADD CONSTRAINT `tb_tr_request_ibfk_2` FOREIGN KEY (`requester`) REFERENCES `tb_m_employee` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_request_ibfk_3` FOREIGN KEY (`current_status`) REFERENCES `tb_m_approval_status` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_request_ibfk_4` FOREIGN KEY (`type`) REFERENCES `tb_m_request_type` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_request_ibfk_5` FOREIGN KEY (`current_approval`) REFERENCES `tb_m_employee` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_request_ibfk_6` FOREIGN KEY (`report`) REFERENCES `tb_tr_request` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tb_tr_request_item`
--
ALTER TABLE `tb_tr_request_item`
  ADD CONSTRAINT `tb_tr_request_item_ibfk_1` FOREIGN KEY (`asset`) REFERENCES `tb_tr_asset_detail` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `tb_tr_request_status`
--
ALTER TABLE `tb_tr_request_status`
  ADD CONSTRAINT `tb_tr_request_status_ibfk_1` FOREIGN KEY (`approver`) REFERENCES `tb_m_employee` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_request_status_ibfk_3` FOREIGN KEY (`status`) REFERENCES `tb_m_approval_status` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_tr_request_status_ibfk_4` FOREIGN KEY (`request`) REFERENCES `tb_tr_request` (`id`) ON UPDATE CASCADE;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `sch_delete_cart` ON SCHEDULE EVERY 1 MINUTE STARTS '2019-10-01 13:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL sp_schedule_delete()$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
