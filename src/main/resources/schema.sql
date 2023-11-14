-- MySQL dump 10.13  Distrib 8.1.0, for macos13.3 (arm64)
--
-- Host: 127.0.0.1    Database: jungko
-- ------------------------------------------------------
-- Server version	11.1.2-MariaDB-1:11.1.2+maria~ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `member`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `member` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `deleted_at` datetime(6) DEFAULT NULL,
    `email` varchar(255) NOT NULL,
    `nickname` varchar(255) NOT NULL,
    `notification_agreement` bit(1) NOT NULL,
    `oauth_id` varchar(255) NOT NULL,
    `oauth_type` varchar(255) NOT NULL,
    `profile_image_url` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_mbmcqelty0fbrvxp1q58dn57t` (`email`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `sido_area` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `adm_code` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sido_area`
--

--
-- Table structure for table `sigg_area`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `sigg_area` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `adm_code` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `sido_area_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKhdsvsno27xwa8912kl9tcc4vw` (`sido_area_id`),
    CONSTRAINT `FKhdsvsno27xwa8912kl9tcc4vw` FOREIGN KEY (`sido_area_id`) REFERENCES `sido_area` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sigg_area`
--

--
-- Table structure for table `emd_area`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `emd_area` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `adm_code` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `sigg_area_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK6ypf3qjgeiqlfqxxq6x06m9xs` (`sigg_area_id`),
    CONSTRAINT `FK6ypf3qjgeiqlfqxxq6x06m9xs` FOREIGN KEY (`sigg_area_id`) REFERENCES `sigg_area` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emd_area`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `product_category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `image_url` varchar(255) DEFAULT NULL,
    `level` int(11) NOT NULL,
    `name` varchar(255) NOT NULL,
    `parent_category_id` bigint(20) DEFAULT NULL,
    `product_category_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_pov1gqk9ji9ob40dr8pn2uk2t` (`product_category_id`),
    KEY `FKh9b72s73v7cga3g5pff7xryvi` (`parent_category_id`),
    CONSTRAINT `FKdskmbxpa7x67o8dbwt1wr54ye` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`),
    CONSTRAINT `FKh9b72s73v7cga3g5pff7xryvi` FOREIGN KEY (`parent_category_id`) REFERENCES `product_category` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

--
-- Table structure for table `product`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `product` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `availability` varchar(255) NOT NULL,
    `content` varchar(4095) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `image_url` varchar(255) DEFAULT NULL,
    `market_name` varchar(255) NOT NULL,
    `market_product_id` varchar(255) NOT NULL,
    `price` bigint(20) DEFAULT NULL,
    `title` varchar(255) NOT NULL,
    `uploaded_at` datetime(6) NOT NULL,
    `area_id` bigint(20) DEFAULT NULL,
    `product_category_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_noc660u2fi6jvk0e6gf0c96km` (`product_category_id`),
    UNIQUE KEY `UK_7e4tm57p8erata3vjpn48ki9y` (`area_id`),
    CONSTRAINT `FK9kr30vlv5kjfc8mr8jwss20p3` FOREIGN KEY (`area_id`) REFERENCES `emd_area` (`id`),
    CONSTRAINT `FKcwclrqu392y86y0pmyrsi649r` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `card` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `keyword` varchar(255) DEFAULT NULL,
    `max_price` int(11) DEFAULT NULL,
    `min_price` int(11) DEFAULT NULL,
    `scope` varchar(255) NOT NULL,
    `title` varchar(255) DEFAULT NULL,
    `area_id` bigint(20) DEFAULT NULL,
    `member_id` bigint(20) DEFAULT NULL,
    `product_category_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_qxn4orpovj38xv1wc6persyry` (`area_id`),
    UNIQUE KEY `UK_8elglmrweo8ck1m4ooevf17mq` (`product_category_id`),
    KEY `FKbf204t9qecurpbyoqlmpcy5t4` (`member_id`),
    CONSTRAINT `FK71au4sipsxodfv3fon6cmo0hw` FOREIGN KEY (`area_id`) REFERENCES `emd_area` (`id`) ON DELETE CASCADE,
    CONSTRAINT `FKbf204t9qecurpbyoqlmpcy5t4` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE,
    CONSTRAINT `FKtqrm1afkkjsxg8s11wj08m412` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

--
-- Table structure for table `interested_card`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `interested_card` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `card_id` bigint(20) DEFAULT NULL,
    `member_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK2165wushri90rxq0ykytsebqt` (`card_id`),
    KEY `FKhh1fner3v64shm5i1tuqn0bxk` (`member_id`),
    CONSTRAINT `FK2165wushri90rxq0ykytsebqt` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE CASCADE,
    CONSTRAINT `FKhh1fner3v64shm5i1tuqn0bxk` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interested_card`
--

--
-- Table structure for table `interested_keyword`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `interested_keyword` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `keyword` varchar(255) NOT NULL,
    `member_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1vx8b13wc1o8lbo1hmoctbyld` (`member_id`),
    CONSTRAINT `FK1vx8b13wc1o8lbo1hmoctbyld` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interested_keyword`
--
--

--
-- Table structure for table `notification`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `notification` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `content` varchar(255) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `is_read` bit(1) NOT NULL,
    `notice_type` varchar(255) NOT NULL,
    `title` varchar(255) NOT NULL,
    `member_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1xep8o2ge7if6diclyyx53v4q` (`member_id`),
    CONSTRAINT `FK1xep8o2ge7if6diclyyx53v4q` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

--
-- Table structure for table `product_keyword`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `product_keyword` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `keyword` varchar(255) NOT NULL,
    `product_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK7t104v2rcj4fxv6bf714x36f5` (`product_id`),
    CONSTRAINT `FK7t104v2rcj4fxv6bf714x36f5` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_keyword`
--

--
-- Table structure for table `seller`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `seller` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `credibility` int(11) NOT NULL,
    `number_of_transactions` int(11) NOT NULL,
    `seller_nickname` varchar(255) NOT NULL,
    `product_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKpsmdbjumfqrncebiwxknjvda7` (`product_id`),
    CONSTRAINT `FKpsmdbjumfqrncebiwxknjvda7` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-14 12:55:47
