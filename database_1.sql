CREATE DATABASE  IF NOT EXISTS `skable2017` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `skable2017`;
-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 52.66.132.178    Database: skable2017
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acntmst`
--

DROP TABLE IF EXISTS `acntmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acntmst` (
  `AC_CD` varchar(7) NOT NULL,
  `FNAME` varchar(100) NOT NULL,
  `MNAME` varchar(50) NOT NULL,
  `LNAME` varchar(25) NOT NULL,
  `GRP_CD` varchar(7) NOT NULL,
  `CONTACT_PRSN` varchar(255) NOT NULL,
  `CST` varchar(50) NOT NULL,
  `PAN` varchar(50) NOT NULL,
  `REF_BY` varchar(255) NOT NULL,
  `EDIT_NO` int(11) NOT NULL DEFAULT '0',
  `USER_ID` int(11) NOT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `AC_ALIAS` varchar(5) DEFAULT NULL,
  `TIN` varchar(20) DEFAULT NULL,
  `CARD_NO` varchar(16) DEFAULT NULL,
  `OPB_AMT` decimal(10,2) DEFAULT '0.00',
  `OPB_EFF` decimal(1,0) DEFAULT '0',
  `REF_AC_CD` varchar(7) DEFAULT NULL,
  `gst_no` varchar(50) DEFAULT '',
  PRIMARY KEY (`AC_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acntmst`
--

LOCK TABLES `acntmst` WRITE;
/*!40000 ALTER TABLE `acntmst` DISABLE KEYS */;
INSERT INTO `acntmst` VALUES ('A7G0001','CASH','','','G000012','','','','',0,1,'2017-07-05 12:51:18','J0010','','',0.00,0,NULL,''),('A7G0002','KASAR AC CD','','','G000005','','','','',0,1,'2017-07-05 15:27:38','J0011','','',0.00,0,NULL,''),('A7G0003','PURCHASE AC','','','G000015','','','','',0,1,'2017-07-05 15:27:57','J0012','','',0.00,0,NULL,''),('A7G0004','SALES','','','G000016','','','','',0,1,'2017-07-05 15:28:09','J0013','','',0.00,0,NULL,''),('A7G0005','PURCHASE RETURN AC','','','G000018','','','','',0,1,'2017-07-05 15:28:25','J0014','','',0.00,0,NULL,''),('A7G0006','SALES RETURN ASC','','','G000017','','','','',0,1,'2017-07-05 15:28:40','J0015','','',0.00,0,NULL,''),('A7G0007','BUY BACK AC','','','G000015','','','','',0,1,'2017-07-05 15:28:57','J0016','','',0.00,0,NULL,''),('A7G0008','URD PURCHASE','','','G000015','','','','',0,1,'2017-07-05 15:29:14','J0017','','',0.00,0,NULL,''),('A7G0009','DISC AC','','','G000005','','','','',0,1,'2017-07-05 15:29:27','J0018','','',0.00,0,NULL,''),('T17G001','SGST 12','','','G000007','','','','',0,1,'2017-07-05 12:50:01','J0001','','',0.00,0,NULL,''),('T17G002','CGST 12','','','G000007','','','','',0,1,'2017-07-05 12:50:01','J0002','','',0.00,0,NULL,''),('T17G003','IGST 12','','','G000007','','','','',0,1,'2017-07-05 12:50:01','J0003','','',0.00,0,NULL,''),('T17G004','SGST 18','','','G000007','','','','',0,1,'2017-07-05 12:50:06','J0004','','',0.00,0,NULL,''),('T17G005','CGST 18','','','G000007','','','','',0,1,'2017-07-05 12:50:06','J0005','','',0.00,0,NULL,''),('T17G006','IGST 18','','','G000007','','','','',0,1,'2017-07-05 12:50:06','J0006','','',0.00,0,NULL,''),('T17G007','SGST 28','','','G000007','','','','',0,1,'2017-07-05 12:50:11','J0007','','',0.00,0,NULL,''),('T17G008','CGST 28','','','G000007','','','','',0,1,'2017-07-05 12:50:11','J0008','','',0.00,0,NULL,''),('T17G009','IGST 28','','','G000007','','','','',0,1,'2017-07-05 12:50:11','J0009','','',0.00,0,NULL,'');
/*!40000 ALTER TABLE `acntmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adbkmst`
--

DROP TABLE IF EXISTS `adbkmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adbkmst` (
  `AC_CD` varchar(7) NOT NULL,
  `ADD1` varchar(255) NOT NULL,
  `ADD2` varchar(255) NOT NULL,
  `ADD3` varchar(255) NOT NULL,
  `AREA_CD` int(11) NOT NULL,
  `CITY_CD` int(11) NOT NULL,
  `sr_no` decimal(2,0) NOT NULL,
  PRIMARY KEY (`AC_CD`,`sr_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adbkmst`
--

LOCK TABLES `adbkmst` WRITE;
/*!40000 ALTER TABLE `adbkmst` DISABLE KEYS */;
INSERT INTO `adbkmst` VALUES ('A7G0001','','','',0,0,1),('A7G0002','','','',0,0,1),('A7G0003','','','',0,0,1),('A7G0004','','','',0,0,1),('A7G0005','','','',0,0,1),('A7G0006','','','',0,0,1),('A7G0007','','','',0,0,1),('A7G0008','','','',0,0,1),('A7G0009','','','',0,0,1),('T17G001','','','',0,0,1),('T17G002','','','',0,0,1),('T17G003','','','',0,0,1),('T17G004','','','',0,0,1),('T17G005','','','',0,0,1),('T17G006','','','',0,0,1),('T17G007','','','',0,0,1),('T17G008','','','',0,0,1),('T17G009','','','',0,0,1);
/*!40000 ALTER TABLE `adbkmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billadjst`
--

DROP TABLE IF EXISTS `billadjst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billadjst` (
  `doc_ref_no` varchar(7) NOT NULL,
  `DR_DOC_REF_NO` varchar(7) DEFAULT NULL,
  `CR_DOC_REF_NO` varchar(7) DEFAULT NULL,
  `DR_DOC_CD` varchar(5) DEFAULT NULL,
  `CR_DOC_CD` varchar(7) DEFAULT NULL,
  `DR_INV_NO` int(11) DEFAULT NULL,
  `CR_INV_NO` int(11) DEFAULT NULL,
  `AMT` decimal(10,2) DEFAULT '0.00',
  `DR_SR_NO` decimal(2,0) DEFAULT '1',
  `CR_SR_NO` decimal(2,0) DEFAULT '1',
  `ac_cd` varchar(7) DEFAULT NULL,
  `is_type` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`doc_ref_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billadjst`
--

LOCK TABLES `billadjst` WRITE;
/*!40000 ALTER TABLE `billadjst` DISABLE KEYS */;
/*!40000 ALTER TABLE `billadjst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bprdt`
--

DROP TABLE IF EXISTS `bprdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bprdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `DOC_REF_NO` varchar(7) DEFAULT NULL,
  `BAL` decimal(10,2) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bprdt`
--

LOCK TABLES `bprdt` WRITE;
/*!40000 ALTER TABLE `bprdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `bprdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bprhd`
--

DROP TABLE IF EXISTS `bprhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bprhd` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `TOT_BAL` decimal(10,2) DEFAULT '0.00',
  `AC_CD` varchar(7) DEFAULT NULL,
  `BANK_CD` varchar(7) DEFAULT NULL,
  `CHEQUE_NO` varchar(255) DEFAULT NULL,
  `CHEQUE_DATE` date DEFAULT NULL,
  `CTYPE` decimal(1,0) DEFAULT NULL,
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `branch_cd` decimal(2,0) DEFAULT '0',
  `OPP_BANK_NAME` varchar(255) DEFAULT NULL,
  `rec_date` date DEFAULT NULL,
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bprhd`
--

LOCK TABLES `bprhd` WRITE;
/*!40000 ALTER TABLE `bprhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `bprhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brandmst`
--

