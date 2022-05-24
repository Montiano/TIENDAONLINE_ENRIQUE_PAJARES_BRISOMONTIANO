CREATE DATABASE  IF NOT EXISTS `tienda_enrique_pajares_brisomontiano` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tienda_enrique_pajares_brisomontiano`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tienda_enrique_pajares_brisomontiano
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
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cif` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` VALUES (1,'A31879851','C/ Cortinas San Miguel 10','Zamora','Serbatic','Zamora','918966400');
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalles_pedido`
--

DROP TABLE IF EXISTS `detalles_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `precio_unidad` double DEFAULT NULL,
  `total` double NOT NULL,
  `unidades` int NOT NULL,
  `pedido_id` bigint DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4qmqlxyy78kjl4ec4wjnfmggu` (`pedido_id`),
  KEY `FK8144uqs26ce7usdnqb1aml16` (`producto_id`),
  CONSTRAINT `FK4qmqlxyy78kjl4ec4wjnfmggu` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`),
  CONSTRAINT `FK8144uqs26ce7usdnqb1aml16` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_pedido`
--

LOCK TABLES `detalles_pedido` WRITE;
/*!40000 ALTER TABLE `detalles_pedido` DISABLE KEYS */;
INSERT INTO `detalles_pedido` VALUES (1,'Samsung Galaxy Book Go',401.64,401.64,1,1,1),(2,'Asus Chromebook Flip Z3400FT',299,299,1,2,10),(3,'PcCom Revolt One 3060',1299,1299,1,3,14),(4,'HP Z3700',18.31,18.31,1,3,23),(5,'HP X27c ',219,219,1,3,20),(6,'Tempest GHS100 Warrior',14.99,14.99,1,3,28),(7,'Samsung Galaxy Book Go',401.64,401.64,1,4,1),(8,'MSI MAG META S',859.98,859.98,1,5,15),(9,'HP X27qc',329,329,1,5,3),(10,'Razer Viper Ultimate',124.79,124.79,1,5,26),(11,'Razer Blackshark V2 Special Edition',102.17,102.17,1,5,27),(12,'HP OMEN Sequencer',189.95,189.95,1,5,6),(13,'Gigabyte GeForce RTX 3060',459.9,2299.5,5,6,9),(14,'PcCom Gold Elite',1114.47,1114.47,1,7,2),(15,'HP OMEN Sequencer',189.95,189.95,1,7,6),(16,'Razer Viper Ultimate',124.79,124.79,1,7,26),(17,'Razer Blackshark V2 Special Edition',102.17,102.17,1,7,27),(18,'Newskill Icarus RGB IC27QRC',269.99,269.99,1,7,21);
/*!40000 ALTER TABLE `detalles_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `num_factura` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5g0es69v35nmkmpi8uewbphs2` (`usuario_id`),
  CONSTRAINT `FK5g0es69v35nmkmpi8uewbphs2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (1,'E','2022-05-23','tarjeta','FRA.2022-05-23T21:11:38.466',401.64,3),(2,'C','2022-05-23','tarjeta','S/N',299,3),(3,'PE','2022-05-23','paypal',NULL,1551.3,3),(4,'PC','2022-05-23','tarjeta',NULL,401.64,2),(5,'PE','2022-05-23','paypal',NULL,1605.89,2),(6,'E','2022-05-23','paypal','FRA.2022-05-23T21:12:47.854',2299.5,2),(7,'PE','2022-05-24','tarjeta',NULL,1801.37,2);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_alta` date DEFAULT NULL,
  `fecha_baja` date DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `impuesto` float NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int NOT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo8g0kqq9awvgh4elqai7tdhu` (`usuario_id`),
  CONSTRAINT `FKo8g0kqq9awvgh4elqai7tdhu` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'portatiles',' Qualcomm Snapdragon 7c Gen2 4GB 128GB 14\" Plata','2022-05-23',NULL,'1528-samsung-galaxy-book-go-qualcomm-snapdragon-7c-gen-2-4gb-128gb-14-plata-676060b3-5463-4e57-ab77-17dc090c5c68.png',0.21,'Samsung Galaxy Book Go',401.64,446,1),(2,'torres',' Intel Core i5 11400F 16GB 500GBSSD RTX3060','2022-05-23',NULL,'1354-pccom-gold-elite-intel-core-i5-11400f-16gb-500gbssd-rtx3060-comprar.png',0.21,'PcCom Gold Elite',1114.47,256,1),(3,'monitores',' LED QHD 165Hz FreeSync Premium Curva','2022-05-23',NULL,'1825-hp-x27qc-27-led-qhd-165hz-freesync-premium-curva.png',0.21,'HP X27qc',329,544,1),(4,'ratones',' Ratón Gaming 7200 DPI Negro','2022-05-23',NULL,'1628-hp-omen-vector-essential-raton-gaming-7200-dpi-negro.png',0.21,'HP OMEN Vector Essential',32.81,682,1),(5,'auriculares',' Auriculares Gaming 7.1 PC PS4 PS5 Xbox','2022-05-23',NULL,'119-forgeon-sergeant-auriculares-gaming-wireless-pc-ps4-ps5-xbox-xbox-x-switch-negros-comprar.png',0.21,'Forgeon Sergeant',69.99,426,1),(6,'teclados',' Teclado mecánico gaming RGB Switch blue','2022-05-23',NULL,'57430588-0222334092.png',0.21,'HP OMEN Sequencer',189.95,5,1),(7,'otros',' Impresora multifunción láser monocromo wifi blanca','2022-05-23',NULL,'1609-hp-laserjet-m140w-impresora-multifuncion-laser-monocromo-wifi-blanca.png',0.21,'HP Laserjet M140w',208.99,4,1),(8,'otros',' Disco duro 500GB SSD M.2 2280 PCle Gen3 x4 NVM3','2022-05-23',NULL,'1398-crucial-p2-500gb-ssd-m2-2280-pcie-gen3-x4-nvme.png',0.21,'Crucial P2',59.33,480,1),(9,'otros',' Tarjeta gráfica RTX 3060 Gaming OC 12GB GDDR6 Rev 2.0','2022-05-23',NULL,'1343-gigabyte-geforce-rtx-3060-gaming-oc-12gb-gddr6-rev-20.png',0.21,'Gigabyte GeForce RTX 3060',459.9,185,1),(10,'portatiles',' Intel Core m3-8100Y 8GB 64GB eMMC 14\" Táctil','2022-05-23',NULL,'asus-chromebook-flip-z3400ft-intel-core-m3-8100y-8gb-64gb-emmc-14-tactil-b0b45798-4a8d-4649-8492-0cd351467a11.png',0.21,'Asus Chromebook Flip Z3400FT',299,466,1),(11,'portatiles',' Intel Core i5-1235U 8GB 256GB SSD 15.6\"','2022-05-23',NULL,'1795-samsung-galaxy-book2-intel-core-i5-1235u-8gb-256gb-ssd-156.png',0.21,'Samsung Galaxy Book2',749,560,1),(12,'portatiles','Intel Core i7-11800H 16GB 1TB SSD RTX 3060 16.1\"','2022-05-23',NULL,'1752-hp-omen-16-b0044ns-intel-core-i7-11800h-16gb-1tb-ssd-rtx-3060-161.png',0.21,'HP OMEN 16-b0044ns ',1499,386,1),(13,'portatiles',' AMD Ryzen 5 5600H 8GB 512GB SSD RTX3060 15.6\"','2022-05-23',NULL,'1134-acer-nitro-5-an515-45-r4cs-amd-ryzen-5-5600h-8gb-512gb-ssd-rtx-3060-156.png',0.21,'Acer Nitro 5 AN515-45-R4CS',1098.99,99,1),(14,'portatiles',' Intel Core i7-11800H 16GB 1TB SSD RTX 3060 15.6\"','2022-05-23',NULL,'1172-pccom-revolt-one-3060-intel-core-i7-11800h-16gb-1tb-ssd-rtx-3060-156-opiniones.png',0.21,'PcCom Revolt One 3060',1299,862,1),(15,'torres',' 5SI-030XES AMD Ryzen 5 3600 16GB 512GB SSD GTX1660 Super','2022-05-23',NULL,'1639-msi-mag-meta-s-5si-030xes-amd-ryzen-5-3600-16gb-512gb-ssd-gtx-1660-super.png',0.21,'MSI MAG META S',859.98,254,1),(16,'torres',' AMD Ryzen 5 4600G 8GB 256GB SSD','2022-05-23',NULL,'1879-huawei-matestation-b515-amd-ryzen-5-4600g-8gb-256gb-ssd.png',671.93,'Huawei MateStation B515',168,386,1),(17,'torres','Intel Core i7-10700F 16 GB 1TB + 512GB SSD RTX3060','2022-05-23',NULL,'167-hp-pavilion-gaming-tg01-1107ns-intel-core-i7-10700f-16gb-1tb-512gb-ssd-rtx-3060ti.jpg',0.21,'HP Pavilion Gaming TG01-1107ns',1099.99,51,1),(18,'torres',' Intel Core i5-11400F 16GB 500GB GTX1650','2022-05-23',NULL,'1482-pccom-silver-intel-core-i5-11400f-16gb-500gb-gtx1650-windows-11-home-comprar.png',0.21,'PcCom Silver ',995.61,353,1),(19,'monitores',' Monitor 23.6\" LED FullHD 165Hz FreeSync Curvo','2022-05-23',NULL,'1606-pccom-discovery-236-led-fullhd-165hz-freesync-curvo.png',0.21,'PcCom Discovery ',169.98,18,1),(20,'monitores',' Monitor LED FullHD 165Hz FreeSync Premium Curva','2022-05-23',NULL,'1823-hp-x27c-27-led-fullhd-165hz-freesync-premium-curva.png',0.21,'HP X27c ',219,633,1),(21,'monitores',' Monitor 27\" LED QuadHD 165Hz G-Sync Compatible','2022-05-23',NULL,'1657-newskill-icarus-rgb-ic27qrc-27-led-quadhd-165hz-g-sync-compatible.png',0.21,'Newskill Icarus RGB IC27QRC',269.99,72,1),(22,'monitores',' Monitor 23.8\" LED IPS FullHD 75Hz Freesync USB-C','2022-05-23',NULL,'1349-hp-m24fd-238-led-ips-fullhd-freesync-75hz-usb-c.png',0.21,'HP M24fd ',248.99,878,1),(23,'ratones',' Ratón inalámbrico blanco','2022-05-23',NULL,'l1.png',0.21,'HP Z3700',18.31,866,1),(24,'ratones',' Ratón Gaming 4000 DPI negro','2022-05-23',NULL,'1544-tempest-ms-300-rgb-soldier-raton-gaming-4000dpi.png',0.21,'Tempest MS300 Soldier RGB',6.99,986,1),(25,'ratones',' Ratón inalámbrico 1600 DPI gris','2022-05-23',NULL,'1391-owlotech-m80-raton-inalambrico-1600-dpi-gris.png',0.21,'Owlotech M80',6.49,642,1),(26,'ratones',' Ratón Gaming inalámbrico 20000DPI','2022-05-23',NULL,'1505-razer-viper-ultimate-raton-gaming-inalambrico-20000-dpi.png',0.21,'Razer Viper Ultimate',124.79,20,1),(27,'auriculares',' Auriculares Gaming multiplataforma','2022-05-23',NULL,'1865-razer-blackshark-v2-auriculares-gaming-edicion-especial-multiplataforma.png',0.21,'Razer Blackshark V2 Special Edition',102.17,561,1),(28,'auriculares','Auriculares Gaming RGB','2022-05-23',NULL,'1301-tempest-warrior-auriculares-gaming-rgb-especificaciones.png',0.21,'Tempest GHS100 Warrior',14.99,364,1),(29,'auriculares',' Auriculares inalámbricos blancos','2022-05-23',NULL,'photo-1.png',0.21,'Owlotech Ear Twins',14.99,699,1),(30,'auriculares',' Auriculares Gaming','2022-05-23',NULL,'1985-aoc-gh200-auriculares-gaming.png',0.21,'AOC GH200',39.3,247,1),(31,'otros',' Gaming teclado + ratón + auriculares + alfombrilla','2022-05-23',NULL,'132-tempest-apocalypse-combo-gaming-teclado-raton-auriculares-alfombrilla-comprar.png',0.21,'Tempest Apocalypse Combo',30,877,1),(32,'teclados',' Teclado mecánico gaming inalámbrico cherry MX brown','2022-05-23',NULL,'1276-hp-omen-spacer-tkl-teclado-mecanico-gaming-inalambrico-cherry-mx-brown.png',0.21,'HP OMEN Spacer TKL',158.98,560,1),(33,'teclados',' Teclado bluetooth con touchpad','2022-05-23',NULL,'1280-owlotech-k500tv-teclado-bluetooth-con-touchpad.png',0.21,'Owlotech K500TV',18.98,501,1),(34,'teclados',' Teclado Gaming retroiluminado yellow switch','2022-05-23',NULL,'1132-razer-blackwidow-v3-tenkeyless-teclado-gaming-retroiluminado-yellow-switch.png',0.21,'Razer BlackWidow V3 Tenkeyless',71.52,239,1),(35,'teclados',' Teclado Gaming RGB 60% Switch Blue','2022-05-23',NULL,'1296-forgeon-clutch-teclado-gaming-rgb-60-switch-blue-comprar.png',0.21,'Forgeon Clutch ',99.99,124,1),(36,'otros',' Producto de prueba sin foto','2022-05-23',NULL,'producto.png',0.21,'Producto de prueba sin foto',1,4,1);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER'),(3,'EMPLOYEE');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido1` varchar(255) DEFAULT NULL,
  `apellido2` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `rol_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kfsp0s1tflm1cwlj8idhqsad0` (`email`),
  KEY `FKqf5elo4jcq7qrt83oi0qmenjo` (`rol_id`),
  CONSTRAINT `FKqf5elo4jcq7qrt83oi0qmenjo` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','admin','admin','admin','admin@admin','admin','admin','$2a$10$ESX/l72icPIb5MjjbitRReTGGsfUiRFR1FHmGnk7W6YR7n6e6Wwcy','admin','666666666','ADMIN',1),(2,'Pajares','Briso-Montiano','C/Tahonas 1','12415042L','kike_montiano@hotmail.com','Valladolid','Enrique','$2a$10$C6/r/Feh9iJC9IzZOalIo.ZB4OKdfTjNFMln1IitP1eM3ma.Qbor2','Valladolid','696680508','USER',2),(3,'Escudero','López','C/Ultramar 7','71548764J','anaes@gmail.com','Valladolid','Anabel','$2a$10$sHMXnA431XhGzsmP9Xqpxe.GTAgrVRlad0sdsWgrsCgbziC5cg/Aa','Valladolid','654897125','USER',2),(4,'García','Alonso','C/Rosa 10','12547865T','gonga@gmail.com','Palencia','Gonzalo','$2a$10$p/88DJVq.77bFPeNV59fge04k.0Jnp6yskif/0dCoRwzoDh.tYUSe','Palencia','654792014','USER',2),(5,'Sanz','Díez','C/Piruleta 33','71548264F','sarsa@gmail.com','Burgo de Osma','Sara','$2a$10$GNlLQn6dtwgbfP30pj42UeN4Ord6C28e2Tn9put9H1fgwcj4C.xI.','Soria','654780514','USER',2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-24 20:40:04
