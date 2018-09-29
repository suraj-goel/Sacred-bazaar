-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 29, 2018 at 01:58 PM
-- Server version: 5.7.23-0ubuntu0.16.04.1
-- PHP Version: 7.0.30-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Sacred_Bazaar`
--

-- --------------------------------------------------------

--
-- Table structure for table `AmountSpendUser`
--

CREATE TABLE `AmountSpendUser` (
  `UserID` varchar(255) NOT NULL,
  `AmountSpend` double(12,2) NOT NULL DEFAULT '0.00',
  `DiscountEndDate` datetime DEFAULT NULL,
  `BonusEndDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AmountSpendUser`
--

INSERT INTO `AmountSpendUser` (`UserID`, `AmountSpend`, `DiscountEndDate`, `BonusEndDate`) VALUES
('229b97d9-5714-3849-85f7-2a5f7b7d70aa', 27132.00, '2018-11-17 00:00:00', '2018-10-11 00:00:00'),
('admin', 10000.00, '2018-11-17 00:00:00', '2018-10-11 00:00:00'),
('df67c2cc-443b-353b-80ac-28dff2752030', 19164.00, NULL, '2018-10-11 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `Category` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`Category`) VALUES
('Cosmetics'),
('Electronics'),
('Fashion'),
('Food'),
('Sports'),
('Books'),
('Others');

-- --------------------------------------------------------

--
-- Table structure for table `offers`
--

CREATE TABLE `offers` (
  `offerID` varchar(255) NOT NULL,
  `retailerID` varchar(255) NOT NULL,
  `Type` varchar(255) NOT NULL DEFAULT 'Discount',
  `x` double(5,2) NOT NULL,
  `y` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `offers`
--

INSERT INTO `offers` (`offerID`, `retailerID`, `Type`, `x`, `y`) VALUES
('25273cd0-a82a-3676-9a4e-665486ac342b', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'X_PERCENT_DISCOUNT', 30.00, 0),
('2aeac3cf-1970-32d1-9598-2a217617140b', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'X_PERCENT_DISCOUNT', 0.00, 0),
('4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'X_PERCENT_DISCOUNT', 10.00, 0),
('b33a51dd-bbe7-3d91-873a-398936d24d1f', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'BUY_X_GET_Y', 5.00, 1),
('e795e6d3-ad44-3173-aebf-50a5bd47eec2', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'BUY_X_GET_Y', 10.00, 2),
('edc1e8a4-2a4f-3895-bcdc-471eab98aa84', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'X_PERCENT_DISCOUNT', 50.00, 0);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` varchar(255) NOT NULL,
  `RetailerID` varchar(255) NOT NULL,
  `ProductName` varchar(255) NOT NULL,
  `Price` varchar(255) NOT NULL,
  `ManufacturingDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Category` varchar(255) NOT NULL,
  `ExpiryDate` datetime DEFAULT NULL,
  `QuantityAvailable` int(11) UNSIGNED NOT NULL DEFAULT '0',
  `currentOffer` varchar(255) DEFAULT NULL,
  `currentOfferEnd` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `RetailerID`, `ProductName`, `Price`, `ManufacturingDate`, `Category`, `ExpiryDate`, `QuantityAvailable`, `currentOffer`, `currentOfferEnd`) VALUES