DROP TABLE IF EXISTS `brandmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brandmst` (
  `BRAND_CD` varchar(7) NOT NULL,
  `BRAND_NAME` varchar(50) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`BRAND_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brandmst`
--

LOCK TABLES `brandmst` WRITE;
/*!40000 ALTER TABLE `brandmst` DISABLE KEYS */;
/*!40000 ALTER TABLE `brandmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colourmst`
--

DROP TABLE IF EXISTS `colourmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colourmst` (
  `COLOUR_CD` varchar(7) NOT NULL,
  `COLOUR_NAME` varchar(50) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`COLOUR_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colourmst`
--

LOCK TABLES `colourmst` WRITE;
/*!40000 ALTER TABLE `colourmst` DISABLE KEYS */;
/*!40000 ALTER TABLE `colourmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contradt`
--

DROP TABLE IF EXISTS `contradt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contradt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` int(11) NOT NULL,
  `AC_CD` varchar(7) NOT NULL,
  `AMT` decimal(15,5) NOT NULL,
  `DRCR` int(11) NOT NULL,
  `PART` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contradt`
--

LOCK TABLES `contradt` WRITE;
/*!40000 ALTER TABLE `contradt` DISABLE KEYS */;
/*!40000 ALTER TABLE `contradt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contrahd`
--

DROP TABLE IF EXISTS `contrahd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contrahd` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `DR` decimal(15,5) NOT NULL,
  `CR` decimal(15,5) NOT NULL,
  `IS_DEL` int(11) DEFAULT '0',
  `PRN_NO` int(11) DEFAULT '0',
  `USER_ID` int(11) NOT NULL,
  `EDIT_NO` int(11) NOT NULL DEFAULT '0',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REMARK` varchar(255) DEFAULT NULL,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `branch_cd` decimal(3,0) DEFAULT NULL,
  `rec_date` date DEFAULT NULL,
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrahd`
--

LOCK TABLES `contrahd` WRITE;
/*!40000 ALTER TABLE `contrahd` DISABLE KEYS */;
/*!40000 ALTER TABLE `contrahd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cprdt`
--

DROP TABLE IF EXISTS `cprdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cprdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `DOC_REF_NO` varchar(7) DEFAULT NULL,
  `BAL` decimal(10,2) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cprdt`
--

LOCK TABLES `cprdt` WRITE;
/*!40000 ALTER TABLE `cprdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `cprdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cprhd`
--

DROP TABLE IF EXISTS `cprhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cprhd` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `TOT_BAL` decimal(10,2) DEFAULT '0.00',
  `AC_CD` varchar(7) DEFAULT NULL,
  `CTYPE` decimal(1,0) DEFAULT NULL,
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `branch_cd` decimal(2,0) DEFAULT '0',
  `cash_ac_cd` varchar(7) DEFAULT 'A000001',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cprhd`
--

LOCK TABLES `cprhd` WRITE;
/*!40000 ALTER TABLE `cprhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `cprhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dcdt`
--

DROP TABLE IF EXISTS `dcdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dcdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `RATE` decimal(10,2) DEFAULT '0.00',
  `AMT` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  `sr_name` varchar(255) DEFAULT '',
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dcdt`
--

LOCK TABLES `dcdt` WRITE;
/*!40000 ALTER TABLE `dcdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `dcdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dchd`
--

DROP TABLE IF EXISTS `dchd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dchd` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `V_TYPE` decimal(1,0) DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `DET_TOT` decimal(10,2) DEFAULT '0.00',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `branch_cd` decimal(3,0) DEFAULT '1',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dchd`
--

LOCK TABLES `dchd` WRITE;
/*!40000 ALTER TABLE `dchd` DISABLE KEYS */;
/*!40000 ALTER TABLE `dchd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `denomation`
--

DROP TABLE IF EXISTS `denomation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `denomation` (
  `v_date` date NOT NULL,
  `branch_cd` decimal(3,0) NOT NULL DEFAULT '0',
  `note_cd` decimal(1,0) NOT NULL DEFAULT '0',
  `qty` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`v_date`,`branch_cd`,`note_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `denomation`
--

LOCK TABLES `denomation` WRITE;
/*!40000 ALTER TABLE `denomation` DISABLE KEYS */;
/*!40000 ALTER TABLE `denomation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dncndt`
--

DROP TABLE IF EXISTS `dncndt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dncndt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `DOC_REF_NO` varchar(7) DEFAULT NULL,
  `BAL` decimal(10,2) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dncndt`
--

LOCK TABLES `dncndt` WRITE;
/*!40000 ALTER TABLE `dncndt` DISABLE KEYS */;
/*!40000 ALTER TABLE `dncndt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dncnhd`
--

DROP TABLE IF EXISTS `dncnhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dncnhd` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `TOT_BAL` decimal(10,2) DEFAULT '0.00',
  `AC_CD` varchar(7) DEFAULT NULL,
  `BANK_CD` varchar(7) DEFAULT NULL,
  `CTYPE` decimal(1,0) DEFAULT NULL,
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `branch_cd` decimal(2,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dncnhd`
--

LOCK TABLES `dncnhd` WRITE;
/*!40000 ALTER TABLE `dncnhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `dncnhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupmst`
--

DROP TABLE IF EXISTS `groupmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupmst` (
  `GRP_CD` varchar(7) NOT NULL,
  `GROUP_NAME` varchar(50) NOT NULL,
  `HEAD` decimal(5,0) DEFAULT '0',
  `HEAD_GRP` varchar(7) DEFAULT NULL,
  `ACC_EFF` int(11) DEFAULT NULL,
  `SIDE` int(11) DEFAULT '2',
  `EDIT_NO` smallint(6) DEFAULT '0',
  `USER_ID` int(11) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`GRP_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupmst`
--

LOCK TABLES `groupmst` WRITE;
/*!40000 ALTER TABLE `groupmst` DISABLE KEYS */;
INSERT INTO `groupmst` VALUES ('G000001','SUNDRY DEBTORS',0,'',2,1,0,1,'2012-11-06 15:15:06'),('G000002','SUNDRY CREDITORS',0,'',2,0,0,1,'2012-11-06 15:15:09'),('G000003','CAPITAL',0,'',2,0,0,1,'2012-11-06 15:15:12'),('G000004','ASSETS',0,'',2,1,0,1,'2012-11-06 15:15:15'),('G000005','DIRECT EXPENSES',0,'',0,0,0,1,'2012-11-06 15:15:18'),('G000006','DIRECT INCOME',0,'',0,1,0,1,'2012-11-06 15:15:20'),('G000007','CURRENT LIABILITIES',0,'',2,0,0,1,'2012-11-06 15:15:22'),('G000008','CURRENT ASSETS',0,'',2,1,0,1,'2012-11-06 15:15:24'),('G000009','INDIRECT EXPENSES',0,'',1,0,0,1,'2012-11-06 15:15:27'),('G000010','INDIRECT INCOME',0,'',1,1,0,1,'2012-11-06 15:15:32'),('G000011','LOANS & LIABILITIES',0,'',2,0,0,1,'2012-11-06 15:15:35'),('G000012','CASH',0,'',2,1,0,1,'2013-02-06 12:52:57'),('G000013','BANK',0,'',2,1,0,1,'2013-02-06 12:52:57'),('G000014','LOANS & ASSETS',0,'',2,1,0,1,'2012-11-06 15:15:35'),('G000015','PURCHASE',0,'',0,0,0,1,'2014-11-23 00:40:06'),('G000016','SALES',0,'',0,1,0,1,'2014-11-23 00:40:06'),('G000017','SALES RETURN',0,'',0,0,0,1,'2014-11-23 00:40:06'),('G000018','PURCHASE RETURN',0,'',0,1,0,1,'2014-11-23 00:40:06');
/*!40000 ALTER TABLE `groupmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jvdt`
--

DROP TABLE IF EXISTS `jvdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jvdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` int(11) NOT NULL,
  `AC_CD` varchar(7) NOT NULL,
  `AMT` decimal(15,5) NOT NULL,
  `DRCR` int(11) NOT NULL,
  `PART` varchar(255) DEFAULT NULL,
  `imei` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jvdt`
--

LOCK TABLES `jvdt` WRITE;
/*!40000 ALTER TABLE `jvdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `jvdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jvhd`
--

DROP TABLE IF EXISTS `jvhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jvhd` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `DR` decimal(15,5) NOT NULL,
  `CR` decimal(15,5) NOT NULL,
  `IS_DEL` int(11) DEFAULT '0',
  `PRN_NO` int(11) DEFAULT '0',
  `USER_ID` int(11) NOT NULL,
  `EDIT_NO` int(11) NOT NULL DEFAULT '0',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REMARK` varchar(255) DEFAULT NULL,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `branch_cd` decimal(3,0) DEFAULT NULL,
  `rec_date` date DEFAULT NULL,
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jvhd`
--

LOCK TABLES `jvhd` WRITE;
/*!40000 ALTER TABLE `jvhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `jvhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lbrpdt`
--

DROP TABLE IF EXISTS `lbrpdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lbrpdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(4,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `RATE` decimal(10,2) DEFAULT '0.00',
  `TAX_CD` varchar(7) DEFAULT NULL,
  `BASIC_AMT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `AMT` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  `DISC_RATE` decimal(10,2) DEFAULT '0.00',
  `MRP` decimal(10,2) DEFAULT '0.00',
  `IS_MAIN` decimal(1,0) DEFAULT '1',
  `nlc` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lbrpdt`
--

LOCK TABLES `lbrpdt` WRITE;
/*!40000 ALTER TABLE `lbrpdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `lbrpdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lbrphd`
--

DROP TABLE IF EXISTS `lbrphd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lbrphd` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `DUE_DATE` date DEFAULT NULL,
  `V_TYPE` decimal(1,0) DEFAULT NULL,
  `PMT_MODE` decimal(1,0) DEFAULT NULL,
  `BILL_DATE` date DEFAULT NULL,
  `BILL_NO` varchar(50) DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `DET_TOT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADJST` decimal(10,2) DEFAULT '0.00',
  `REMARK` varchar(255) DEFAULT NULL,
  `FR_CHG` decimal(10,2) DEFAULT '0.00',
  `NET_AMT` decimal(10,2) DEFAULT '0.00',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BRANCH_CD` int(11) DEFAULT NULL,
  `scheme_cd` varchar(7) DEFAULT NULL,
  `tax_type` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lbrphd`
--

LOCK TABLES `lbrphd` WRITE;
/*!40000 ALTER TABLE `lbrphd` DISABLE KEYS */;
/*!40000 ALTER TABLE `lbrphd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memorymst`
--

DROP TABLE IF EXISTS `memorymst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `memorymst` (
  `MEMORY_CD` varchar(7) NOT NULL,
  `MEMORY_NAME` varchar(20) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`MEMORY_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memorymst`
--

LOCK TABLES `memorymst` WRITE;
/*!40000 ALTER TABLE `memorymst` DISABLE KEYS */;
/*!40000 ALTER TABLE `memorymst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelmst`
--

DROP TABLE IF EXISTS `modelmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modelmst` (
  `MODEL_CD` varchar(7) NOT NULL,
  `MODEL_NAME` varchar(150) DEFAULT NULL,
  `BRAND_CD` varchar(7) DEFAULT NULL,
  `TAX_CD` varchar(7) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TYPE_CD` varchar(7) DEFAULT NULL,
  `sub_type_cd` varchar(7) DEFAULT NULL,
  `HSN_CODE` varchar(50) DEFAULT '',
  `gst_cd` varchar(7) DEFAULT '',
  PRIMARY KEY (`MODEL_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelmst`
--

LOCK TABLES `modelmst` WRITE;
/*!40000 ALTER TABLE `modelmst` DISABLE KEYS */;
/*!40000 ALTER TABLE `modelmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notes` (
  `rec_no` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `ac_cd` varchar(7) DEFAULT NULL,
  `descr` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`rec_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `odbdt`
--

DROP TABLE IF EXISTS `odbdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `odbdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `BAL` decimal(10,2) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `odbdt`
--

LOCK TABLES `odbdt` WRITE;
/*!40000 ALTER TABLE `odbdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `odbdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `odbhd`
--

DROP TABLE IF EXISTS `odbhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `odbhd` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `TOT_BAL` decimal(10,2) DEFAULT '0.00',
  `AC_CD` varchar(7) DEFAULT NULL,
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `branch_cd` decimal(2,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `odbhd`
--

LOCK TABLES `odbhd` WRITE;
/*!40000 ALTER TABLE `odbhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `odbhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oldb0_1`
--

DROP TABLE IF EXISTS `oldb0_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oldb0_1` (
  `REC_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `SR_CD` varchar(7) DEFAULT NULL,
  `BRANCH_CD` decimal(2,0) DEFAULT NULL,
  `PRD_ST_CD` decimal(1,0) DEFAULT NULL,
  `OPB` decimal(4,0) DEFAULT '0',
  `PPUR_4` decimal(4,0) DEFAULT '0',
  `PSAL_4` decimal(4,0) DEFAULT '0',
  `PPUR_5` decimal(4,0) DEFAULT '0',
  `PSAL_5` decimal(4,0) DEFAULT '0',
  `PPUR_6` decimal(4,0) DEFAULT '0',
  `PSAL_6` decimal(4,0) DEFAULT '0',
  `PPUR_7` decimal(4,0) DEFAULT '0',
  `PSAL_7` decimal(4,0) DEFAULT '0',
  `PPUR_8` decimal(4,0) DEFAULT '0',
  `PSAL_8` decimal(4,0) DEFAULT '0',
  `PPUR_9` decimal(4,0) DEFAULT '0',
  `PSAL_9` decimal(4,0) DEFAULT '0',
  `PPUR_10` decimal(4,0) DEFAULT '0',
  `PSAL_10` decimal(4,0) DEFAULT '0',
  `PPUR_11` decimal(4,0) DEFAULT '0',
  `PSAL_11` decimal(4,0) DEFAULT '0',
  `PPUR_12` decimal(4,0) DEFAULT '0',
  `PSAL_12` decimal(4,0) DEFAULT '0',
  `PPUR_1` decimal(4,0) DEFAULT '0',
  `PSAL_1` decimal(4,0) DEFAULT '0',
  `PPUR_2` decimal(4,0) DEFAULT '0',
  `PSAL_2` decimal(4,0) DEFAULT '0',
  `PPUR_3` decimal(4,0) DEFAULT '0',
  `PSAL_3` decimal(4,0) DEFAULT '0',
  PRIMARY KEY (`REC_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oldb0_1`
--

LOCK TABLES `oldb0_1` WRITE;
/*!40000 ALTER TABLE `oldb0_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `oldb0_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oldb0_2`
--

DROP TABLE IF EXISTS `oldb0_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oldb0_2` (
  `REC_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `DOC_REF_NO` varchar(10) DEFAULT NULL,
  `DOC_CD` varchar(3) DEFAULT NULL,
  `INV_NO` int(11) DEFAULT '0',
  `DOC_DATE` date DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `BRANCH_CD` decimal(2,0) DEFAULT NULL,
  `PRD_ST_CD` decimal(1,0) DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `PCS` decimal(4,0) DEFAULT NULL,
  `TRNS_ID` varchar(1) DEFAULT NULL,
  `RATE` decimal(10,2) DEFAULT '0.00',
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TAG_NO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`REC_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oldb0_2`
--

LOCK TABLES `oldb0_2` WRITE;
/*!40000 ALTER TABLE `oldb0_2` DISABLE KEYS */;
/*!40000 ALTER TABLE `oldb0_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oldb2_1`
--

DROP TABLE IF EXISTS `oldb2_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oldb2_1` (
  `REC_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `AC_CD` varchar(7) DEFAULT NULL,
  `OPB` decimal(10,2) DEFAULT '0.00',
  `DR_4` decimal(10,2) DEFAULT '0.00',
  `CR_4` decimal(10,2) DEFAULT '0.00',
  `DR_5` decimal(10,2) DEFAULT '0.00',
  `CR_5` decimal(10,2) DEFAULT '0.00',
  `DR_6` decimal(10,2) DEFAULT '0.00',
  `CR_6` decimal(10,2) DEFAULT '0.00',
  `DR_7` decimal(10,2) DEFAULT '0.00',
  `CR_7` decimal(10,2) DEFAULT '0.00',
  `DR_8` decimal(10,2) DEFAULT '0.00',
  `CR_8` decimal(10,2) DEFAULT '0.00',
  `DR_9` decimal(10,2) DEFAULT '0.00',
  `CR_9` decimal(10,2) DEFAULT '0.00',
  `DR_10` decimal(10,2) DEFAULT '0.00',
  `CR_10` decimal(10,2) DEFAULT '0.00',
  `DR_11` decimal(10,2) DEFAULT '0.00',
  `CR_11` decimal(10,2) DEFAULT '0.00',
  `DR_12` decimal(10,2) DEFAULT '0.00',
  `CR_12` decimal(10,2) DEFAULT '0.00',
  `DR_1` decimal(10,2) DEFAULT '0.00',
  `CR_1` decimal(10,2) DEFAULT '0.00',
  `DR_2` decimal(10,2) DEFAULT '0.00',
  `CR_2` decimal(10,2) DEFAULT '0.00',
  `DR_3` decimal(10,2) DEFAULT '0.00',
  `CR_3` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`REC_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oldb2_1`
--

LOCK TABLES `oldb2_1` WRITE;
/*!40000 ALTER TABLE `oldb2_1` DISABLE KEYS */;
INSERT INTO `oldb2_1` VALUES (1,'T17G001',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(2,'T17G002',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(3,'T17G003',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(4,'T17G004',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(5,'T17G005',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(6,'T17G006',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(7,'T17G007',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(8,'T17G008',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(9,'T17G009',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(10,'A7G0001',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(11,'A7G0002',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(12,'A7G0003',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(13,'A7G0004',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(14,'A7G0005',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(15,'A7G0006',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(16,'A7G0007',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(17,'A7G0008',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00),(18,'A7G0009',0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `oldb2_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oldb2_2`
--

DROP TABLE IF EXISTS `oldb2_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oldb2_2` (
  `REC_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `DOC_REF_NO` varchar(7) DEFAULT NULL,
  `INV_NO` decimal(5,0) DEFAULT '0',
  `DOC_DATE` date DEFAULT NULL,
  `DOC_CD` varchar(3) DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `VAL` decimal(10,2) DEFAULT NULL,
  `CRDR` decimal(1,0) DEFAULT NULL,
  `PARTICULAR` varchar(255) DEFAULT NULL,
  `OPP_AC_CD` varchar(7) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REC_DATE` date DEFAULT NULL,
  `CHQ_NO` varchar(25) DEFAULT '',
  `REC_BANK_NAME` varchar(255) DEFAULT NULL,
  `BRANCH_CD` decimal(2,0) DEFAULT '0',
  PRIMARY KEY (`REC_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oldb2_2`
--

LOCK TABLES `oldb2_2` WRITE;
/*!40000 ALTER TABLE `oldb2_2` DISABLE KEYS */;
INSERT INTO `oldb2_2` VALUES (1,'',0,'2017-04-01','OPB','T17G001',0.00,0,'Opening Balance','0','2017-07-05 12:50:01','2016-04-01','',NULL,0),(2,'',0,'2017-04-01','OPB','T17G002',0.00,0,'Opening Balance','0','2017-07-05 12:50:01','2016-04-01','',NULL,0),(3,'',0,'2017-04-01','OPB','T17G003',0.00,0,'Opening Balance','0','2017-07-05 12:50:01','2016-04-01','',NULL,0),(4,'',0,'2017-04-01','OPB','T17G004',0.00,0,'Opening Balance','0','2017-07-05 12:50:06','2016-04-01','',NULL,0),(5,'',0,'2017-04-01','OPB','T17G005',0.00,0,'Opening Balance','0','2017-07-05 12:50:06','2016-04-01','',NULL,0),(6,'',0,'2017-04-01','OPB','T17G006',0.00,0,'Opening Balance','0','2017-07-05 12:50:06','2016-04-01','',NULL,0),(7,'',0,'2017-04-01','OPB','T17G007',0.00,0,'Opening Balance','0','2017-07-05 12:50:11','2016-04-01','',NULL,0),(8,'',0,'2017-04-01','OPB','T17G008',0.00,0,'Opening Balance','0','2017-07-05 12:50:11','2016-04-01','',NULL,0),(9,'',0,'2017-04-01','OPB','T17G009',0.00,0,'Opening Balance','0','2017-07-05 12:50:11','2016-04-01','',NULL,0),(10,'',0,'2016-04-01','OPB','A7G0001',0.00,0,'Opening Balance','0','2017-07-05 12:51:18','2016-04-01','',NULL,0),(11,'',0,'2016-04-01','OPB','A7G0002',0.00,0,'Opening Balance','0','2017-07-05 15:27:38','2016-04-01','',NULL,0),(12,'',0,'2016-04-01','OPB','A7G0003',0.00,0,'Opening Balance','0','2017-07-05 15:27:57','2016-04-01','',NULL,0),(13,'',0,'2016-04-01','OPB','A7G0004',0.00,0,'Opening Balance','0','2017-07-05 15:28:09','2016-04-01','',NULL,0),(14,'',0,'2016-04-01','OPB','A7G0005',0.00,0,'Opening Balance','0','2017-07-05 15:28:25','2016-04-01','',NULL,0),(15,'',0,'2016-04-01','OPB','A7G0006',0.00,0,'Opening Balance','0','2017-07-05 15:28:40','2016-04-01','',NULL,0),(16,'',0,'2016-04-01','OPB','A7G0007',0.00,0,'Opening Balance','0','2017-07-05 15:28:57','2016-04-01','',NULL,0),(17,'',0,'2016-04-01','OPB','A7G0008',0.00,0,'Opening Balance','0','2017-07-05 15:29:14','2016-04-01','',NULL,0),(18,'',0,'2016-04-01','OPB','A7G0009',0.00,0,'Opening Balance','0','2017-07-05 15:29:27','2016-04-01','',NULL,0);
/*!40000 ALTER TABLE `oldb2_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oldb2_3`
--

DROP TABLE IF EXISTS `oldb2_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oldb2_3` (
  `REC_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `DOC_REF_NO` varchar(10) NOT NULL,
  `DOC_CD` varchar(3) DEFAULT NULL,
  `INV_NO` decimal(4,0) DEFAULT NULL,
  `DOC_DATE` date DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `TOT_AMT` decimal(13,2) DEFAULT '0.00',
  `UNPAID_AMT` decimal(13,2) DEFAULT '0.00',
  `DUE_DATE` date DEFAULT NULL,
  `CUR_ADJST` decimal(13,2) DEFAULT '0.00',
  PRIMARY KEY (`REC_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oldb2_3`
--

LOCK TABLES `oldb2_3` WRITE;
/*!40000 ALTER TABLE `oldb2_3` DISABLE KEYS */;
/*!40000 ALTER TABLE `oldb2_3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oldb2_4`
--

DROP TABLE IF EXISTS `oldb2_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oldb2_4` (
  `REC_NO` bigint(20) NOT NULL AUTO_INCREMENT,
  `DOC_REF_NO` varchar(10) NOT NULL,
  `DOC_CD` varchar(3) DEFAULT NULL,
  `INV_NO` decimal(4,0) DEFAULT NULL,
  `DOC_DATE` date DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `TOT_AMT` decimal(13,2) DEFAULT '0.00',
  `UNPAID_AMT` decimal(13,2) DEFAULT '0.00',
  `DUE_DATE` date DEFAULT NULL,
  `CUR_ADJST` decimal(13,2) DEFAULT '0.00',
  `SR_NO` decimal(2,0) DEFAULT '1',
  PRIMARY KEY (`REC_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oldb2_4`
--

LOCK TABLES `oldb2_4` WRITE;
/*!40000 ALTER TABLE `oldb2_4` DISABLE KEYS */;
INSERT INTO `oldb2_4` VALUES (1,'T17G001','OPB',0,'2017-04-01','T17G001',0.00,0.00,'2016-04-01',0.00,1),(2,'T17G002','OPB',0,'2017-04-01','T17G002',0.00,0.00,'2016-04-01',0.00,1),(3,'T17G003','OPB',0,'2017-04-01','T17G003',0.00,0.00,'2016-04-01',0.00,1),(4,'T17G004','OPB',0,'2017-04-01','T17G004',0.00,0.00,'2016-04-01',0.00,1),(5,'T17G005','OPB',0,'2017-04-01','T17G005',0.00,0.00,'2016-04-01',0.00,1),(6,'T17G006','OPB',0,'2017-04-01','T17G006',0.00,0.00,'2016-04-01',0.00,1),(7,'T17G007','OPB',0,'2017-04-01','T17G007',0.00,0.00,'2016-04-01',0.00,1),(8,'T17G008','OPB',0,'2017-04-01','T17G008',0.00,0.00,'2016-04-01',0.00,1),(9,'T17G009','OPB',0,'2017-04-01','T17G009',0.00,0.00,'2016-04-01',0.00,1),(10,'OPB','OPB',0,'2017-04-01','A7G0001',0.00,0.00,'2017-04-01',0.00,1),(11,'OPB','OPB',0,'2017-04-01','A7G0002',0.00,0.00,'2017-04-01',0.00,1),(12,'OPB','OPB',0,'2017-04-01','A7G0003',0.00,0.00,'2017-04-01',0.00,1),(13,'OPB','OPB',0,'2017-04-01','A7G0004',0.00,0.00,'2017-04-01',0.00,1),(14,'OPB','OPB',0,'2017-04-01','A7G0005',0.00,0.00,'2017-04-01',0.00,1),(15,'OPB','OPB',0,'2017-04-01','A7G0006',0.00,0.00,'2017-04-01',0.00,1),(16,'OPB','OPB',0,'2017-04-01','A7G0007',0.00,0.00,'2017-04-01',0.00,1),(17,'OPB','OPB',0,'2017-04-01','A7G0008',0.00,0.00,'2017-04-01',0.00,1),(18,'OPB','OPB',0,'2017-04-01','A7G0009',0.00,0.00,'2017-04-01',0.00,1);
/*!40000 ALTER TABLE `oldb2_4` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opb_sr_val`
--

DROP TABLE IF EXISTS `opb_sr_val`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opb_sr_val` (
  `SR_CD` varchar(7) NOT NULL,
  `SR_NO` smallint(6) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERIAL_NO` varchar(25) DEFAULT NULL,
  `P_RATE` decimal(10,2) DEFAULT '0.00',
  `BRANCH_CD` bigint(20) DEFAULT NULL,
  `REF_NO` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`SR_CD`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opb_sr_val`
--

LOCK TABLES `opb_sr_val` WRITE;
/*!40000 ALTER TABLE `opb_sr_val` DISABLE KEYS */;
/*!40000 ALTER TABLE `opb_sr_val` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderbook`
--

DROP TABLE IF EXISTS `orderbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderbook` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `amt` decimal(10,2) DEFAULT '0.00',
  `AC_CD` varchar(7) DEFAULT NULL,
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `model_cd` varchar(7) DEFAULT NULL,
  `memory_cd` varchar(7) DEFAULT NULL,
  `colour_cd` varchar(7) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `edit_no` decimal(2,0) DEFAULT '0',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderbook`
--

LOCK TABLES `orderbook` WRITE;
/*!40000 ALTER TABLE `orderbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `REF_NO` varchar(7) DEFAULT NULL,
  `CASH_AMT` decimal(15,5) DEFAULT '0.00000',
  `BANK_CD` varchar(7) DEFAULT NULL,
  `BANK_NAME` varchar(255) DEFAULT NULL,
  `BANK_BRANCH` varchar(255) DEFAULT NULL,
  `CHEQUE_NO` varchar(50) DEFAULT NULL,
  `CHEQUE_DATE` date DEFAULT NULL,
  `BANK_AMT` decimal(15,5) DEFAULT '0.00000',
  `CARD_NAME` varchar(7) DEFAULT NULL,
  `CARD_AMT` decimal(15,5) DEFAULT '0.00000',
  `USER_ID` decimal(3,0) DEFAULT '1',
  `VOU_DATE` date DEFAULT NULL,
  `BAJAJ_AMT` decimal(10,2) DEFAULT '0.00',
  `BAJAJ_NAME` varchar(7) DEFAULT NULL,
  `SFID` varchar(25) DEFAULT NULL,
  `bajaj_per` decimal(10,2) DEFAULT '0.00',
  `bajaj_chg` decimal(10,2) DEFAULT '0.00',
  `card_per` decimal(10,2) DEFAULT '0.00',
  `card_chg` decimal(10,2) DEFAULT '0.00',
  `DISC_AMT` decimal(10,2) DEFAULT '0.00',
  `card_no` varchar(16) DEFAULT NULL,
  `tid_no` varchar(8) DEFAULT NULL,
  `cash_ac_cd` varchar(7) DEFAULT 'A000001'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentlg`
--

DROP TABLE IF EXISTS `paymentlg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymentlg` (
  `REF_NO` varchar(7) DEFAULT NULL,
  `CASH_AMT` decimal(15,5) DEFAULT '0.00000',
  `BANK_CD` varchar(7) DEFAULT NULL,
  `BANK_NAME` varchar(255) DEFAULT NULL,
  `BANK_BRANCH` varchar(255) DEFAULT NULL,
  `CHEQUE_NO` varchar(50) DEFAULT NULL,
  `CHEQUE_DATE` date DEFAULT NULL,
  `BANK_AMT` decimal(15,5) DEFAULT '0.00000',
  `CARD_NAME` varchar(7) DEFAULT NULL,
  `CARD_AMT` decimal(15,5) DEFAULT '0.00000',
  `USER_ID` decimal(3,0) DEFAULT '1',
  `VOU_DATE` date DEFAULT NULL,
  `BAJAJ_AMT` decimal(10,2) DEFAULT '0.00',
  `BAJAJ_NAME` varchar(7) DEFAULT NULL,
  `SFID` varchar(25) DEFAULT NULL,
  `bajaj_per` decimal(10,2) DEFAULT '0.00',
  `bajaj_chg` decimal(10,2) DEFAULT '0.00',
  `card_per` decimal(10,2) DEFAULT '0.00',
  `card_chg` decimal(10,2) DEFAULT '0.00',
  `DISC_AMT` decimal(10,2) DEFAULT '0.00',
  `card_no` varchar(16) DEFAULT NULL,
  `tid_no` varchar(8) DEFAULT NULL,
  `cash_ac_cd` varchar(7) DEFAULT 'A000001'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentlg`
--

LOCK TABLES `paymentlg` WRITE;
/*!40000 ALTER TABLE `paymentlg` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentlg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phbkmst`
--

DROP TABLE IF EXISTS `phbkmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phbkmst` (
  `AC_CD` varchar(7) NOT NULL,
  `LL_NO` varchar(20) NOT NULL,
  `FAX_NO` varchar(20) NOT NULL,
  `MOBILE1` varchar(25) NOT NULL,
  `MOBILE2` varchar(25) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `EMAIL2` varchar(100) DEFAULT NULL,
  `SCHEMESMSSENT` smallint(6) DEFAULT '0',
  PRIMARY KEY (`AC_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phbkmst`
--

LOCK TABLES `phbkmst` WRITE;
/*!40000 ALTER TABLE `phbkmst` DISABLE KEYS */;
INSERT INTO `phbkmst` VALUES ('A7G0001','','','1','','1','',0),('A7G0002','','','1','','1','',0),('A7G0003','','','1','','','',0),('A7G0004','','','1','','','',0),('A7G0005','','','1','','','',0),('A7G0006','','','1','','','',0),('A7G0007','','','1','','','',0),('A7G0008','','','1','','','',0),('A7G0009','','','1','','','',0),('T17G001','','','','','','',0),('T17G002','','','','','','',0),('T17G003','','','','','','',0),('T17G004','','','','','','',0),('T17G005','','','','','','',0),('T17G006','','','','','','',0),('T17G007','','','','','','',0),('T17G008','','','','','','',0),('T17G009','','','','','','',0);
/*!40000 ALTER TABLE `phbkmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prdt`
--

DROP TABLE IF EXISTS `prdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `RATE` decimal(10,2) DEFAULT '0.00',
  `TAX_CD` varchar(7) DEFAULT NULL,
  `BASIC_AMT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `AMT` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  `DISC_RATE` decimal(10,2) DEFAULT '0.00',
  `MRP` decimal(10,2) DEFAULT '0.00',
  `IS_MAIN` decimal(1,0) DEFAULT '1',
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prdt`
--

LOCK TABLES `prdt` WRITE;
/*!40000 ALTER TABLE `prdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `prdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prhd`
--

DROP TABLE IF EXISTS `prhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prhd` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `PMT_DAYS` decimal(3,0) DEFAULT '0',
  `DUE_DATE` date DEFAULT NULL,
  `V_TYPE` decimal(1,0) DEFAULT NULL,
  `PMT_MODE` decimal(1,0) DEFAULT NULL,
  `BILL_DATE` date DEFAULT NULL,
  `BILL_NO` varchar(50) DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `DET_TOT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADJST` decimal(10,2) DEFAULT '0.00',
  `REMARK` varchar(255) DEFAULT NULL,
  `FR_CHG` decimal(10,2) DEFAULT '0.00',
  `NET_AMT` decimal(10,2) DEFAULT '0.00',
  `ADVANCE_AMT` decimal(10,2) DEFAULT '0.00',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BRANCH_CD` int(11) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prhd`
--

LOCK TABLES `prhd` WRITE;
/*!40000 ALTER TABLE `prhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `prhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quotationdt`
--

DROP TABLE IF EXISTS `quotationdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quotationdt` (
  `ref_no` varchar(10) NOT NULL,
  `sr_no` decimal(3,0) NOT NULL,
  `sr_cd` varchar(7) DEFAULT NULL,
  `QTY` int(11) DEFAULT NULL,
  `disc_per` decimal(10,2) DEFAULT '0.00',
  `rate` decimal(10,2) DEFAULT '0.00',
  `mrp` decimal(10,2) DEFAULT '0.00',
  `amount` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`ref_no`,`sr_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotationdt`
--

LOCK TABLES `quotationdt` WRITE;
/*!40000 ALTER TABLE `quotationdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `quotationdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quotationhd`
--

DROP TABLE IF EXISTS `quotationhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quotationhd` (
  `ref_no` varchar(10) NOT NULL,
  `inv_no` int(11) DEFAULT NULL,
  `v_date` date DEFAULT NULL,
  `ac_cd` varchar(7) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `is_del` decimal(1,0) DEFAULT '0',
  `net_amt` decimal(10,2) DEFAULT '0.00',
  `branch_cd` decimal(1,0) DEFAULT NULL,
  `user_id` decimal(3,0) DEFAULT NULL,
  `edit_no` decimal(2,0) DEFAULT '0',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ref_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotationhd`
--

LOCK TABLES `quotationhd` WRITE;
/*!40000 ALTER TABLE `quotationhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `quotationhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refmst`
--

DROP TABLE IF EXISTS `refmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refmst` (
  `REF_CD` varchar(7) NOT NULL,
  `REF_NAME` varchar(50) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`REF_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refmst`
--

LOCK TABLES `refmst` WRITE;
/*!40000 ALTER TABLE `refmst` DISABLE KEYS */;
INSERT INTO `refmst` VALUES ('RF17G01','ADMIN',0,1,'2017-07-05 12:48:20');
/*!40000 ALTER TABLE `refmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schememst`
--

DROP TABLE IF EXISTS `schememst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schememst` (
  `SCHEME_CD` varchar(7) NOT NULL,
  `SCHEME_NAME` varchar(255) DEFAULT NULL,
  `TYPE_CD` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SCHEME_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schememst`
--

LOCK TABLES `schememst` WRITE;
/*!40000 ALTER TABLE `schememst` DISABLE KEYS */;
INSERT INTO `schememst` VALUES ('SC17G01','NON-PURCHASE',1,0,1,'2017-07-05 12:49:17'),('SC17G02','NON-SALES',0,0,1,'2017-07-05 12:49:23');
/*!40000 ALTER TABLE `schememst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seriesmst`
--

DROP TABLE IF EXISTS `seriesmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seriesmst` (
  `SR_CD` varchar(7) NOT NULL,
  `SR_ALIAS` varchar(50) DEFAULT NULL,
  `SR_NAME` varchar(500) DEFAULT NULL,
  `MODEL_CD` varchar(7) DEFAULT NULL,
  `MEMORY_CD` varchar(7) DEFAULT NULL,
  `COLOUR_CD` varchar(7) DEFAULT NULL,
  `OPB_QTY` decimal(4,0) DEFAULT '0',
  `OPB_VAL` decimal(10,2) DEFAULT '0.00',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(2,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SR_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seriesmst`
--

LOCK TABLES `seriesmst` WRITE;
/*!40000 ALTER TABLE `seriesmst` DISABLE KEYS */;
/*!40000 ALTER TABLE `seriesmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smmst`
--

DROP TABLE IF EXISTS `smmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smmst` (
  `SM_CD` varchar(7) NOT NULL,
  `SM_NAME` varchar(50) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SM_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smmst`
--

LOCK TABLES `smmst` WRITE;
/*!40000 ALTER TABLE `smmst` DISABLE KEYS */;
INSERT INTO `smmst` VALUES ('SM17G01','ADMIN',0,1,'2017-07-05 12:49:39');
/*!40000 ALTER TABLE `smmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srdt`
--

DROP TABLE IF EXISTS `srdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `srdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `RATE` decimal(10,2) DEFAULT '0.00',
  `TAX_CD` varchar(7) DEFAULT NULL,
  `BASIC_AMT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `AMT` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  `DISC_RATE` decimal(10,2) DEFAULT '0.00',
  `MRP` decimal(10,2) DEFAULT '0.00',
  `IS_MAIN` decimal(1,0) DEFAULT '1',
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srdt`
--

LOCK TABLES `srdt` WRITE;
/*!40000 ALTER TABLE `srdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `srdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srhd`
--

DROP TABLE IF EXISTS `srhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `srhd` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `V_TYPE` decimal(1,0) DEFAULT NULL,
  `PMT_MODE` decimal(1,0) DEFAULT NULL,
  `DUE_DATE` date DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `DET_TOT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADJST` decimal(10,2) DEFAULT '0.00',
  `REMARK` varchar(255) DEFAULT NULL,
  `NET_AMT` decimal(10,2) DEFAULT '0.00',
  `ADVANCE_AMT` decimal(10,2) DEFAULT '0.00',
  `PMT_DAYS` decimal(2,0) DEFAULT '0',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BRANCH_CD` int(11) DEFAULT NULL,
  `tax_type` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srhd`
--

LOCK TABLES `srhd` WRITE;
/*!40000 ALTER TABLE `srhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `srhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stkadjdt`
--

DROP TABLE IF EXISTS `stkadjdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stkadjdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(2,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stkadjdt`
--

LOCK TABLES `stkadjdt` WRITE;
/*!40000 ALTER TABLE `stkadjdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `stkadjdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stkadjhd`
--

DROP TABLE IF EXISTS `stkadjhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stkadjhd` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REMARK` varchar(255) DEFAULT NULL,
  `branch_cd` decimal(3,0) DEFAULT '1',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stkadjhd`
--

LOCK TABLES `stkadjhd` WRITE;
/*!40000 ALTER TABLE `stkadjhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `stkadjhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stktrfdt`
--

DROP TABLE IF EXISTS `stktrfdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stktrfdt` (
  `ref_no` varchar(11) NOT NULL,
  `sr_no` int(11) NOT NULL DEFAULT '0',
  `pur_tag_no` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ref_no`,`sr_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stktrfdt`
--

LOCK TABLES `stktrfdt` WRITE;
/*!40000 ALTER TABLE `stktrfdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `stktrfdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stktrfhd`
--

DROP TABLE IF EXISTS `stktrfhd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stktrfhd` (
  `ref_no` varchar(11) NOT NULL,
  `inv_no` decimal(4,0) DEFAULT '0',
  `v_date` date DEFAULT NULL,
  `from_loc` bigint(20) DEFAULT NULL,
  `to_loc` bigint(20) DEFAULT NULL,
  `is_del` decimal(1,0) DEFAULT '0',
  `user_id` bigint(20) DEFAULT NULL,
  `edit_no` decimal(2,0) DEFAULT '0',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approve_by` bigint(20) DEFAULT NULL,
  `approve_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ref_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stktrfhd`
--

LOCK TABLES `stktrfhd` WRITE;
/*!40000 ALTER TABLE `stktrfhd` DISABLE KEYS */;
/*!40000 ALTER TABLE `stktrfhd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stktrfoutdt`
--

DROP TABLE IF EXISTS `stktrfoutdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stktrfoutdt` (
  `ref_no` varchar(10) NOT NULL,
  `sr_no` int(11) NOT NULL DEFAULT '0',
  `pur_tag_no` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ref_no`,`sr_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stktrfoutdt`
--

LOCK TABLES `stktrfoutdt` WRITE;
/*!40000 ALTER TABLE `stktrfoutdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `stktrfoutdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stktrfouthd`
--

DROP TABLE IF EXISTS `stktrfouthd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stktrfouthd` (
  `ref_no` varchar(10) NOT NULL,
  `inv_no` decimal(10,0) DEFAULT '0',
  `v_date` date DEFAULT NULL,
  `from_loc` bigint(20) DEFAULT NULL,
  `to_loc` bigint(20) DEFAULT NULL,
  `is_del` decimal(1,0) DEFAULT '0',
  `user_id` bigint(20) DEFAULT NULL,
  `edit_no` decimal(2,0) DEFAULT '0',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approve_by` bigint(20) DEFAULT NULL,
  `approve_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ref_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stktrfouthd`
--

LOCK TABLES `stktrfouthd` WRITE;
/*!40000 ALTER TABLE `stktrfouthd` DISABLE KEYS */;
/*!40000 ALTER TABLE `stktrfouthd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `REF_NO` varchar(7) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `PUR_RATE` decimal(10,2) DEFAULT '0.00',
  `SALE_RATE` decimal(10,2) DEFAULT '0.00',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `PUR_REF_NO` varchar(7) DEFAULT NULL,
  `SALE_REF_NO` varchar(7) DEFAULT NULL,
  `BASIC_PUR_RATE` decimal(10,2) DEFAULT '0.00',
  `GODOWN` decimal(1,0) DEFAULT '0',
  `MRP` decimal(10,2) DEFAULT '0.00',
  `DISC_RATE` decimal(10,2) DEFAULT '0.00',
  `DISC_RATE_SALES` decimal(10,2) DEFAULT '0.00',
  `BRANCH_CD` decimal(2,0) DEFAULT NULL,
  `IS_MAIN` decimal(1,0) DEFAULT '1',
  `PUR_DATE` date DEFAULT NULL,
  `AC_NAME` varchar(255) DEFAULT NULL,
  `nlc` decimal(10,2) DEFAULT '0.00',
  `old_ref_no` varchar(7) DEFAULT '',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxmst`
--

DROP TABLE IF EXISTS `taxmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taxmst` (
  `TAX_CD` varchar(7) NOT NULL,
  `TAX_NAME` varchar(20) DEFAULT NULL,
  `TAX_PER` decimal(5,2) DEFAULT '0.00',
  `ADD_TAX_PER` decimal(5,2) DEFAULT '0.00',
  `TAX_ON_SALES` decimal(1,0) DEFAULT '1',
  `TAX_AC_CD` varchar(7) DEFAULT NULL,
  `ADD_TAX_AC_CD` varchar(7) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `add_tax_ac_cd1` varchar(7) DEFAULT '',
  PRIMARY KEY (`TAX_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxmst`
--

LOCK TABLES `taxmst` WRITE;
/*!40000 ALTER TABLE `taxmst` DISABLE KEYS */;
INSERT INTO `taxmst` VALUES ('T17G001','GST 12',6.00,6.00,1,'T17G001','T17G002',0,1,'2017-07-05 12:50:01','T17G003'),('T17G002','GST 18',9.00,9.00,1,'T17G004','T17G005',0,1,'2017-07-05 12:50:06','T17G006'),('T17G003','GST 28',14.00,14.00,1,'T17G007','T17G008',0,1,'2017-07-05 12:50:11','T17G009');
/*!40000 ALTER TABLE `taxmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temp_tag`
--

DROP TABLE IF EXISTS `temp_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temp_tag` (
  `tag_no` varchar(25) NOT NULL,
  PRIMARY KEY (`tag_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temp_tag`
--

LOCK TABLES `temp_tag` WRITE;
/*!40000 ALTER TABLE `temp_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `temp_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tidmst`
--

DROP TABLE IF EXISTS `tidmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tidmst` (
  `tid_cd` varchar(7) NOT NULL,
  `tid_name` varchar(255) DEFAULT NULL,
  `user_id` decimal(3,0) DEFAULT NULL,
  `edit_no` decimal(2,0) DEFAULT '0',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tid_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tidmst`
--

LOCK TABLES `tidmst` WRITE;
/*!40000 ALTER TABLE `tidmst` DISABLE KEYS */;
/*!40000 ALTER TABLE `tidmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typemst`
--

DROP TABLE IF EXISTS `typemst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `typemst` (
  `TYPE_CD` varchar(7) NOT NULL,
  `TYPE_NAME` varchar(50) DEFAULT NULL,
  `EDIT_NO` decimal(3,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TYPE_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typemst`
--

LOCK TABLES `typemst` WRITE;
/*!40000 ALTER TABLE `typemst` DISABLE KEYS */;
/*!40000 ALTER TABLE `typemst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vilsdt`
--

DROP TABLE IF EXISTS `vilsdt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vilsdt` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(4,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `RATE` decimal(10,2) DEFAULT '0.00',
  `TAX_CD` varchar(7) DEFAULT NULL,
  `BASIC_AMT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `AMT` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  `DISC_RATE` decimal(10,2) DEFAULT '0.00',
  `MRP` decimal(10,2) DEFAULT '0.00',
  `IS_MAIN` decimal(1,0) DEFAULT '1',
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vilsdt`
--

LOCK TABLES `vilsdt` WRITE;
/*!40000 ALTER TABLE `vilsdt` DISABLE KEYS */;
/*!40000 ALTER TABLE `vilsdt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vilsdtlg`
--

DROP TABLE IF EXISTS `vilsdtlg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vilsdtlg` (
  `REF_NO` varchar(7) NOT NULL,
  `SR_NO` decimal(4,0) NOT NULL,
  `TAG_NO` varchar(30) DEFAULT NULL,
  `SR_CD` varchar(7) DEFAULT NULL,
  `IMEI_NO` varchar(15) DEFAULT NULL,
  `SERAIL_NO` varchar(25) DEFAULT NULL,
  `QTY` decimal(10,2) DEFAULT '0.00',
  `RATE` decimal(10,2) DEFAULT '0.00',
  `TAX_CD` varchar(7) DEFAULT NULL,
  `BASIC_AMT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `AMT` decimal(10,2) DEFAULT '0.00',
  `PUR_TAG_NO` varchar(7) DEFAULT NULL,
  `DISC_RATE` decimal(10,2) DEFAULT '0.00',
  `MRP` decimal(10,2) DEFAULT '0.00',
  `IS_MAIN` decimal(1,0) DEFAULT '1',
  PRIMARY KEY (`REF_NO`,`SR_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vilsdtlg`
--

LOCK TABLES `vilsdtlg` WRITE;
/*!40000 ALTER TABLE `vilsdtlg` DISABLE KEYS */;
/*!40000 ALTER TABLE `vilsdtlg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vilshd`
--

DROP TABLE IF EXISTS `vilshd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vilshd` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `V_TYPE` decimal(1,0) DEFAULT NULL,
  `PMT_MODE` decimal(1,0) DEFAULT NULL,
  `DUE_DATE` date DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `DET_TOT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADJST` decimal(10,2) DEFAULT '0.00',
  `REMARK` varchar(255) DEFAULT NULL,
  `NET_AMT` decimal(10,2) DEFAULT '0.00',
  `BUY_BACK_MODEL` varchar(7) DEFAULT NULL,
  `BUY_BACK_AMT` decimal(10,2) DEFAULT '0.00',
  `PART_NO` varchar(255) DEFAULT NULL,
  `BUY_BACK_IMEI_NO` varchar(25) DEFAULT NULL,
  `ADVANCE_AMT` decimal(10,2) DEFAULT '0.00',
  `INS_CD` varchar(7) DEFAULT NULL,
  `INS_AMT` decimal(10,2) DEFAULT '0.00',
  `BANK_CHARGES` decimal(10,2) DEFAULT '0.00',
  `PMT_DAYS` decimal(2,0) DEFAULT '0',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BRANCH_CD` int(11) DEFAULT NULL,
  `DISCOUNT` decimal(10,2) DEFAULT '0.00',
  `ref_cd` varchar(7) DEFAULT NULL,
  `rec_date` date DEFAULT NULL,
  `sm_cd` varchar(7) DEFAULT 'SM00001',
  `scheme_cd` varchar(7) DEFAULT NULL,
  `add_sr_no` decimal(2,0) DEFAULT '1',
  `tax_type` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vilshd`
--

LOCK TABLES `vilshd` WRITE;
/*!40000 ALTER TABLE `vilshd` DISABLE KEYS */;
/*!40000 ALTER TABLE `vilshd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vilshdlg`
--

DROP TABLE IF EXISTS `vilshdlg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vilshdlg` (
  `REF_NO` varchar(7) NOT NULL,
  `INV_NO` int(11) DEFAULT NULL,
  `V_DATE` date DEFAULT NULL,
  `V_TYPE` decimal(1,0) DEFAULT NULL,
  `PMT_MODE` decimal(1,0) DEFAULT NULL,
  `DUE_DATE` date DEFAULT NULL,
  `AC_CD` varchar(7) DEFAULT NULL,
  `DET_TOT` decimal(10,2) DEFAULT '0.00',
  `TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADD_TAX_AMT` decimal(10,2) DEFAULT '0.00',
  `ADJST` decimal(10,2) DEFAULT '0.00',
  `REMARK` varchar(255) DEFAULT NULL,
  `NET_AMT` decimal(10,2) DEFAULT '0.00',
  `BUY_BACK_MODEL` varchar(7) DEFAULT NULL,
  `BUY_BACK_AMT` decimal(10,2) DEFAULT '0.00',
  `PART_NO` varchar(255) DEFAULT NULL,
  `BUY_BACK_IMEI_NO` varchar(25) DEFAULT NULL,
  `ADVANCE_AMT` decimal(10,2) DEFAULT '0.00',
  `INS_CD` varchar(7) DEFAULT NULL,
  `INS_AMT` decimal(10,2) DEFAULT '0.00',
  `BANK_CHARGES` decimal(10,2) DEFAULT '0.00',
  `PMT_DAYS` decimal(2,0) DEFAULT '0',
  `IS_DEL` decimal(1,0) DEFAULT '0',
  `EDIT_NO` decimal(2,0) DEFAULT '0',
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `TIME_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `INIT_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BRANCH_CD` int(11) DEFAULT NULL,
  `DISCOUNT` decimal(10,2) DEFAULT '0.00',
  `ref_cd` varchar(7) DEFAULT NULL,
  `rec_date` date DEFAULT NULL,
  `sm_cd` varchar(7) DEFAULT 'SM00001',
  `scheme_cd` varchar(7) DEFAULT NULL,
  `add_sr_no` decimal(2,0) DEFAULT '1',
  `tax_type` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vilshdlg`
--

LOCK TABLES `vilshdlg` WRITE;
/*!40000 ALTER TABLE `vilshdlg` DISABLE KEYS */;
/*!40000 ALTER TABLE `vilshdlg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitorbook`
--

DROP TABLE IF EXISTS `visitorbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visitorbook` (
  `REF_NO` varchar(7) NOT NULL,
  `VDATE` date DEFAULT NULL,
  `ac_name` varchar(255) DEFAULT NULL,
  `mobile_no` varchar(25) DEFAULT NULL,
  `USER_ID` decimal(3,0) DEFAULT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  `memory_name` varchar(255) DEFAULT NULL,
  `color_name` varchar(255) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `edit_no` decimal(2,0) DEFAULT '0',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `attended_by` varchar(7) DEFAULT NULL,
  `follow_up_date` date DEFAULT NULL,
  `rev_ref_no` varchar(7) DEFAULT NULL,
  `branch_cd` decimal(2,0) DEFAULT '1',
  `is_del` decimal(2,0) DEFAULT '0',
  PRIMARY KEY (`REF_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitorbook`
--

LOCK TABLES `visitorbook` WRITE;
/*!40000 ALTER TABLE `visitorbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `visitorbook` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-05 21:01:43
CREATE DATABASE  IF NOT EXISTS `skablemain` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `skablemain`;
-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 52.66.132.178    Database: skablemain
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill_mail_list`
--

DROP TABLE IF EXISTS `bill_mail_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill_mail_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_mail_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_mail_list`
--

LOCK TABLES `bill_mail_list` WRITE;
/*!40000 ALTER TABLE `bill_mail_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_mail_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branchmst`
--

DROP TABLE IF EXISTS `branchmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branchmst` (
  `branch_cd` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_name` varchar(255) NOT NULL,
  `sh_name` varchar(2) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `cash_ac_cd` varchar(7) DEFAULT NULL,
  `ins_amt` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`branch_cd`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branchmst`
--

LOCK TABLES `branchmst` WRITE;
/*!40000 ALTER TABLE `branchmst` DISABLE KEYS */;
INSERT INTO `branchmst` VALUES (1,'ADMIN','AD','23,M.R.S HIGH SCHOOL BUILDING','OPP NEW GANJ BAZAR GATE-2','STATION ROAD, UNJHA-384170',' ',' ',' ',1000000.00);
/*!40000 ALTER TABLE `branchmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dbmst`
--

DROP TABLE IF EXISTS `dbmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dbmst` (
  `CMP_NAME` varchar(50) NOT NULL,
  `DB_YEAR` varchar(4) NOT NULL,
  `DB_NAME` varchar(25) NOT NULL,
  PRIMARY KEY (`CMP_NAME`,`DB_YEAR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbmst`
--

LOCK TABLES `dbmst` WRITE;
/*!40000 ALTER TABLE `dbmst` DISABLE KEYS */;
INSERT INTO `dbmst` VALUES ('MANAV MANDIR','2016',' '),('MANAV MANDIR','2017','skable2017');
/*!40000 ALTER TABLE `dbmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formmst`
--

DROP TABLE IF EXISTS `formmst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formmst` (
  `FORM_CD` bigint(20) NOT NULL AUTO_INCREMENT,
  `FORM_NAME` varchar(255) DEFAULT NULL,
  `MENU_CD` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`FORM_CD`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formmst`
--

LOCK TABLES `formmst` WRITE;
/*!40000 ALTER TABLE `formmst` DISABLE KEYS */;
INSERT INTO `formmst` VALUES (1,'GROUP MASTER',1),(2,'ACCOUNT MASTER',1),(3,'BRAND MASTER',1),(4,'TYPE MASTER',1),(5,'MODEL MASTER',1),(6,'COLOR MASTER',1),(7,'MEMORY MASTER',1),(8,'SERIES MASTER',1),(9,'TAX MASTER',1),(10,'RD PURCHASE',2),(11,'URD PURCHASE',2),(12,'PURCHASE RETURN',2),(13,'RETAIL INVOICE',2),(14,'TAX INVOICE',2),(15,'SALES RETURN',2),(16,'SALES INVOICE BY NUMBER',2),(17,'CASH PAYMENT',2),(18,'CASH RECEIPT',2),(19,'BANK PAYMENT',2),(20,'BANK RECEIPT',2),(21,'JOURNAL ENTRY',2),(22,'CONTRA ENTRY',2),(23,'STOCK TRANSFER',2),(24,'DC ISSUE',2),(25,'STOCK ADJUSTMENT',2),(26,'CREDIT NOTE ITEM LIST',2),(27,'STOCK LEDGER',3),(28,'STOCK LEDGER RATE',3),(29,'STOCK ITEMWISE MONTHWISE',3),(30,'STOCK SUMMARY',3),(31,'STOCK SUMMARY BALANCE',3),(32,'STOCK STATEMENT IMEI',3),(33,'STOCK VALUE STATEMENT IMEI',3),(34,'STOCK STATEMENT ACCESSSORY',3),(35,'STOCK VALUE STATEMENT ACCESSORY',3),(36,'STOCK VALUE STATEMENT DATE WISE',3),(37,'STOCK STATEMENT DATE WISE',3),(38,'TAG TRACK TRANSACTIONWISE',3),(39,'SALES REGISTER',4),(40,'SALES REGISTER DETAIL',4),(41,'SALES REGISTER ACCOUNT',4),(42,'INSURANCE REGISTER',4),(43,'PURCHASE REGISTER',4),(44,'PURCHASE REGISTER DETAIL',4),(45,'PURCHASE REGISTER DETAIL ACCOUNT',4),(46,'SALES RETURN REGISTER',4),(47,'SALES RETURN REGISTER DETAIL',4),(48,'SALES RETURN REGISTER DETAIL ACCOUNT',4),(49,'PURCHASE RETURN REGISTER',4),(50,'PURCHASE RETURN REGISTER DETAIL',4),(51,'PURHASE RETURN REGISTER DETAIL ACCOUNT',4),(52,'BUY BACK REGISTER',4),(53,'DC REGISTER',4),(54,'CREDIT NOTE REGISTER',4),(55,'STOCK ADJUSTMENT REGISTER',4),(56,'SALES REGISTER CARD WISE',4),(57,'SALES REGISTER CARD WISE DETAIL',4),(58,'GENERAL LEDGER',4),(59,'GROUP SUMMARY',4),(60,'CASH BOOK',4),(61,'BANK BOOK',4),(62,'DAILY SALES STATEMENT',4),(63,'DAILY SALES STATEMENT DETAIL',4),(64,'TYPE WISE SALES STATEMENT',4),(65,'TYPE WISE SALES STATEMENT DETAIL',4),(66,'TYPE WISE PURCHASE STATEMENT',4),(67,'TYPE WISE PURCHASE STATEMENT DETAIL',4),(68,'MARGIN REPORT',4),(69,'MARGIN REPORT BY TAG',4),(70,'IMEI PS ON PURCHASE',4),(71,'IMEI PS ON SALES',4),(72,'TYPE WISE PROFIT STATEMENT',4),(73,'TYPE WISE BRAND WISE PROFIT STATEMENT',4),(74,'IMEI SEARCH',5),(75,'TAG TRACK',5),(76,'CREATE USER',5),(77,'DUMMY PRINT',5),(78,'UPDATE USER',5),(79,'USER RIGHTS',5),(80,'USER GROUP MASTER',5),(81,'IN TRANSIT REPORT',3),(82,'STOCK IN OUT REPORT',3),(83,'PURCHASE PARTY WISE ONLY CODE',4),(84,'PURCHASE RATE BY TAG',4),(85,'SNAP SHOT',4),(86,'SALESMAN MASTER',1),(87,'REFRAL MASTER',1),(88,'PURCHASE BILL .',2),(89,'RETAIL INVOICE .',2),(90,'CREDIT NOTE',2),(91,'DEBIT NOTE',2),(92,'ORDER BOOK',2),(93,'BRAND WISE ITEM LEDGER',3),(94,'DATE WISE STOCK ON HAND',3),(95,'ITEMWISE DATE/MONTH WISE PURCHASE STATEMENT',3),(96,'ITEMWISE DATE/MONTH WISE SALES STATEMENT',3),(97,'MODELWISE MONTHWISE PURCHASE STATEMENT',3),(98,'MODELWISE MONTHWISE SALES STATEMENT',3),(99,'BRANCHWISE PENDING COLLECTION REPORT',4),(100,'TAX REPORT',4),(101,'JOURNAL REGISTER',4),(102,'OPENING BALANCE REGISTER',4),(103,'TYPE WISE SALES WITHOUT TAG',4),(104,'AVERAGE PURCHASE REPORT',4),(105,'PHONE BOOK',5),(106,'PHONE BOOK VIEW',5),(107,'EOD',5),(108,'BUY BACK TRACK',5),(109,'SCHEME MASTER',1),(110,'TID MASTER',1),(111,'BILL ADJUSTMENT',2),(112,'STOCK TRANSFER BRANCH',2),(113,'VISITOR BOOK',2),(114,'QUOTATION',2),(115,'BRANCH WISE INSURANCE',2),(116,'RD PURCHASE LOCAL',2),(117,'RD PURCAHSE OUTSIDE',2),(118,'PURCAHSE RETURN LOCAL',2),(119,'PURCAHSE RETURN OUTSIDE',2),(120,'SALES INVOICE LOCAL',2),(121,'SALES INVOICE OUTSIDE',2),(122,'SALES RETURN LOCAL',2),(123,'SALES RETURN OUTSIDE',2);
/*!40000 ALTER TABLE `formmst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menumst`
--

DROP TABLE IF EXISTS `menumst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menumst` (
  `MENU_CD` bigint(20) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MENU_CD`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menumst`
--

LOCK TABLES `menumst` WRITE;
/*!40000 ALTER TABLE `menumst` DISABLE KEYS */;
INSERT INTO `menumst` VALUES (1,'MASTER'),(2,'TRANSACTION'),(3,'INVENTORY'),(4,'ACCOUNTS'),(5,'UTILITY');
/*!40000 ALTER TABLE `menumst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_variables`
--

DROP TABLE IF EXISTS `system_variables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_variables` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `PARAM_NAME` varchar(50) DEFAULT NULL,
  `PARAM_VALUE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_variables`
--

LOCK TABLES `system_variables` WRITE;
/*!40000 ALTER TABLE `system_variables` DISABLE KEYS */;
INSERT INTO `system_variables` VALUES (1,'SMS_KEY','A0f090bc3b9c8b12064bf219df4681c64'),(2,'SMS_SENDER','iPearl'),(3,'BILL_MAIL_ID','bill.ipearl@gmail.com'),(4,'BILL_MAIL_PASSWORD','billipearl');
/*!40000 ALTER TABLE `system_variables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_grp_mst`
--

DROP TABLE IF EXISTS `user_grp_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_grp_mst` (
  `USER_GRP_CD` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_GRP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_GRP_CD`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_grp_mst`
--

LOCK TABLES `user_grp_mst` WRITE;
/*!40000 ALTER TABLE `user_grp_mst` DISABLE KEYS */;
INSERT INTO `user_grp_mst` VALUES (1,'ADMIN');
/*!40000 ALTER TABLE `user_grp_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_rights`
--

DROP TABLE IF EXISTS `user_rights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_rights` (
  `USER_GRP_CD` bigint(20) DEFAULT NULL,
  `FORM_CD` bigint(20) DEFAULT NULL,
  `VIEWS` decimal(1,0) DEFAULT '0',
  `ADDS` decimal(1,0) DEFAULT '0',
  `EDITS` decimal(1,0) DEFAULT '0',
  `DELETES` decimal(1,0) DEFAULT '0',
  `PRINTS` decimal(1,0) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_rights`
--

LOCK TABLES `user_rights` WRITE;
/*!40000 ALTER TABLE `user_rights` DISABLE KEYS */;
INSERT INTO `user_rights` VALUES (1,1,1,1,1,1,1),(1,2,1,1,1,1,1),(1,3,1,1,1,1,1),(1,4,1,1,1,1,1),(1,5,1,1,1,1,1),(1,6,1,1,1,1,1),(1,7,1,1,1,1,1),(1,8,1,1,1,1,1),(1,9,1,1,1,1,1),(1,10,1,1,1,1,1),(1,11,1,1,1,1,1),(1,12,1,1,1,1,1),(1,13,1,1,1,1,1),(1,14,1,1,1,1,1),(1,15,1,1,1,1,1),(1,16,1,1,1,1,1),(1,17,1,1,1,1,1),(1,18,1,1,1,1,1),(1,19,1,1,1,1,1),(1,20,1,1,1,1,1),(1,21,1,1,1,1,1),(1,22,1,1,1,1,1),(1,23,1,1,1,1,1),(1,24,1,1,1,1,1),(1,25,1,1,1,1,1),(1,26,1,1,1,1,1),(1,27,1,1,1,1,1),(1,28,1,1,1,1,1),(1,29,1,1,1,1,1),(1,30,1,1,1,1,1),(1,31,1,1,1,1,1),(1,32,1,1,1,1,1),(1,33,1,1,1,1,1),(1,34,1,1,1,1,1),(1,35,1,1,1,1,1),(1,36,1,1,1,1,1),(1,37,1,1,1,1,1),(1,38,1,1,1,1,1),(1,39,1,1,1,1,1),(1,40,1,1,1,1,1),(1,41,1,1,1,1,1),(1,42,1,1,1,1,1),(1,43,1,1,1,1,1),(1,44,1,1,1,1,1),(1,45,1,1,1,1,1),(1,46,1,1,1,1,1),(1,47,1,1,1,1,1),(1,48,1,1,1,1,1),(1,49,1,1,1,1,1),(1,50,1,1,1,1,1),(1,51,1,1,1,1,1),(1,52,1,1,1,1,1),(1,53,1,1,1,1,1),(1,54,1,1,1,1,1),(1,55,1,1,1,1,1),(1,56,1,1,1,1,1),(1,57,1,1,1,1,1),(1,58,1,1,1,1,1),(1,59,1,1,1,1,1),(1,60,1,1,1,1,1),(1,61,1,1,1,1,1),(1,62,1,1,1,1,1),(1,63,1,1,1,1,1),(1,64,1,1,1,1,1),(1,65,1,1,1,1,1),(1,66,1,1,1,1,1),(1,67,1,1,1,1,1),(1,68,1,1,1,1,1),(1,69,1,1,1,1,1),(1,70,1,1,1,1,1),(1,71,1,1,1,1,1),(1,72,1,1,1,1,1),(1,73,1,1,1,1,1),(1,74,1,1,1,1,1),(1,75,1,1,1,1,1),(1,76,1,1,1,1,1),(1,77,1,1,1,1,1),(1,78,1,1,1,1,1),(1,79,1,1,1,1,1),(1,80,1,1,1,1,1),(1,86,1,1,1,1,1),(1,87,1,1,1,1,1),(1,88,1,1,1,1,1),(1,89,1,1,1,1,1),(1,91,1,1,1,1,1),(1,91,1,1,1,1,1),(1,92,1,1,1,1,1),(1,93,1,1,1,1,1),(1,94,1,1,1,1,1),(1,95,1,1,1,1,1),(1,96,1,1,1,1,1),(1,97,1,1,1,1,1),(1,98,1,1,1,1,1),(1,99,1,1,1,1,1),(1,101,1,1,1,1,1),(1,101,1,1,1,1,1),(1,102,1,1,1,1,1),(1,103,1,1,1,1,1),(1,104,1,1,1,1,1),(1,105,1,1,1,1,1),(1,106,1,1,1,1,1),(1,107,1,1,1,1,1),(1,108,1,1,1,1,1),(1,98,1,1,1,1,1),(1,90,1,1,1,1,1);
/*!40000 ALTER TABLE `user_rights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usermst`
--

DROP TABLE IF EXISTS `usermst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usermst` (
  `USER_ID` decimal(2,0) NOT NULL,
  `USER_NAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `EMAIL_ID` varchar(100) DEFAULT NULL,
  `IS_ACTIVE` decimal(1,0) DEFAULT '1',
  `USER_GRP_CD` bigint(20) DEFAULT '0',
  `BRANCH_CD` bigint(20) DEFAULT '0',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usermst`
--

LOCK TABLES `usermst` WRITE;
/*!40000 ALTER TABLE `usermst` DISABLE KEYS */;
INSERT INTO `usermst` VALUES (1,'','','',1,1,0);
/*!40000 ALTER TABLE `usermst` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-05 21:01:48
CREATE DATABASE  IF NOT EXISTS `skablelogindb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `skablelogindb`;
-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 52.66.132.178    Database: skablelogindb
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company_mst`
--

DROP TABLE IF EXISTS `company_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_mst` (
  `AC_YEAR` varchar(4) NOT NULL,
  `CMPN_NAME` varchar(100) NOT NULL,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDRESS3` varchar(255) DEFAULT NULL,
  `CITY` varchar(50) DEFAULT NULL,
  `PINCODE` varchar(6) DEFAULT NULL,
  `PHONE_NO` varchar(15) DEFAULT NULL,
  `FAX_NO` varchar(15) DEFAULT NULL,
  `CST_NO` varchar(20) DEFAULT NULL,
  `TIN_NO` varchar(20) DEFAULT NULL,
  `PAN_NO` varchar(10) DEFAULT NULL,
  `SH_NAME` varchar(2) DEFAULT NULL,
  `EMAILID` varchar(100) DEFAULT NULL,
  `CASH_AC_CD` varchar(7) DEFAULT NULL,
  `KASAR_AC` varchar(7) DEFAULT NULL,
  `COUNTRY` varchar(10) DEFAULT NULL,
  `PROVIANCE` varchar(25) DEFAULT NULL,
  `PURCHASE_AC` varchar(7) DEFAULT NULL,
  `SALES_AC` varchar(7) DEFAULT NULL,
  `PURCHASE_RETURN_AC` varchar(7) DEFAULT NULL,
  `SALES_RETURN_AC` varchar(7) DEFAULT NULL,
  `BUY_BACK_AC` varchar(7) DEFAULT NULL,
  `URD_PURCHASE` varchar(7) DEFAULT NULL,
  `DISC_AC` varchar(7) DEFAULT NULL,
  `gst_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AC_YEAR`,`CMPN_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_mst`
--

LOCK TABLES `company_mst` WRITE;
/*!40000 ALTER TABLE `company_mst` DISABLE KEYS */;
INSERT INTO `company_mst` VALUES ('2017','MANAV MANDIR','23, M.R.S HIGH SCHOOL BUILDING','OPP NEW GANJ BAZAR GATE-2','STATION ROAD','UNJHA','384170',' ',' ',' ',' ',' ','MM',' ','A7G0001','A7G0002',NULL,NULL,'A7G0003','A7G0004','A7G0005','A7G0006','A7G0007','A7G0008','A7G0009','APPLIED FOR');
/*!40000 ALTER TABLE `company_mst` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-05 21:01:51
