-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: banking_transaction_orm
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(300) DEFAULT NULL,
  `balance` decimal(12,0) DEFAULT '0',
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `createdBy` bigint DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `email` varchar(255) NOT NULL,
  `name` varchar(200) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `updatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedBy` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`),
  UNIQUE KEY `UK_m3iom37efaxd5eucmxjqqcbe9` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (10,'vfds',0,'2022-06-25 15:13:12',0,1,'rgf','acv','563457','2022-06-26 19:07:48',NULL),(11,'Rem elit quis nobis',15098450,'2022-06-25 15:14:30',NULL,0,'geruw@mailinator.com','Triet','0987057832','2022-06-30 09:11:42',NULL),(12,'Saepe recusandae Mo',999999891099,'2022-06-25 15:17:11',NULL,0,'tojahabux@mailinator.com','Hope Williams','01346583567','2022-06-28 10:19:51',NULL),(13,'Ullam voluptatem Es',0,'2022-06-25 15:17:29',NULL,0,'kyjonowam@mailinator.com','Daniel Gilbert','0546346756','2022-06-28 09:12:58',NULL),(14,'Proident harum quia',0,'2022-06-25 15:24:48',NULL,0,'syvypetu@mailinator.com','Neil Oneal','0107647567','2022-06-28 09:09:28',NULL),(29,'Occaecat earum aliqu',0,'2022-06-26 08:18:23',NULL,1,'pybilub@mailinator.com','Nicholas Benton','68','2022-06-26 08:18:23',NULL),(30,'Dolorem expedita par',0,'2022-06-27 08:41:46',NULL,1,'duwol@mailinator.com','Fio','64','2022-06-27 08:41:46',NULL),(31,'Magni possimus eos',0,'2022-06-27 13:37:14',NULL,0,'cezutatale@mailinator.com','Yuli Kaufman','0975877577','2022-06-27 13:37:14',NULL),(34,'Consequat Ut autem',0,'2022-06-28 09:35:12',NULL,0,'pufe@mailinator.com','Quemby Logan','0623562435','2022-06-28 09:35:12',NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-04 11:46:57
