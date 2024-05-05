-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: banco-hora-tupi
-- ------------------------------------------------------
-- Server version	5.7.25

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
-- Table structure for table `jornada`
--

DROP TABLE IF EXISTS `jornada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jornada` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dat_jornada` date DEFAULT NULL,
  `end_almoco` time(6) DEFAULT NULL,
  `end_jornada` time(6) DEFAULT NULL,
  `porcentagem` int(11) NOT NULL,
  `start_almoco` time(6) DEFAULT NULL,
  `start_jornada` time(6) DEFAULT NULL,
  `periodo_trabalhado` decimal(21,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jornada`
--

LOCK TABLES `jornada` WRITE;
/*!40000 ALTER TABLE `jornada` DISABLE KEYS */;
INSERT INTO `jornada` VALUES (1,'2024-03-26','13:01:00.000000','18:40:00.000000',70,'11:59:00.000000','07:12:00.000000',NULL),(2,'2024-03-27','12:57:00.000000','17:33:00.000000',70,'11:56:00.000000','07:16:00.000000',NULL),(3,'2024-03-28','12:53:00.000000','17:33:00.000000',70,'11:52:00.000000','06:57:00.000000',NULL),(4,'2024-04-01','12:56:00.000000','17:36:00.000000',70,'11:51:00.000000','06:51:00.000000',NULL),(5,'2024-04-02','12:47:00.000000','17:51:00.000000',70,'11:46:00.000000','07:12:00.000000',NULL),(6,'2024-04-03','13:11:00.000000','17:55:00.000000',70,'12:10:00.000000','07:15:00.000000',NULL),(7,'2024-04-04','13:46:00.000000','17:09:00.000000',70,'12:46:00.000000','07:32:00.000000',NULL),(8,'2024-04-05','13:12:00.000000','17:45:00.000000',70,'12:11:00.000000','07:00:00.000000',NULL),(9,'2024-04-08','10:01:00.000000','12:00:00.000000',70,'10:01:00.000000','07:14:00.000000',NULL),(10,'2024-04-09','13:07:00.000000','17:11:00.000000',70,'12:07:00.000000','07:02:00.000000',NULL),(11,'2024-04-10','13:07:00.000000','18:50:00.000000',70,'12:07:00.000000','06:42:00.000000',NULL),(12,'2024-04-11','13:01:00.000000','17:21:00.000000',70,'12:01:00.000000','07:09:00.000000',NULL),(13,'2024-04-12','12:31:00.000000','18:27:00.000000',70,'11:28:00.000000','06:13:00.000000',NULL),(14,'2024-04-13','10:01:00.000000','13:03:00.000000',110,'10:01:00.000000','06:13:00.000000',NULL),(15,'2024-04-15','12:51:00.000000','17:23:00.000000',70,'11:49:00.000000','06:57:00.000000',NULL),(16,'2024-04-16','12:59:00.000000','17:46:00.000000',70,'11:59:00.000000','06:58:00.000000',NULL),(17,'2024-04-17','12:44:00.000000','19:05:00.000000',70,'11:44:00.000000','07:00:00.000000',NULL),(18,'2024-04-18','13:20:00.000000','17:58:00.000000',70,'12:17:00.000000','07:06:00.000000',NULL),(19,'2024-03-21','12:00:00.000000','17:00:00.000000',70,'11:00:00.000000','07:00:00.000000',NULL),(22,'2024-04-22','12:56:00.000000','18:22:00.000000',70,'11:50:00.000000','06:47:00.000000',NULL),(39,'2024-04-23','12:38:00.000000','18:01:00.000000',70,'11:38:00.000000','07:12:00.000000',NULL),(40,'2024-04-24','13:06:00.000000','18:13:00.000000',70,'11:55:00.000000','07:08:00.000000',NULL),(41,'2024-04-25','12:53:00.000000','18:00:00.000000',70,'11:53:00.000000','07:10:00.000000',NULL),(42,'2024-04-29','13:11:00.000000','19:26:00.000000',70,'12:11:00.000000','06:50:00.000000',NULL),(45,'2024-04-26','12:17:00.000000','16:55:00.000000',70,'11:17:00.000000','07:07:00.000000',NULL),(46,'2024-05-30','12:56:00.000000','17:05:00.000000',70,'11:50:00.000000','06:57:00.000000',NULL),(47,'2024-05-02','13:11:00.000000','17:31:00.000000',70,'12:11:00.000000','06:46:00.000000',NULL),(48,'2024-05-30','12:55:00.000000','17:05:00.000000',70,'11:50:00.000000','06:57:00.000000',NULL),(49,'2024-04-30','12:55:00.000000','17:05:00.000000',70,'11:50:00.000000','06:57:00.000000',NULL),(50,'2024-05-03','13:00:00.000000','17:13:00.000000',70,'12:00:00.000000','07:03:00.000000',NULL),(51,'2024-05-04','13:30:00.000000','14:54:00.000000',110,'12:26:00.000000','05:58:00.000000',NULL);
/*!40000 ALTER TABLE `jornada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jornada_new`
--

DROP TABLE IF EXISTS `jornada_new`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jornada_new` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dat_jornada` date DEFAULT NULL,
  `start_jornada` datetime(6) DEFAULT NULL,
  `end_jornada` datetime(6) DEFAULT NULL,
  `start_almoco` datetime(6) DEFAULT NULL,
  `end_almoco` datetime(6) DEFAULT NULL,
  `porcentagem` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jornada_new`
--

LOCK TABLES `jornada_new` WRITE;
/*!40000 ALTER TABLE `jornada_new` DISABLE KEYS */;
/*!40000 ALTER TABLE `jornada_new` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-05 17:49:57
