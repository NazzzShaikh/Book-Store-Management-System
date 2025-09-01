-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2024 at 07:57 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bms`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateQuantity` (IN `in_quntity` INT, IN `in_bookid` INT)   BEGIN
UPDATE books set stock_quantity=stock_quantity - in_quntity where book_id=in_bookid;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `username`, `password`, `email`, `phone_number`) VALUES
(1, 'admin1', 'pass123', 'admin1@example.com', '123-456-7890'),
(2, 'admin2', 'pass456', 'admin2@example.com', '098-765-4321'),
(3, 'admin3', 'mypassword789', 'admin3@example.com', '555-555-5555');

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `book_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `isbn` varchar(13) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `published_date` date DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `rating` float DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`book_id`, `title`, `author`, `genre`, `isbn`, `price`, `stock_quantity`, `published_date`, `publisher`, `rating`) VALUES
(1, 'abc', 'naz', 'fiction', '9780061120084', 78.90, 63, '1960-07-11', 'J.B. Lippincott & Co.', 4.9),
(3, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', '9780743273565', 10.99, 36, '1925-04-10', 'Charles Scribner\'s Sons', 4.7),
(4, 'Pride and Prejudice', 'Jane Austen', 'Romance', '9781503290563', 12.99, 19, '1813-01-28', 'T. Egerton', 4.5),
(5, 'The Catcher in the Rye', 'J.D. Salinger', 'Fiction', '9780316769488', 13.99, 17, '1951-07-16', 'Little, Brown and Company', 4.3),
(7, '1', '2', '3', '1233445', 4.00, 5, '2003-05-29', 'Bloomsbury', 4.9),
(8, 'The Great Escape', 'John Doe', 'Fiction', '978-123456789', 19.99, 50, '2022-05-15', 'Penguin Random House', 4.5),
(12, 'The River Between Us', 'Richard Adams', 'Fiction', '0553263573', 11.49, 20, '2016-09-15', 'Small Press Books', 3.8),
(13, 'The Mindful Explorer', 'Lucy Thompson', 'Non-Fiction', '0987654321', 14.99, 15, '2018-11-10', 'Insightful Press', 4.1),
(14, 'Echoes of the Past', 'Nina Harper', 'Novel', '1234543219', 12.75, 30, '2019-02-18', 'Windy City Publishing', 3.9),
(15, 'Wings of the Firebird', 'Gareth Williams', 'Fantasy', '9876543210', 17.99, 25, '2020-08-27', 'Starbound Books', 4.2),
(16, 'The Shattered Lens', 'Fiona Drake', 'Mystery', '0543219876', 18.50, 18, '2021-05-14', 'Misty Valley Press', 3.7),
(17, 'Portrait of a Dreamer', 'Clara Young', 'Biography', '6543210987', 21.99, 12, '2017-12-22', 'Heritage Publishers', 4),
(18, 'Tales of the Silk Road', 'Matthew Scott', 'History', '210987654', 23.45, 28, '2015-06-09', 'Eastern Horizons Press', 3.8),
(19, 'The Hidden Garden', 'Isabel Turner', 'Romance', '109876543', 13.25, 31, '2022-03-30', 'Rose Petal Press', 4.1);

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `cart_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `book_id` int(10) NOT NULL,
  `bookname` varchar(50) NOT NULL,
  `quantity` int(10) NOT NULL,
  `price` double NOT NULL,
  `added_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(50) DEFAULT 'Processing'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `total_amount`, `order_date`, `status`) VALUES
(1, 1, 31.98, '2024-08-30 05:11:51', 'paid'),
(2, 1, 63.96, '2024-08-30 10:46:19', 'paid'),
(3, 1, 25.98, '2024-08-31 02:05:46', 'paid'),
(4, 1, 25.98, '2024-08-31 02:10:03', 'paid'),
(5, 1, 25.98, '2024-08-31 02:12:45', 'paid'),
(6, 1, 41.97, '2024-08-31 05:14:36', 'paid'),
(7, 1, 53.00, '2024-08-31 05:14:36', 'paid'),
(8, 1, 157.80, '2024-08-31 05:41:00', 'paid'),
(9, 1, 43.96, '2024-08-31 05:44:07', 'paid');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_item_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `payment_method` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `user_id`, `username`, `amount`, `payment_date`, `payment_method`, `status`) VALUES
(1, 1, 'user1', 25.98, '2024-08-31 02:10:03', 'Cash on Delivery', 'Paid'),
(2, 1, 'user1', 25.98, '2024-08-31 02:12:45', 'Cash on Delivery', 'Paid'),
(3, 1, 'user1', 41.97, '2024-08-31 05:14:36', 'Cash on Delivery', 'Paid'),
(4, 1, 'user1', 53.00, '2024-08-31 05:14:36', 'Cash on Delivery', 'Paid'),
(5, 1, 'user1', 157.80, '2024-08-31 05:41:01', 'By UPI', 'Paid'),
(6, 1, 'user1', 43.96, '2024-08-31 05:44:07', 'By UPI', 'Paid');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userFullname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address` text DEFAULT NULL,
  `vip_pass` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `userFullname`, `email`, `phone_number`, `address`, `vip_pass`) VALUES
(1, 'user1', 'pass1', 'John Doe', '', '111-111-1111', '123 Main St, Anytown, USA', 1),
(2, 'user2', 'pass2', 'Jane Smith', '', '222-222-2222', '456 Oak Ave, Anytown, USA', 0),
(3, 'user3', 'pass3', 'Alice Johnson', '', '333-333-3333', '789 Pine Rd, Anytown, USA', 0),
(4, 'user4', 'pass4', 'Bob Brown', '', '444-444-4444', '321 Cedar Ln, Anytown, USA', 0),
(5, 'user5', 'pass5', 'Eve White', '', '555-555-5555', '654 Maple Dr, Anytown, USA', 0),
(6, 'nezuko', 'zenistu', ' No nmae', '', '2134', 'xsaf', 0),
(7, '99999', '000', '555', '', 'lllll', 'k', 0),
(8, 'nazo', '123@abc!', 'Naz', '', '1234567890', 'dvjspovjqa', 0),
(9, 'aaa', '123!123!', 'abcd', '', '1234567890', 'effsbsfng', 0),
(11, 'nazzz', '123!123!', 'Naznin Shaikh', 'naz@gmail.com', '1234567890', 'ahemdabad ', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`book_id`),
  ADD UNIQUE KEY `isbn` (`isbn`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `order_item_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
