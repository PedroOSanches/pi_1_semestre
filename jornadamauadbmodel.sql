-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: pi-gameficacao-db-pedrohenriqueoliveirasanches3-0b27.i.aivencloud.com    Database: jornadamauadb
-- ------------------------------------------------------
-- Server version	8.4.8

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET
@@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '051cd0db-5503-11f1-b4df-420ceaa35c25:1-199,
53843901-4d28-11f1-b1f4-76f7b9ecc862:1-31,
ebee13f3-4dfb-11f1-a16b-c202e0cec888:1-39';

--
-- Table structure for table `alternativa`
--

DROP TABLE IF EXISTS `alternativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alternativa` (
  `id_alternativa` int NOT NULL AUTO_INCREMENT,
  `id_questao` int NOT NULL,
  `correta` int NOT NULL DEFAULT '0',
  `texto_alternativa` varchar(250) NOT NULL,
  PRIMARY KEY (`id_alternativa`,`id_questao`),
  KEY `alternativa_questao_idx` (`id_questao`),
  CONSTRAINT `alternativa_questao` FOREIGN KEY (`id_questao`) REFERENCES `questao` (`id_questao`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ano`
--

DROP TABLE IF EXISTS `ano`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ano` (
  `id_ano` int NOT NULL AUTO_INCREMENT,
  `ano` year NOT NULL,
  PRIMARY KEY (`id_ano`),
  UNIQUE KEY `ano_UNIQUE` (`ano`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `casa`
--

DROP TABLE IF EXISTS `casa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `casa` (
  `id_casa` int NOT NULL AUTO_INCREMENT,
  `id_secao` int NOT NULL,
  `titulo_casa` varchar(45) NOT NULL,
  `data_limite_casa` datetime NOT NULL,
  PRIMARY KEY (`id_casa`),
  KEY `casa_pertence_sessao_idx` (`id_secao`),
  CONSTRAINT `casa_pertence_secao` FOREIGN KEY (`id_secao`) REFERENCES `secao` (`id_secao`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `id_curso` int NOT NULL AUTO_INCREMENT,
  `nome_curso` varchar(100) NOT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dissertativa`
--

DROP TABLE IF EXISTS `dissertativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dissertativa` (
  `resposta_modelo_dissertativa` varchar(250) NOT NULL,
  `id_questao` int NOT NULL,
  PRIMARY KEY (`id_questao`),
  KEY `dissertativa_pertence_questao_idx` (`id_questao`),
  CONSTRAINT `dissertativa_pertence_questao` FOREIGN KEY (`id_questao`) REFERENCES `questao` (`id_questao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `questao`
--

DROP TABLE IF EXISTS `questao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questao` (
  `id_questao` int NOT NULL AUTO_INCREMENT,
  `id_tarefa` int NOT NULL,
  `tipo_questao` enum('alternativa','dissertativa','upload') NOT NULL,
  `enunciado_questao` varchar(250) NOT NULL,
  PRIMARY KEY (`id_questao`),
  KEY `questao_pertence_tarefa_idx` (`id_tarefa`),
  CONSTRAINT `questao_pertence_tarefa` FOREIGN KEY (`id_tarefa`) REFERENCES `tarefa` (`id_tarefa`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resposta`
--

DROP TABLE IF EXISTS `resposta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resposta` (
  `id_resposta` int NOT NULL AUTO_INCREMENT,
  `id_tentativa` int NOT NULL,
  `id_questao` int NOT NULL,
  `nota_resposta` decimal(4,2) DEFAULT NULL,
  `feedback_resposta` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id_resposta`),
  KEY `resposta_questao_tentativa_idx` (`id_tentativa`),
  KEY `resposta_questao_idx` (`id_questao`),
  CONSTRAINT `resposta_questao` FOREIGN KEY (`id_questao`) REFERENCES `questao` (`id_questao`),
  CONSTRAINT `resposta_questao_tentativa` FOREIGN KEY (`id_tentativa`) REFERENCES `tentativa` (`id_tentativa`)
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resposta_alternativa`
--

DROP TABLE IF EXISTS `resposta_alternativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resposta_alternativa` (
  `id_resposta` int NOT NULL,
  `id_alternativa` int NOT NULL,
  PRIMARY KEY (`id_resposta`),
  KEY `alternativa_selecionada_idx` (`id_alternativa`),
  KEY `alternativa_selecionada_resposta_idx` (`id_alternativa`) /*!80000 INVISIBLE */,
  CONSTRAINT `alternativa_pertence_resposta` FOREIGN KEY (`id_resposta`) REFERENCES `resposta` (`id_resposta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resposta_dissertativa`
--

DROP TABLE IF EXISTS `resposta_dissertativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resposta_dissertativa` (
  `id_resposta` int NOT NULL,
  `resposta` text NOT NULL,
  PRIMARY KEY (`id_resposta`),
  CONSTRAINT `dissertativa_responde` FOREIGN KEY (`id_resposta`) REFERENCES `resposta` (`id_resposta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resposta_upload`
--

DROP TABLE IF EXISTS `resposta_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resposta_upload` (
  `id_resposta` int NOT NULL,
  `arquivo_resposta` varchar(250) NOT NULL,
  PRIMARY KEY (`id_resposta`),
  CONSTRAINT `upload_pertence_resposta` FOREIGN KEY (`id_resposta`) REFERENCES `resposta` (`id_resposta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secao`
--

DROP TABLE IF EXISTS `secao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secao` (
  `id_secao` int NOT NULL AUTO_INCREMENT,
  `titulo_secao` varchar(100) NOT NULL,
  `descricao_secao` text NOT NULL,
  PRIMARY KEY (`id_secao`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subturma`
--

DROP TABLE IF EXISTS `subturma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subturma` (
  `id_subturma` int NOT NULL AUTO_INCREMENT,
  `cod_subturma` varchar(5) NOT NULL,
  PRIMARY KEY (`id_subturma`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tarefa`
--

DROP TABLE IF EXISTS `tarefa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarefa` (
  `id_tarefa` int NOT NULL AUTO_INCREMENT,
  `titulo_tarefa` varchar(45) NOT NULL,
  `id_casa` int NOT NULL,
  `prazo_tarefa` date NOT NULL,
  PRIMARY KEY (`id_tarefa`),
  KEY `tarefa_pertence_casa_idx` (`id_casa`),
  CONSTRAINT `tarefa_pertence_casa` FOREIGN KEY (`id_casa`) REFERENCES `casa` (`id_casa`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`avnadmin`@`%`*/ /*!50003 TRIGGER `tarefa_BEFORE_INSERT` BEFORE INSERT ON `tarefa` FOR EACH ROW BEGIN
	IF NEW.prazo_tarefa < NOW() THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'O prazo da tarefa deve ser maior ou igual a data de hoje';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tentativa`
--

DROP TABLE IF EXISTS `tentativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tentativa` (
  `id_tentativa` int NOT NULL AUTO_INCREMENT,
  `status_tentativa` enum('pendente','concluida','corrigida') NOT NULL DEFAULT 'pendente',
  `data_tentativa` timestamp NOT NULL,
  `id_usuario` int NOT NULL,
  `id_tarefa` int NOT NULL,
  PRIMARY KEY (`id_tentativa`),
  KEY `usuario_tentou_idx` (`id_usuario`),
  KEY `tentativa_responde_tarefa_idx` (`id_tarefa`),
  CONSTRAINT `tentativa_responde_tarefa` FOREIGN KEY (`id_tarefa`) REFERENCES `tarefa` (`id_tarefa`),
  CONSTRAINT `usuario_tentou` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turma`
--

DROP TABLE IF EXISTS `turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turma` (
  `id_turma` int NOT NULL AUTO_INCREMENT,
  `cod_turma` varchar(3) NOT NULL,
  PRIMARY KEY (`id_turma`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turma_subturma`
--

DROP TABLE IF EXISTS `turma_subturma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turma_subturma` (
  `id_turma_subturma` int NOT NULL AUTO_INCREMENT,
  `id_turma` int NOT NULL,
  `id_subturma` int NOT NULL,
  `id_curso` int NOT NULL,
  `id_ano` int NOT NULL,
  `semestre_turma_subturma` enum('primeiro','segundo') NOT NULL,
  PRIMARY KEY (`id_turma_subturma`),
  KEY `fk_turma_subturma_turma1_idx` (`id_turma`),
  KEY `fk_turma_subturma_subturma1_idx` (`id_subturma`),
  KEY `fk_turma_subturma_Curso1_idx` (`id_curso`),
  KEY      `fk_tuma_subturma_ano_idx` (`id_ano`),
  CONSTRAINT `fk_tuma_subturma_ano` FOREIGN KEY (`id_ano`) REFERENCES `ano` (`id_ano`),
  CONSTRAINT `fk_turma_subturma_Curso1` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id_curso`),
  CONSTRAINT `fk_turma_subturma_subturma1` FOREIGN KEY (`id_subturma`) REFERENCES `subturma` (`id_subturma`),
  CONSTRAINT `fk_turma_subturma_turma1` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id_turma`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turma_usuario`
--

DROP TABLE IF EXISTS `turma_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turma_usuario`
(
    `id_usuario`        int NOT NULL,
    `id_turma_subturma` int NOT NULL,
    PRIMARY KEY (`id_usuario`, `id_turma_subturma`),
    KEY                 `fk_usuario_turma_usuario_idx` (`id_usuario`),
    KEY                 `fk_turma_subturma_turma_usuario_idx` (`id_turma_subturma`),
    CONSTRAINT `fk_turma_subturma_turma_usuario` FOREIGN KEY (`id_turma_subturma`) REFERENCES `turma_subturma` (`id_turma_subturma`),
    CONSTRAINT `fk_usuario_turma_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `upload`
--

DROP TABLE IF EXISTS `upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upload` (
  `id_questao` int NOT NULL,
  `titulo_upload` varchar(100) NOT NULL,
  `arquivo_modelo_upload` varchar(250) NOT NULL COMMENT 'Mascara TTT01_00_upload.pdf',
  PRIMARY KEY (`id_questao`),
  CONSTRAINT `upload_questao` FOREIGN KEY (`id_questao`) REFERENCES `questao` (`id_questao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome_usuario` varchar(100) NOT NULL,
  `sobrenome_usuario` varchar(100) NOT NULL,
  `username_usuario` varchar(100) NOT NULL,
  `senha_usuario` varbinary(128) NOT NULL,
  `tipo_usuario` enum('professor','aluno') NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'jornadamauadb'
--

--
-- Dumping routines for database 'jornadamauadb'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-04  0:17:37