('0ceba93f-45cc-3c9d-882c-87f2619e870a', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Maggi', '12.0', '2018-09-03 00:00:00', 'Food', '2019-04-03 00:00:00', 187, 'b33a51dd-bbe7-3d91-873a-398936d24d1f', '2018-12-07 00:00:00'),
('6c304f99-252d-3b78-8a4a-7d72a4a4ccf8', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Lays', '20.0', '2018-09-10 00:00:00', 'Food', '2019-03-10 00:00:00', 16, '2aeac3cf-1970-32d1-9598-2a217617140b', '2019-02-27 00:00:00'),
('9161e70e-3dd6-3bae-83c0-824a73bcaed2', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Levis Jeans', '2000.0', '2018-09-25 00:00:00', 'Fashion', '2019-03-30 00:00:00', 1, 'e795e6d3-ad44-3173-aebf-50a5bd47eec2', '2018-09-29 00:00:00'),
('c93eb52c-f742-3b04-9d0c-602d9f7337c5', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Kurkure', '20.0', '2018-09-10 00:00:00', 'Food', '2019-03-10 00:00:00', 17, '2aeac3cf-1970-32d1-9598-2a217617140b', '2019-02-28 00:00:00'),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Adidas Tshirt', '1000.0', '2018-09-11 00:00:00', 'Fashion', '2019-04-27 00:00:00', 12, '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', '2018-10-31 00:00:00'),
('f991d1f3-5f36-3884-a3e3-17ca32cc9c14', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Iphone 7', '45000.0', '2018-09-24 00:00:00', 'Electronics', '2019-01-18 00:00:00', 20, '2aeac3cf-1970-32d1-9598-2a217617140b', '2019-02-23 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `ProductSold`
--

CREATE TABLE `ProductSold` (
  `ProductID` varchar(255) NOT NULL,
  `CustomerID` varchar(255) NOT NULL,
  `DateOfOrder` datetime NOT NULL,
  `OfferApplied` varchar(255) DEFAULT NULL,
  `ProductCost` double(10,2) NOT NULL,
  `OrderID` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ProductSold`
--

INSERT INTO `ProductSold` (`ProductID`, `CustomerID`, `DateOfOrder`, `OfferApplied`, `ProductCost`, `OrderID`, `quantity`) VALUES
('6c304f99-252d-3b78-8a4a-7d72a4a4ccf8', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '2aeac3cf-1970-32d1-9598-2a217617140b', 20.00, '00bc1a0d-0428-3b98-84cd-75dcbeccff8e', 1),
('0ceba93f-45cc-3c9d-882c-87f2619e870a', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-27 00:00:00', 'b33a51dd-bbe7-3d91-873a-398936d24d1f', 24.00, '050edfaa-7d6b-35d0-b41c-d4899ca711cb', 2),
('c93eb52c-f742-3b04-9d0c-602d9f7337c5', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '2aeac3cf-1970-32d1-9598-2a217617140b', 40.00, '32fa57bb-f194-3fd1-8d40-5c3ac8072b28', 2),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-22 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 1000.00, '37e42f07-2ce4-3c1e-a150-fd44cc49735c', 1),
('6c304f99-252d-3b78-8a4a-7d72a4a4ccf8', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-26 00:00:00', '2aeac3cf-1970-32d1-9598-2a217617140b', 20.00, '4bc876a2-8c1f-3415-8fcd-dd419f67cf94', 1),
('0ceba93f-45cc-3c9d-882c-87f2619e870a', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-22 00:00:00', 'b33a51dd-bbe7-3d91-873a-398936d24d1f', 72.00, '5839ca91-df7f-3829-8dda-bb8f493a7f33', 6),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 1000.00, '7423278b-f7b8-34e3-8da6-77bd4fa78eed', 1),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 1000.00, '764d6855-2b56-32c3-ab64-fb5ca0d6e144', 1),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-22 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 1000.00, '8badfe51-c996-346a-a89e-6ab0ec0e1145', 1),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-25 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 1000.00, 'a26078ea-5a30-3640-8313-a18a85def4f5', 1),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 1000.00, 'b6381f85-f090-34ee-9f83-802a9f6d0587', 1),
('9161e70e-3dd6-3bae-83c0-824a73bcaed2', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-27 00:00:00', 'e795e6d3-ad44-3173-aebf-50a5bd47eec2', 14000.00, 'c4a03e51-4a44-3108-8868-87373ade2632', 7),
('9161e70e-3dd6-3bae-83c0-824a73bcaed2', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-22 00:00:00', 'e795e6d3-ad44-3173-aebf-50a5bd47eec2', 22000.00, 'cb9d593a-98fd-30bd-973a-f289a1dcbfff', 11),
('6c304f99-252d-3b78-8a4a-7d72a4a4ccf8', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '2aeac3cf-1970-32d1-9598-2a217617140b', 40.00, 'd0756f75-219d-3498-a818-7788c3b11dc4', 2),
('9161e70e-3dd6-3bae-83c0-824a73bcaed2', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-22 00:00:00', 'e795e6d3-ad44-3173-aebf-50a5bd47eec2', 2000.00, 'd0f15f3a-4987-3b78-aa16-16bd8076358d', 1),
('c93eb52c-f742-3b04-9d0c-602d9f7337c5', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '2aeac3cf-1970-32d1-9598-2a217617140b', 20.00, 'f77cb65d-8a4c-38ba-9758-93b10490513b', 1),
('0ceba93f-45cc-3c9d-882c-87f2619e870a', '229b97d9-5714-3849-85f7-2a5f7b7d70aa', '2018-09-22 00:00:00', 'b33a51dd-bbe7-3d91-873a-398936d24d1f', 60.00, 'fa4dd02f-9c58-36c2-aebc-3e274f79dbfd', 5),
('e879133f-9d76-38b3-8296-fe3e35bcd8f7', 'df67c2cc-443b-353b-80ac-28dff2752030', '2018-09-22 00:00:00', '4b95062b-cb68-3aaf-93e4-e60b13a7fd4b', 2000.00, 'fb601ed1-2a09-36b2-8cfc-378e18ed5cc3', 2);

-- --------------------------------------------------------

--
-- Table structure for table `retailers`
--

CREATE TABLE `retailers` (
  `UserID` varchar(255) NOT NULL,
  `RetailerID` varchar(255) NOT NULL,
  `BusinessName` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `retailers`
--

INSERT INTO `retailers` (`UserID`, `RetailerID`, `BusinessName`, `Address`) VALUES
('df67c2cc-443b-353b-80ac-28dff2752030', 'eb00b520-0c79-3fd9-aa0c-c4f2cae274da', 'Suraj\'s Shop', 'MNNIT');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `First_Name` varchar(255) NOT NULL,
  `Last_Name` varchar(255) NOT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  `Password` varchar(255) NOT NULL,
  `Retailer_Status` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Email`, `First_Name`, `Last_Name`, `Phone`, `Password`, `Retailer_Status`) VALUES
('229b97d9-5714-3849-85f7-2a5f7b7d70aa', 'shrey@gmail.com', 'shey', 'pandey', '9052000000', '173168172165179', 0),
('df67c2cc-443b-353b-80ac-28dff2752030', 'suraj@gmail.com', 'suraj', 'goel', '8901435825', '17317517216116a', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `AmountSpendUser`
--
ALTER TABLE `AmountSpendUser`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`offerID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `ProductSold`
--
ALTER TABLE `ProductSold`
  ADD PRIMARY KEY (`OrderID`);

--
-- Indexes for table `retailers`
--
ALTER TABLE `retailers`
  ADD PRIMARY KEY (`RetailerID`),
  ADD UNIQUE KEY `UserID` (`UserID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Email` (`Email`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
