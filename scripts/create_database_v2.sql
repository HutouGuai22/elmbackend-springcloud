-- 创建数据库
CREATE DATABASE IF NOT EXISTS eleme CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE eleme;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `userSex` int DEFAULT '1',
  `userImg` varchar(100) DEFAULT 'yhtx01.png',
  `delTag` int DEFAULT '1',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 商家表
CREATE TABLE IF NOT EXISTS `business` (
  `businessId` int NOT NULL AUTO_INCREMENT,
  `businessName` varchar(100) NOT NULL,
  `businessAddress` varchar(200) DEFAULT NULL,
  `businessExplain` varchar(200) DEFAULT NULL,
  `businessImg` varchar(100) DEFAULT NULL,
  `orderTypeId` int DEFAULT NULL,
  `starPrice` decimal(10,2) DEFAULT NULL,
  `deliveryPrice` decimal(10,2) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`businessId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 食品表
CREATE TABLE IF NOT EXISTS `food` (
  `foodId` int NOT NULL AUTO_INCREMENT,
  `foodName` varchar(100) NOT NULL,
  `foodExplain` varchar(200) DEFAULT NULL,
  `foodImg` varchar(100) DEFAULT NULL,
  `foodPrice` decimal(10,2) NOT NULL,
  `businessId` int NOT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`foodId`),
  KEY `businessId` (`businessId`),
  FOREIGN KEY (`businessId`) REFERENCES `business` (`businessId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单表
CREATE TABLE IF NOT EXISTS `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `businessId` int NOT NULL,
  `orderDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `orderTotal` decimal(10,2) NOT NULL,
  `daId` int NOT NULL,
  `orderState` int DEFAULT '0',
  PRIMARY KEY (`orderId`),
  KEY `userId` (`userId`),
  KEY `businessId` (`businessId`),
  KEY `daId` (`daId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单详情表
CREATE TABLE IF NOT EXISTS `orderdetailet` (
  `odId` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `foodId` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`odId`),
  KEY `orderId` (`orderId`),
  KEY `foodId` (`foodId`),
  FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`) ON DELETE CASCADE,
  FOREIGN KEY (`foodId`) REFERENCES `food` (`foodId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 配送地址表
CREATE TABLE IF NOT EXISTS `deliveryaddress` (
  `daId` int NOT NULL AUTO_INCREMENT,
  `contactName` varchar(50) NOT NULL,
  `contactSex` int DEFAULT '1',
  `contactTel` varchar(20) NOT NULL,
  `address` varchar(200) NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`daId`),
  KEY `userId` (`userId`),
  FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 购物车表
CREATE TABLE IF NOT EXISTS `cart` (
  `cartId` int NOT NULL AUTO_INCREMENT,
  `foodId` int NOT NULL,
  `businessId` int NOT NULL,
  `userId` int NOT NULL,
  `quantity` int DEFAULT '1',
  PRIMARY KEY (`cartId`),
  KEY `userId` (`userId`),
  KEY `foodId` (`foodId`),
  KEY `businessId` (`businessId`),
  FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE,
  FOREIGN KEY (`foodId`) REFERENCES `food` (`foodId`) ON DELETE CASCADE,
  FOREIGN KEY (`businessId`) REFERENCES `business` (`businessId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建索引以提高查询性能
CREATE INDEX idx_user_username ON `user` (`userName`);
CREATE INDEX idx_business_ordertype ON `business` (`orderTypeId`);
CREATE INDEX idx_food_business ON `food` (`businessId`);
CREATE INDEX idx_orders_user_state ON `orders` (`userId`, `orderState`);
CREATE INDEX idx_orders_date ON `orders` (`orderDate`);
CREATE INDEX idx_cart_user_business ON `cart` (`userId`, `businessId`);
