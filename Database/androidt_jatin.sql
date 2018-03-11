-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 25, 2017 at 06:42 PM
-- Server version: 5.6.36-cll-lve
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `androidt_jatin`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_details`
--

CREATE TABLE `account_details` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_details`
--

INSERT INTO `account_details` (`id`, `name`, `pass`, `email`, `phone`, `address`) VALUES
(1, 'asdad', '1234', 'dsas@gmail.com', '123456', 'asdasa'),
(3, 'Jatin Arora', '12345', 'jatinarora@gmail.com', '123456789', 'asdavgqeq'),
(4, 'Jatin Arora', '12345', 'jatinarora@gmail.com', '123456789', 'asdavgqeq'),
(5, 'jatin', '123456', 'arrao@gmail.com', '1234567909', 'sadasvwgdfdbdb'),
(6, 'jatin arora', '12345678', 'arora@gmail.com', '123456', 'gfhnsfvc'),
(7, 'jalnl', '12345678', 'nsdal@gmail.com', '12345678', 'gbn hfxcfdss'),
(8, 'dsaasbsd', '21354687', 'dsadaas@gma.com', '121264', 'asab db fs'),
(9, 'jatin', '123456', 'jatin@gmail.com', '12345687', 'sadvwwvw'),
(10, 'jat', '123456', 'jat@gmail.com', '2665552', 'asvbene'),
(11, 'abcd', '123456', 'ab@cd.com', '14568', 'cfbwbw'),
(12, 'abcd', '123456', 'ab@cd.com', '14568', 'cfbwbw'),
(17, 'dhbff', '1234556', 'fhgdddc@gik.com', '989507', 'jdjsavG'),
(18, 'dhbff', '1234556', 'fhgdddc@gik.com', '989507', 'jdjsavG'),
(19, 'jt', '123456', 'jt@gmail.com', '5684131841', 'sada vawvavav');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `category` varchar(500) NOT NULL,
  `image_url` varchar(1000) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `category`, `image_url`) VALUES
(1, 'Electronics', 'http://www.pionero.it/wp-content/uploads/2014/02/NewTech_0-600x375.jpg'),
(2, 'Health Care', 'http://www.thegatecoach.com/images/banner%201.jpg'),
(3, 'Clothing', 'http://www.thegatecoach.com/images/banner%201.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `category` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `description` varchar(5000) NOT NULL,
  `image` varchar(1000) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `category`, `price`, `description`, `image`) VALUES
(1, 'Levis Jeans', 'Clothing', '750', 'This is a test description', 'http://i.ebayimg.com/images/i/360756697189-0-1/s-l1000.jpg'),
(2, 'Puma T-Shirt', 'Clothing', '450', 'Puma T-Shirt for mens Category', 'http://images.champssports.com/pi/59401203/zoom/puma-sneaker-tongue-metallic-t-shirt-mens'),
(3, 'Nike T-Shirt', 'Clothing', '299', 'Nike sports T-Shirt', 'https://s-media-cache-ak0.pinimg.com/736x/1f/73/be/1f73be2f7909d9e1256213e2fc065426--nike-t-shirts-black-and-white-tops.jpg'),
(4, 'Nike Digital Print', 'Clothing', '399', 'Nike Digital Print T-Shirt For Mens', 'http://www.bluemaize.net/im/shirts/nike-t-shirts-1.jpg'),
(5, 'Adidas T-Shirt', 'Mens', '499', 'Adidas T-Shirt', 'https://s-media-cache-ak0.pinimg.com/736x/cc/5d/10/cc5d1082d35b5bb1fda6bc2a45190e02--original-t-shirts-crop-t-shirt.jpg'),
(6, 'Adidas Top', 'Womens', '499', 'Adidas Top for Womens', 'https://s-media-cache-ak0.pinimg.com/736x/1e/0c/4e/1e0c4edeab2d050acd2129cd5b3b213f--black-and-white-shirt-white-t-shirts.jpg'),
(7, 'Adidas Black Shell Tile', 'Womens', '699', 'Adidas Black Shell Tile For Womens', 'http://cdnp.jimmyjazz.com/AZ4939/AZ4939_black_adidas_shell_tile_boyfriend_adidas_logo_tee_lp1.jpg'),
(8, 'Adidas White Shell Tile', 'Womens', '799', 'Adidas White Shell Tile For Womens', 'http://cdnp.jimmyjazz.com/AJ7242-100/AJ7242-100_white_1000_1.jpg'),
(9, 'Adidas Track Suite', 'Womens', '1099', 'Adidas Track Suite For Womens', 'http://nord.imgix.net/Zoom/10/_12795790.jpg?fit=fill&fm=jpg&dpr=2&h=368&w=240&q=30'),
(10, 'Adidas Sport Set', 'Womens', '799', 'Adidas Sport Set For Womens', 'http://cdn2.next.co.uk/common/Items/Default/Default/Publications/G50/shotview-315x472/2686/449-796s.jpg'),
(11, 'Kids Cloths', 'Kids', '699', 'Kids Cloths', 'http://cdn.skim.gs/image/upload/v1456338162/msi/one-stop-shopping-for-fabulous-kids-clothes-lands-end_fvhnyp.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_details`
--
ALTER TABLE `account_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_details`
--
ALTER TABLE `account_details`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
