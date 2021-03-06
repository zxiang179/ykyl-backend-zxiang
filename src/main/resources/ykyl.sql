/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.40 : Database - ykyl
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ykyl` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ykyl`;

/*Table structure for table `t_answer` */

DROP TABLE IF EXISTS `t_answer`;

CREATE TABLE `t_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `an_question_id` bigint(20) DEFAULT NULL,
  `an_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_btwu8hnreklemfv864qjd30v6` (`an_question_id`),
  KEY `FK_2ohvsft823apvl07id73ex0uc` (`an_user_id`),
  CONSTRAINT `FK_2ohvsft823apvl07id73ex0uc` FOREIGN KEY (`an_user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_btwu8hnreklemfv864qjd30v6` FOREIGN KEY (`an_question_id`) REFERENCES `t_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_answer` */

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_detail` varchar(50) DEFAULT NULL,
  `question_ans_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `zan` int(11) DEFAULT NULL,
  `grade` varchar(50) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `cme_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bsurrhdoklqg4t7l4d6e1y8ov` (`question_ans_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `t_comment` */

/*Table structure for table `t_grade` */

DROP TABLE IF EXISTS `t_grade`;

CREATE TABLE `t_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_grade` */

/*Table structure for table `t_knowledge_point` */

DROP TABLE IF EXISTS `t_knowledge_point`;

CREATE TABLE `t_knowledge_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kp_name` varchar(255) DEFAULT NULL,
  `kp_shortcut` varchar(255) DEFAULT NULL,
  `kp_subject_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_58yd661plrqb3wktgnk97y7jw` (`kp_subject_id`),
  CONSTRAINT `FK_58yd661plrqb3wktgnk97y7jw` FOREIGN KEY (`kp_subject_id`) REFERENCES `t_subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `t_knowledge_point` */

insert  into `t_knowledge_point`(`id`,`kp_name`,`kp_shortcut`,`kp_subject_id`) values (1,'加减乘除','jjcc',2),(2,'平面几何','pmjh',2),(3,'几何小实践','jhxsj',2),(4,'时间计算','sjjs',2),(5,'角的度量','jddl',2),(6,'大数和凑整','dshcz',2),(7,'符号表示数','fhbss',2),(8,'小数的四则运算','xsdszys',2),(9,'平均数','pjs',2),(10,'简易方程','jyfc',2),(11,'古文练习','gwlx',1),(12,'现代文阅读理解','xdwydlj',1),(13,'诗歌赏析','sgsx',1),(14,'完形填空','wxtk',3),(15,'看图作文','ktzw',3);

/*Table structure for table `t_label` */

DROP TABLE IF EXISTS `t_label`;

CREATE TABLE `t_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lb_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_label` */

/*Table structure for table `t_option` */

DROP TABLE IF EXISTS `t_option`;

CREATE TABLE `t_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `op_code` varchar(255) DEFAULT NULL,
  `op_content` varchar(255) DEFAULT NULL,
  `op_question_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_r9uj1v1a0do0t8er5o4kdmg2h` (`op_question_id`),
  CONSTRAINT `FK_r9uj1v1a0do0t8er5o4kdmg2h` FOREIGN KEY (`op_question_id`) REFERENCES `t_question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=637 DEFAULT CHARSET=utf8;

/*Data for the table `t_option` */

insert  into `t_option`(`id`,`op_code`,`op_content`,`op_question_id`) values (1,'A','3÷3	',1),(2,'B','3＋3	',1),(3,'C','3×3	',1),(4,'D','3-3	',1),(5,'A','小胖做的朵数是小亚的几倍	',2),(6,'B','小亚做的朵数是小胖做的几倍	',2),(7,'C','小胖和小亚一共做了多少朵花	',2),(8,'D','小胖和小亚做的花相差了多少朵	',2),(9,'A','2个	',3),(10,'B','3个	',3),(11,'C','4个	',3),(12,'D','5个	',3),(13,'A','36、18、54、72	',4),(14,'B','42、36、45、9	',4),(15,'C','81、0、18、72	',4),(16,'D','6、9、27、90	',4),(17,'A','13除3等于4	',5),(18,'B','13除以3等于4余1	',5),(19,'C','13除3等于4余1	',5),(20,'D','13除以3等于4	',5),(21,'A','5×10=50	',6),(22,'B','10×5=50	',6),(23,'C','50÷5=10	',6),(24,'D','50÷10=5	',6),(25,'A','8＋2	',7),(26,'B','3×6	',7),(27,'C','2×9	',7),(28,'D','4×4	',7),(29,'A','7个4	',8),(30,'B','7乘4	',8),(31,'C','4乘7	',8),(32,'D','7加4	',8),(33,'A','8的4倍是多少？	',9),(34,'B','8是4的几倍？	',9),(35,'C','把8平均分成4份，每份是几？	',9),(36,'D','8和4的关系	',9),(37,'A','15＋14	',10),(38,'B','38＋10	',10),(39,'C','6×6	',10),(40,'D','1+40	',10),(41,'A','3×3	',11),(42,'B','2×3	',11),(43,'C','2＋3	',11),(44,'D','3*4	',11),(45,'A','2个	',12),(46,'B','3个	',12),(47,'C','不确定	',12),(48,'D','4个	',12),(49,'A','3.0	',13),(50,'B','6.0	',13),(51,'C','9.0	',13),(52,'D','12.0	',13),(53,'A','89.0	',14),(54,'B','90.0	',14),(55,'C','99.0	',14),(56,'D','100.0	',14),(57,'A','16个	',15),(58,'B','24个	',15),(59,'C','25个	',15),(60,'D','26个	',15),(61,'A','99.0	',16),(62,'B','89.0	',16),(63,'C','90.0	',16),(64,'D','109.0	',16),(65,'A','15.0	',17),(66,'B','20.0	',17),(67,'C','30.0	',17),(68,'D','49.0	',17),(69,'A','8袋	',18),(70,'B',' 9袋	',18),(71,'C','10袋	',18),(72,'D','无法确定	',18),(73,'A','8.0	',19),(74,'B','9.0	',19),(75,'C','10.0	',19),(76,'D','11.0	',19),(77,'A','800÷2	',20),(78,'B','800×2	',20),(79,'C',' 800÷2＋800	',20),(80,'D','800×2＋800	',20),(81,'A','1800.0	',21),(82,'B','240.0	',21),(83,'C','360.0	',21),(84,'D','300.0	',21),(85,'A','280.0	',22),(86,'B','630.0	',22),(87,'C','560.0	',22),(88,'D','490.0	',22),(89,'A','10×△=☆	',23),(90,'B','100×☆=△	',23),(91,'C','100×△=☆	',23),(92,'D','☆=△	',23),(93,'A','2.0	',24),(94,'B','3.0	',24),(95,'C','4.0	',24),(96,'D','5.0	',24),(97,'A','86－38－29	',25),(98,'B','86－(38＋29)	',25),(99,'C','38＋29	',25),(100,'D','38－29	',25),(101,'A','4除56	',26),(102,'B','56除4	',26),(103,'C','56除以4	',26),(104,'D','56和4的商	',26),(105,'A','大	',27),(106,'B','小	',27),(107,'C','不能确定	',27),(108,'D','不知道	',27),(109,'A','119.0	',28),(110,'B','112.0	',28),(111,'C','91.0	',28),(112,'D','113.0	',28),(113,'A','加/减/乘/除	',29),(114,'B','除/加/减/乘	',29),(115,'C','乘/除/加/减	',29),(116,'D','加/除/减/乘	',29),(117,'A','交换律	',30),(118,'B','结合律	',30),(119,'C','分配律	',30),(120,'D','	',30),(121,'A','(8*3-16)/8  	',31),(122,'B','8*3-16/8	',31),(123,'C','8/8 * 3-16	',31),(124,'D','8/(8*3-16)	',31),(125,'A','200克		',32),(126,'B','216克	',32),(127,'C','225克	',32),(128,'D','270克	',32),(129,'A','2.0	',33),(130,'B','3.0	',33),(131,'C','4.0	',33),(132,'D','5.0	',33),(133,'A','3.0	',34),(134,'B','4.0	',34),(135,'C','5.0	',34),(136,'D','6.0	',34),(137,'A','直角三角形	',35),(138,'B','钝角三角形	',35),(139,'C','锐角三角形	',35),(140,'D','以上三种情况都有可能	',35),(141,'A','100.0	',36),(142,'B','10.0	',36),(143,'C','50.0	',36),(144,'D','25.0	',36),(145,'A','0条	',37),(146,'B','1条	',37),(147,'C','2条	',37),(148,'D','3条	',37),(149,'A','锐角三角形	',38),(150,'B','直角三角形	',38),(151,'C','钝角三角形	',38),(152,'D','无法确定	',38),(153,'A','6根一样长的小棒	',39),(154,'B','12根分3组每组4根长度相等的小棒	',39),(155,'C','12根一样长的小棒	',39),(156,'D','12根分2组每组6根长度相等的小棒	',39),(157,'A','正方体	',40),(158,'B','长方体	',40),(159,'C','长方体或正方体	',40),(160,'D','其他	',40),(161,'A','12条棱，8个顶点	',41),(162,'B','长方体的棱长不全都相等	',41),(163,'C','6个面都是正方形	',41),(164,'D','正方体是特殊的长方体	',41),(165,'A','2厘米	',42),(166,'B','8分米	',42),(167,'C','20厘米	',42),(168,'D','80厘米	',42),(169,'A','2.0	',43),(170,'B','4.0	',43),(171,'C','6.0	',43),(172,'D','8.0	',43),(173,'A','2厘米	',44),(174,'B','3厘米	',44),(175,'C','6厘米	',44),(176,'D','12厘米	',44),(177,'A','2.0	',45),(178,'B','3.0	',45),(179,'C','6.0	',45),(180,'D','12.0	',45),(181,'A','三角形	',46),(182,'B',' 正方形	',46),(183,'C','长方形	',46),(184,'D','平行四边形	',46),(185,'A','四个锐角三角形	',47),(186,'B','两个直角三角形和两个钝角三角形	',47),(187,'C','四个钝角三角形	',47),(188,'D','两个锐角三角形和两个钝角三角形	',47),(189,'A','1.0	',48),(190,'B','2.0	',48),(191,'C','4.0	',48),(192,'D','无数	',48),(193,'A','5×4÷3	',49),(194,'B','3×4÷5	',49),(195,'C','5×3÷4	',49),(196,'D','5×3×4	',49),(197,'A','三角形	',50),(198,'B','平行四边形	',50),(199,'C','长方形	',50),(200,'D','正方形	',50),(201,'A','等边三角形	',51),(202,'B','平行四边形	',51),(203,'C','长方形	',51),(204,'D','等腰梯形	',51),(205,'A','两个三角形	',52),(206,'B','一个平行四边形和一个三角形	',52),(207,'C','两个平行四边形	',52),(208,'D','两个直角梯形	',52),(209,'A','形状相同	',53),(210,'B','面积相等	',53),(211,'C','上底、下底、高都相等	',53),(212,'D','形状相同、面积相等	',53),(213,'A','a	',54),(214,'B','2a	',54),(215,'C',' 3a	',54),(216,'D','5a	',54),(217,'A','5cm和4.8cm	',55),(218,'B',' 3cm和16mm	',55),(219,'C',' 3.2cm和15cm	',55),(220,'D','9cm和6cm	',55),(221,'A','3倍	',56),(222,'B','9倍	',56),(223,'C','18倍	',56),(224,'D','27倍	',56),(225,'A','2厘米	',57),(226,'B',' 4厘米	',57),(227,'C','8厘米	',57),(228,'D','16厘米	',57),(229,'A','平行四边形容易变形	',58),(230,'B','平行四边形是轴对称图形	',58),(231,'C','三角形不容易变形		',58),(232,'D','直角梯形有两个直角	',58),(233,'A',' 24厘米	',59),(234,'B',' 12厘米	',59),(235,'C','18厘米	',59),(236,'D','36厘米	',59),(237,'A','183.0	',60),(238,'B','181.0	',60),(239,'C','182.0	',60),(240,'D','184.0	',60),(241,'A','31天，31天	',61),(242,'B','30天，30天	',61),(243,'C','30天，31天	',61),(244,'D','29天，30天	',61),(245,'A','可能会看到太阳	',62),(246,'B','一定能看到太阳	',62),(247,'C','不可能看到太阳	',62),(248,'D','不知道	',62),(249,'A','2600.0	',63),(250,'B','2700.0	',63),(251,'C','2800.0	',63),(252,'D','2900.0	',63),(253,'A','31天，31天	',64),(254,'B','30天，30天	',64),(255,'C',' 31天，30天	',64),(256,'D','29天，30天	',64),(257,'A','2300年	',65),(258,'B','2060年	',65),(259,'C','1989年	',65),(260,'D','2021年	',65),(261,'A','1.0	',66),(262,'B','2.0	',66),(263,'C','12.0	',66),(264,'D','24.0	',66),(265,'A','40度	',67),(266,'B','80度	',67),(267,'C','120度	',67),(268,'D','160度	',67),(269,'A','钝角	',68),(270,'B','锐角	',68),(271,'C','直角	',68),(272,'D','无法确定	',68),(273,'A','平角	',69),(274,'B','直角  	',69),(275,'C','钝角	',69),(276,'D','锐角	',69),(277,'A','周角 > 平角 > 钝角 > 锐角	',70),(278,'B','钝角 > 锐角 > 周角 > 平角	',70),(279,'C','周角 > 锐角 > 直角 > 平角	',70),(280,'D','周角 > 平角 > 锐角 > 钝角	',70),(281,'A','锐角	',71),(282,'B','钝角	',71),(283,'C','直角	',71),(284,'D','无法确定	',71),(285,'A','角的边	',72),(286,'B','角的顶点	',72),(287,'C','角的顶点的字母	',72),(288,'D','无法确定	',72),(289,'A','180.0	',73),(290,'B','360.0	',73),(291,'C','540.0	',73),(292,'D','720.0	',73),(293,'A','没有端点	',74),(294,'B','—个端点	',74),(295,'C','两个端点	',74),(296,'D','无数个端	',74),(297,'A','没有端点	',75),(298,'B','—个端点	',75),(299,'C','两个端点	',75),(300,'D','无数个端	',75),(301,'A','没有端点	',76),(302,'B','—个端点	',76),(303,'C','两个端点	',76),(304,'D','无数个端	',76),(305,'A','可以度量	',77),(306,'B','不可以度量	',77),(307,'C','有时可以度量有时不可以度量	',77),(308,'D','无法确定	',77),(309,'A','直线	',78),(310,'B','射线		',78),(311,'C','线段	',78),(312,'D','曲线	',78),(313,'A','1.0	',79),(314,'B','2.0	',79),(315,'C','3.0	',79),(316,'D','无数	',79),(317,'A','—条直线	',80),(318,'B','—条线段	',80),(319,'C','一条射线	',80),(320,'D','—条曲线	',80),(321,'A','90度	',81),(322,'B','180度	',81),(323,'C','直	',81),(324,'D','平	',81),(325,'A','360度	',82),(326,'B','180度	',82),(327,'C','90度	',82),(328,'D','30度	',82),(329,'A','80度 	',83),(330,'B','100度	',83),(331,'C','110度	',83),(332,'D','135度	',83),(333,'A','30度	',84),(334,'B','50度	',84),(335,'C','5度	',84),(336,'D','150度	',84),(337,'A','30度	',85),(338,'B','50度	',85),(339,'C','5度	',85),(340,'D','150度	',85),(341,'A','6毫米 	',86),(342,'B','10厘米	',86),(343,'C','6厘米	',86),(344,'D','6米	',86),(345,'A','比原来的角大	',87),(346,'B','和原来的角一样大	',87),(347,'C','比原来的角小	',87),(348,'D','前面三种都有可能	',87),(349,'A','3时半	',88),(350,'B',' 9时	',88),(351,'C','6点正	',88),(352,'D','12时正	',88),(353,'A','369000.0	',89),(354,'B','3609000.0	',89),(355,'C','3.6009E7	',89),(356,'D','3.0609E7	',89),(357,'A','千位	',90),(358,'B','万位	',90),(359,'C','十万位	',90),(360,'D','千万位	',90),(361,'A','千位	',91),(362,'B','万位	',91),(363,'C','十万位	',91),(364,'D','千万位	',91),(365,'A','16.0	',92),(366,'B','18.0	',92),(367,'C','20.0	',92),(368,'D','24.0	',92),(369,'A','2.02205808E8	',93),(370,'B','2.20208505E8	',93),(371,'C','2.0208505E7	',93),(372,'D','2.220208505E9	',93),(373,'A','数位	',94),(374,'B','计数单位	',94),(375,'C','数级	',94),(376,'D','数字	',94),(377,'A','1001300.0	',95),(378,'B','1.010003E8	',95),(379,'C','101300.0	',95),(380,'D','1010300.0	',95),(381,'A','7.0005006E8	',96),(382,'B','7.0005006E8	',96),(383,'C','7.00050006E9	',96),(384,'D','4.007005006E10	',96),(385,'A','四位数	',97),(386,'B','五位数 	',97),(387,'C','六位数	',97),(388,'D','七位数	',97),(389,'A','0.0	',98),(390,'B','4.0	',98),(391,'C','5.0	',98),(392,'D','6.0	',98),(393,'A','小胖	',99),(394,'B','小亚	',99),(395,'C','一样多 	',99),(396,'D','无法比较	',99),(397,'A','2.0	',100),(398,'B','4.0	',100),(399,'C','6.0	',100),(400,'D','7.0	',100),(401,'A','500055.0	',101),(402,'B','555000.0	',101),(403,'C','505500.0	',101),(404,'D','500505.0	',101),(405,'A','5/7<5/6<3/7	',102),(406,'B','5/6>5/7>3/7	',102),(407,'C','3/7<5/7<5/6	',102),(408,'D','	',102),(409,'A','3200平方分米	',103),(410,'B','180000平方米	',103),(411,'C','5000000 平方厘米	',103),(412,'D','1平方千米	',103),(413,'A','进一法	',104),(414,'B','去尾法	',104),(415,'C','四舍五入法或进一法	',104),(416,'D','四舍五入法或去尾法	',104),(417,'A','0.24	',105),(418,'B','2.0	',105),(419,'C','2.4	',105),(420,'D','24.0	',105),(421,'A','0.806	',106),(422,'B','8.06	',106),(423,'C','80.6	',106),(424,'D','806.0	',106),(425,'A','0.02001	',107),(426,'B','0.2001	',107),(427,'C','2.001	',107),(428,'D','20.01	',107),(429,'A','5600米	',108),(430,'B','5千米60米	',108),(431,'C','5.006千米		',108),(432,'D',' 5千米660米	',108),(433,'A',' 36.5×2.54	',109),(434,'B',' 25.4×36.5	',109),(435,'C','36.5×0.254	',109),(436,'D','36.5×1	',109),(437,'A','<	',110),(438,'B','=	',110),(439,'C','>	',110),(440,'D','不确定	',110),(441,'A','<	',111),(442,'B','=	',111),(443,'C','>	',111),(444,'D','不确定	',111),(445,'A','① 	',112),(446,'B','②		',112),(447,'C','③		',112),(448,'D',' ④	',112),(449,'A','大于1.17	',113),(450,'B','等于1.17	',113),(451,'C','小于1.17	',113),(452,'D','小于1	',113),(453,'A','②>③>④>①	',114),(454,'B','②>③>①>④	',114),(455,'C','③>②>④>①	',114),(456,'D',' ③>②>①>④	',114),(457,'A','M÷0.1>M		',115),(458,'B','M×1.1>M	',115),(459,'C','M÷1.1>M	',115),(460,'D','M×0.99<M	',115),(461,'A','N>1		',116),(462,'B',' 0<N≤1		',116),(463,'C','N=1	',116),(464,'D','0<N<1	',116),(465,'A','大于1,小于7.2	',117),(466,'B','大于0,小于等于1	',117),(467,'C','大于等于1,小于7.2	',117),(468,'D','大于0,小于1	',117),(469,'A','12.0	',118),(470,'B','1.2	',118),(471,'C','0.12	',118),(472,'D','0.012	',118),(473,'A','a>b>c	',119),(474,'B','a>c>b	',119),(475,'C','b>c>a	',119),(476,'D','c>b>a	',119),(477,'A','4.16666		',120),(478,'B','5.0232323	',120),(479,'C',' 8.9808080…	',120),(480,'D','3.1415926	',120),(481,'A','<0	',121),(482,'B','<a		',121),(483,'C','>a	',121),(484,'D','以上三种都有可能	',121),(485,'A','>	',122),(486,'B','<	',122),(487,'C','=	',122),(488,'D','无法确定	',122),(489,'A','a÷0.1=1	',123),(490,'B','a×0.8=1		',123),(491,'C',' 6.3÷a=1	',123),(492,'D','0.8×a=1	',123),(493,'A','>	',124),(494,'B','<	',124),(495,'C','=	',124),(496,'D','无法确定	',124),(497,'A','30.0	',125),(498,'B','30.2	',125),(499,'C','30.13	',125),(500,'D','30.1	',125),(501,'A','大于或等于6.75但小于6.85	',126),(502,'B','大于或等于6.74但小于6.84	',126),(503,'C','大于或等于6.70但小于6.85	',126),(504,'D','大于或等于6.75但小于6.84	',126),(505,'A','a>b	',127),(506,'B','a=b		',127),(507,'C','a<b	',127),(508,'D','无法确定大小	',127),(509,'A','0.004	',128),(510,'B','0.005	',128),(511,'C','0.009	',128),(512,'D','0.01	',128),(513,'A','两人都是女的,甲比乙大18岁	',129),(514,'B','一男一女,乙比甲大18岁	',129),(515,'C','一男一女,甲比乙大18岁	',129),(516,'D','两人都是男的,乙比甲大18岁	',129),(517,'A','0.987	',130),(518,'B','0.988	',130),(519,'C','0.998	',130),(520,'D','1.0	',130),(521,'A','0.0	',131),(522,'B','0.3	',131),(523,'C','1.0	',131),(524,'D','2.5	',131),(525,'A','6.3÷a=1	',132),(526,'B',' a×0.3=1	',132),(527,'C','a÷0.1=1		',132),(528,'D','0.8×a=1	',132),(529,'A','1.06×1.74	',133),(530,'B','1.06×174	',133),(531,'C','0.106×1740	',133),(532,'D','1060×0.0174	',133),(533,'A','大于或等于5.69但小于5.74	',134),(534,'B','大于或等于5.695但小于5.704	',134),(535,'C','大于或等于5.70但小于5.74	',134),(536,'D','大于或等于5.695但小于5.705	',134),(537,'A','数学	',135),(538,'B',' 英语	',135),(539,'C','语文	',135),(540,'D','无法判断	',135),(541,'A','0.375×0.16	',136),(542,'B','37.5×16	',136),(543,'C',' 37.5×0.16	',136),(544,'D','37.5×0.016	',136),(545,'A','(4+6+6+6+7+7)÷6		',137),(546,'B',' (4+6×3+7×2)÷3	',137),(547,'C','(4+6+7)÷3	',137),(548,'D','(4+6×3+7×2)÷6	',137),(549,'A','(120+70)÷2		',138),(550,'B',' (120+70×3)÷(2+3)	',138),(551,'C','(120+70×3)÷2		',138),(552,'D',' (120×2+70×3)÷(2+3)	',138),(553,'A','(60+70)÷2		',139),(554,'B','(60+70)÷(2+3)	',139),(555,'C','(60×2+70×3)÷2		',139),(556,'D','(60×2+70×3)÷(2+3)	',139),(557,'A','(360+450+160+180)÷2	',140),(558,'B',' (360+450+160+180)÷3	',140),(559,'C','(360+450+160+180)÷4	',140),(560,'D','(360+450+160+180)÷6	',140),(561,'A','(200+160+190)÷3		',141),(562,'B','(200×3+160×2+190×2)÷7	',141),(563,'C','(200×2+160×2+190×3)÷7	',141),(564,'D','(200×2+160×3+190×2)÷7	',141),(565,'A','(92+91+91.5)÷3		',142),(566,'B',' (92+91)×2÷4	',142),(567,'C','(91+91.5)×2÷4	',142),(568,'D','(92+91.5)×2÷4	',142),(569,'A','149.0	',143),(570,'B','150.0	',143),(571,'C','152.0	',143),(572,'D','无法计算	',143),(573,'A',' (68+84)÷2		',144),(574,'B',' (68÷3÷6+92÷5÷4)÷2	',144),(575,'C',' (68+84)÷(3×6+5×4)	',144),(576,'D','(68+84)÷(3+5)÷(6+4)	',144),(577,'A','a×2	',145),(578,'B','a+2		',145),(579,'C','a×a	',145),(580,'D','a+a	',145),(581,'A',' 2x>x2	',146),(582,'B','2x=x2	',146),(583,'C',' 2x<x2	',146),(584,'D','无法确定	',146),(585,'A','2.0	',147),(586,'B','b-a	',147),(587,'C',' a-b	',147),(588,'D',' b-a+2	',147),(589,'A','a÷4-b	',148),(590,'B','(a-b)÷4	',148),(591,'C',' (a+b)÷4	',148),(592,'D','	',148),(593,'A','18x+5x=23x	',149),(594,'B',' 5(a+b)=5a+5b	',149),(595,'C','6x-x-2x	',149),(596,'D','6y-8=40	',149),(597,'A','C=2(a+b)	',150),(598,'B','18-2m<5	',150),(599,'C','9×0.9=8.1	',150),(600,'D','x÷6=0	',150),(601,'A','9　3	',151),(602,'B','8　4	',151),(603,'C','7　5	',151),(604,'D','5　7	',151),(605,'A',' 18		',152),(606,'B','24.0	',152),(607,'C','54.0	',152),(608,'D','72.0	',152),(609,'A',' x+5=10	',153),(610,'B','5-x=2	',153),(611,'C',' 3+y=10	',153),(612,'D','37-x=30	',153),(613,'A','0.25÷x=2.5\n解:x=2.5÷0.25\nx=10	',154),(614,'B','0.25÷x=2.5\n解:=0.25÷2.5\n=0.1	',154),(615,'C','0.25÷x=2.5\n解:=0.25÷2.5\n=0.1	',154),(616,'D','0.25÷x=2.5\n解:x=2.5×0.25\nx=0.625	',154),(617,'A','25x	',155),(618,'B','15-3=12	',155),(619,'C','6x+1=6	',155),(620,'D','4x+7<9	',155),(621,'A','3x=4.5	',156),(622,'B','2x+9=5x	',156),(623,'C','1.2÷x=4	',156),(624,'D',' 3x÷2=18	',156),(625,'A','5a	',157),(626,'B','10a		',157),(627,'C','12a	',157),(628,'D','20a	',157),(629,'A',' (2a-b)	',158),(630,'B',' (a+b)÷2		',158),(631,'C','2(a+b)		',158),(632,'D','(a-b)÷2	',158),(633,'A','2.5	',159),(634,'B','4.5	',159),(635,'C','8.5	',159),(636,'D','12.5	',159);

/*Table structure for table `t_person` */

DROP TABLE IF EXISTS `t_person`;

CREATE TABLE `t_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_person` */

/*Table structure for table `t_pkroom` */

DROP TABLE IF EXISTS `t_pkroom`;

CREATE TABLE `t_pkroom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstpkuser` varchar(255) DEFAULT NULL,
  `pk_result` varchar(255) DEFAULT NULL,
  `pk_room_id` int(11) DEFAULT NULL,
  `questions` text,
  `secondpkuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_pkroom` */

/*Table structure for table `t_question` */

DROP TABLE IF EXISTS `t_question`;

CREATE TABLE `t_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kprelative_id` bigint(20) DEFAULT NULL,
  `question_content` varchar(200) NOT NULL,
  `question_analyse` varchar(255) DEFAULT NULL,
  `question_type` smallint(6) DEFAULT NULL,
  `std_answer` varchar(100) NOT NULL,
  `qu_knowledge_point_id` bigint(20) DEFAULT NULL,
  `qu_label_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_75pwk8fa0w2gd3feag05cwesr` (`qu_knowledge_point_id`),
  KEY `FK_csu4pwq4vklxq80jnywctoouc` (`qu_label_id`),
  CONSTRAINT `FK_75pwk8fa0w2gd3feag05cwesr` FOREIGN KEY (`qu_knowledge_point_id`) REFERENCES `t_knowledge_point` (`id`),
  CONSTRAINT `FK_csu4pwq4vklxq80jnywctoouc` FOREIGN KEY (`qu_label_id`) REFERENCES `t_label` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;

/*Data for the table `t_question` */

insert  into `t_question`(`id`,`kprelative_id`,`question_content`,`question_analyse`,`question_type`,`std_answer`,`qu_knowledge_point_id`,`qu_label_id`) values (1,1,'3是几的3倍？正确的算式是（）	','题目过于简单，请自行分析	',0,'A	',1,NULL),(2,2,'小胖做了 7朵花，小亚做了 21朵花，21 ÷ 7表示（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(3,3,'在24、32、30、21、48、9中，既是3的倍数，又是6的倍数的数有（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(4,4,'既是6的倍数，又是9的倍数的一组数是（）	','题目过于简单，请自行分析	',0,'A	',1,NULL),(5,5,'13÷3=4……1 读作：（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(6,6,'商是10，除数是5，被除数是50，算式是（）	','题目过于简单，请自行分析	',0,'C	',1,NULL),(7,7,'与8×2得数相等的算式是（）	','题目过于简单，请自行分析	',0,'D	',1,NULL),(8,8,'7×4读作：（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(9,9,'算式8÷4=2，下面（）表示的意义式错的	','题目过于简单，请自行分析	',0,'A	',1,NULL),(10,10,'得数最接近50的算式是（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(11,11,'2＋2可以写成2×2，3＋3可以写成（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(12,12,'8个☆分给4个小朋友，每人分到（）	','题目过于简单，请自行分析	',0,'C	',1,NULL),(13,13,'3个○的3倍是（）个○	','题目过于简单，请自行分析	',0,'C	',1,NULL),(14,14,'最小的两位数和最大的两位数相差（）	','题目过于简单，请自行分析	',0,'A	',1,NULL),(15,15,'一堆苹果，比20个多，比30个少，分的份数和每一份的个数同样多。这些苹果有（）	','题目过于简单，请自行分析	',0,'C	',1,NULL),(16,16,'最小的两位数和最大的两位数的和是（）	','题目过于简单，请自行分析	',0,'D	',1,NULL),(17,17,'下列数中（）是平方数	','题目过于简单，请自行分析	',0,'D	',1,NULL),(18,18,'面包店做了50个面包，每6个装一袋，最多可以装满（）	','题目过于简单，请自行分析	',0,'A	',1,NULL),(19,19,'仓库里有89吨面粉，第一天运走10吨，剩下的每天运9吨，还需要（）天运完。	','题目过于简单，请自行分析	',0,'B	',1,NULL),(20,20,'大卖场中运进一批水果，卖出的重量是剩下的2倍，如果剩下800千克水果，那么运进多少千克水果？正确的列式是（）	','题目过于简单，请自行分析	',0,'D	',1,NULL),(21,21,'已知△=3，□=600，○=80，那么□－△×○=（）	','题目过于简单，请自行分析	',0,'C	',1,NULL),(22,22,'如果36=☆+☆+☆+☆，那么70×☆=（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(23,23,'24×△=2400×☆，比较△与☆的关系，得出的结论是（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(24,24,'要使345×□的积是一个三位数，□里最大填（）	','题目过于简单，请自行分析	',0,'A	',1,NULL),(25,25,'一根绳子长86米，第一次用去38米，第二次用去29米，现在绳子的长度比原来缩短了多少米？正确的算式是（）	','题目过于简单，请自行分析	',0,'C	',1,NULL),(26,26,'56÷4的读法中，错误的是（）	','题目过于简单，请自行分析	',0,'B	',1,NULL),(27,27,'两个因数相乘得到的积比其中一个因数（）	','题目过于简单，请自行分析	',0,'C	',1,NULL),(28,28,'□÷△=14……7，如果△是最小的数，那么□是（）	','题目过于简单，请自行分析	',0,'A	',1,NULL),(29,29,'15 *[1395/(17+ 28)-17]的运算顺序是(   )•	','题目过于简单，请自行分析	',0,'D	',1,NULL),(30,30,' 88 * 125 = 8 * 125 + 125 * 80,这是运用了乘法的（  ）•	','题目过于简单，请自行分析	',0,'C	',1,NULL),(31,31,'用比8的3倍少16的数去除8,商是多少？算式是（ ）	','题目过于简单，请自行分析	',0,'D	',1,NULL),(32,32,'有一袋零件共60个,其中包含2克、5克、9克的零件各若干个,并且2克零件的数量是5克零件数量的2倍,5克零件的数量是9克零件数量的3倍,则这袋零件的重量是(　).	','题目过于简单，请自行分析	',0,'B	',1,NULL),(33,1,'最少用（）个完全一样的小正方形能拼出一个大正方形	','题目过于简单，请自行分析	',0,'C	',2,NULL),(34,2,'一个正方体有（）个面是正方形	','题目过于简单，请自行分析	',0,'D	',2,NULL),(35,3,'一个三角形有2个锐角，它是（）	','题目过于简单，请自行分析	',0,'D	',2,NULL),(36,4,'一个正方形的面积是100平方厘米，边长是（）厘米	','题目过于简单，请自行分析	',0,'B	',2,NULL),(37,5,'等腰三角形有（）条对称轴	','题目过于简单，请自行分析	',0,'B	',2,NULL),(38,6,'在一个三角形中，最大的一个角是锐角，这个三角形是（）	','题目过于简单，请自行分析	',0,'A	',2,NULL),(39,1,'要搭一个长方体模型需要（）	','题目过于简单，请自行分析	',0,'B	',3,NULL),(40,2,'用4个相同的正方体可以拼成（）	','题目过于简单，请自行分析	',0,'B	',3,NULL),(41,3,'下面选项中哪个不是长方体的特点？（）	','题目过于简单，请自行分析	',0,'C	',3,NULL),(42,4,'同一个圆中，直径是4分米，半径是（ ）.	','题目过于简单，请自行分析	',0,'C	',3,NULL),(43,5,'如图，小圆的直径是2厘米，大圆的半径是（  ）厘米.	','题目过于简单，请自行分析	',0,'A	',3,NULL),(44,6,' 一个圆的直径增加6厘米，它的半径增加（   )	','题目过于简单，请自行分析	',0,'B	',3,NULL),(45,7,'一个圆的直径增加6厘米，它的半径增加（   ）厘米	','题目过于简单，请自行分析	',0,'B	',3,NULL),(46,8,'具有稳定性的图形是(　).	','题目过于简单，请自行分析	',0,'A	',3,NULL),(47,9,'沿着平行四边形的两条对角线剪开,得到四个三角形,它们分别是(　).	','题目过于简单，请自行分析	',0,'D	',3,NULL),(48,10,' 在平行四边形的一条边上,可以作(　)条高.	','题目过于简单，请自行分析	',0,'D	',3,NULL),(49,11,'如图,求CD长度正确的是(　).(单位:dm)	','题目过于简单，请自行分析	',0,'C	',3,NULL),(50,12,'两个完全相同的梯形一定能拼成一个(　).	','题目过于简单，请自行分析	',0,'B	',3,NULL),(51,13,'只有一条对称轴的四边形是(　).	','题目过于简单，请自行分析	',0,'D	',3,NULL),(52,14,'把一个梯形分成两个部分,不可能得到的是( )	','题目过于简单，请自行分析	',0,'C	',3,NULL),(53,15,'两个(　)的梯形,可以拼成一个平行四边形.	','题目过于简单，请自行分析	',0,'D	',3,NULL),(54,16,'一个平行四边形和一个梯形面积相等,高也相等,平行四边形的底是2a,梯形的上底是a,那么梯形的下底是(　).	','题目过于简单，请自行分析	',0,'C	',3,NULL),(55,17,'有一个三角形,它的面积是24cm2,那么它的底和高可以是( ).	','题目过于简单，请自行分析	',0,'C	',3,NULL),(56,18,'梯形的上底、下底和高分别扩大到原来的3倍,它的面积将扩大到原来的(　).	','题目过于简单，请自行分析	',0,'B	',3,NULL),(57,19,'三角形和平行四边形面积相等,平行四边形的底是三角形底的2倍,若平行四边形的高是4厘米,则三角形的高是(　).	','题目过于简单，请自行分析	',0,'D	',3,NULL),(58,20,'下面四句话中,不正确的是(　).	','题目过于简单，请自行分析	',0,'B	',3,NULL),(59,21,'有一个等腰梯形,它的周长是48厘米,面积是96平方厘米,高是8厘米,则这个等腰梯形的腰长(　　).	','题目过于简单，请自行分析	',0,'B	',3,NULL),(60,1,'闰年上半年有（）天	','题目过于简单，请自行分析	',0,'C	',4,NULL),(61,2,'7月有31天，与它相邻的两个月各有（）	','题目过于简单，请自行分析	',0,'C	',4,NULL),(62,3,'某天上午9:00出了太阳，小刚说：“再过36小时（）”	','题目过于简单，请自行分析	',0,'C	',4,NULL),(63,4,'（）年是闰年	','题目过于简单，请自行分析	',0,'C	',4,NULL),(64,5,'8月有31天，与它相邻的两个月各有（）	','题目过于简单，请自行分析	',0,'C	',4,NULL),(65,6,'下列年份中，闰年是（）	','题目过于简单，请自行分析	',0,'B	',4,NULL),(66,7,'一天有24小时，分针要转（）圈	','题目过于简单，请自行分析	',0,'D	',4,NULL),(67,1,'一个角40度，在一个放大3倍的放大镜下这个角是（ ）.	','题目过于简单，请自行分析	',0,'A	',5,NULL),(68,2,'两个锐角之和一定是（  ）	','题目过于简单，请自行分析	',0,'D	',5,NULL),(69,3,'角AOB = 135。，角AOB 是（  ）	','题目过于简单，请自行分析	',0,'C	',5,NULL),(70,4,'把角按从大到小的顺序排列，正确的是（ ）	','题目过于简单，请自行分析	',0,'A	',5,NULL),(71,5,'两条直线相交可以组成4个角，这些角一定是（ ）.	','题目过于简单，请自行分析	',0,'D	',5,NULL),(72,6,'度量角的大小，量角器的中心点与（ ）重合.	','题目过于简单，请自行分析	',0,'B	',5,NULL),(73,7,'一天24小时，时针转了（  ）度.	','题目过于简单，请自行分析	',0,'D	',5,NULL),(74,8,'直线（  ）	','题目过于简单，请自行分析	',0,'A	',5,NULL),(75,9,'线段有（  ）	','题目过于简单，请自行分析	',0,'C	',5,NULL),(76,10,'射线有（  ）	','题目过于简单，请自行分析	',0,'B	',5,NULL),(77,11,'射线的长度（ ）	','题目过于简单，请自行分析	',0,'B	',5,NULL),(78,12,'从一点引出两条（ )就组成一个角•	','题目过于简单，请自行分析	',0,'B	',5,NULL),(79,13,'周角有( ) 条边	','题目过于简单，请自行分析	',0,'B	',5,NULL),(80,14,'画角的第一步是画（ 	)	','题目过于简单，请自行分析	',0,'C	',5,NULL),(81,15,'3点钟，钟面上时针与分针所夹的角是（ 	)	','题目过于简单，请自行分析	',0,'A	',5,NULL),(82,16,'时钟的分针旋转一周，时针就要旋转( )	','题目过于简单，请自行分析	',0,'D	',5,NULL),(83,17,'13时30分，钟面上时针与分针所夹的角是（ ）	','题目过于简单，请自行分析	',0,'D	',5,NULL),(84,18,'210度的角比周角小（ ）	','题目过于简单，请自行分析	',0,'D	',5,NULL),(85,19,'95°的角比直角大（  ）	','题目过于简单，请自行分析	',0,'C	',5,NULL),(86,20,'把1米长的线段，平均分成100份，其中的6份是(  )	','题目过于简单，请自行分析	',0,'C	',5,NULL),(87,21,'用放大镜看一个角，看到的角(  )	','题目过于简单，请自行分析	',0,'C	',5,NULL),(88,22,'时针和分针所成的角是锐角的是( )	','题目过于简单，请自行分析	',0,'A	',5,NULL),(89,1,'由3个千万、6个百万、9个千组成的数是（  ）	','题目过于简单，请自行分析	',0,'C	',6,NULL),(90,2,'从个位起向左数第四位是（  ）	','题目过于简单，请自行分析	',0,'A	',6,NULL),(91,3,'亿位的右面一位是（ ）	','题目过于简单，请自行分析	',0,'D	',6,NULL),(92,4,'用0、1、5、9组成的不重复的四位数，一共有（  ）个	','题目过于简单，请自行分析	',0,'B	',6,NULL),(93,5,'下面各数中百万位上是2的数是（ )	','题目过于简单，请自行分析	',0,'A	',6,NULL),(94,6,'万、十万、百万、千万都是（  ）	','题目过于简单，请自行分析	',0,'B	',6,NULL),(95,7,'十万一千三百写作（ ）	','题目过于简单，请自行分析	',0,'C	',6,NULL),(96,8,'七十亿零五十万零六十写作（  ）.	','题目过于简单，请自行分析	',0,'C	',6,NULL),(97,9,'一个多位数如果只有万级和个级，那么它至少是（ ）	','题目过于简单，请自行分析	',0,'B	',6,NULL),(98,10,' 19 () 324用四舍五入法凑成整万数是190000,( )里最小能填	','题目过于简单，请自行分析	',0,'A	',6,NULL),(99,11,'开学了，小丁丁和小亚各拿出自己零用钱的3去买文具，小丁丁用去10元，小亚 用去20元，原来零用钱多的是（ ）	','题目过于简单，请自行分析	',0,'B	',6,NULL),(100,12,'以1/8为单位的分数中，比1小的分数有（ ）个	','题目过于简单，请自行分析	',0,'D	',6,NULL),(101,13,'下面只读一个零的数是（ ）	','题目过于简单，请自行分析	',0,'A	',6,NULL),(102,14,'在5/7、5/6、3/7三个分数中，按从小到大顺序排列的是（ ）.	','题目过于简单，请自行分析	',0,'C	',6,NULL),(103,15,'在 3200平方分米、 180000平方米、5000000 平方厘米、 1平方千米 中，面积最大的是( )	','题目过于简单，请自行分析	',0,'D	',6,NULL),(104,16,'633570约等于64 万，一定是按（ ）凑整的	','题目过于简单，请自行分析	',0,'A	',6,NULL),(105,1,'下面各数的末尾添上“0”后,数的大小会发生变化的是( )	','题目过于简单，请自行分析	',0,'B	',7,NULL),(106,2,'8.06÷100×10,结果是(　).	','题目过于简单，请自行分析	',0,'A	',7,NULL),(107,3,'20.01中的小数点向左移三位,再向右移一位,结果是(　).	','题目过于简单，请自行分析	',0,'B	',7,NULL),(108,4,'将5600米、5千米60米、5.006千米、5千米660米按从大到小的顺序排列,排在第二个的是(　).	','题目过于简单，请自行分析	',0,'A	',7,NULL),(109,1,'下面算式中,积小于36.5的是(　)	','题目过于简单，请自行分析	',0,'C	',8,NULL),(110,2,'如果算式0.24×M<0.24,那么M(　)1	','题目过于简单，请自行分析	',0,'A	',8,NULL),(111,3,'两个纯小数,它们的和与积比较,结果是和(　)积.	','题目过于简单，请自行分析	',0,'C	',8,NULL),(112,4,'下面的除法算式中,商大于被除数的算式是(　).① 8.2÷4.1 ② 1.25÷25 ③ 0.36÷0.8 ④ 10.8÷3.6	','题目过于简单，请自行分析	',0,'C	',8,NULL),(113,5,'算式1.17÷0.9,除得的商一定(　)	','题目过于简单，请自行分析	',0,'A	',8,NULL),(114,6,'不计算①8.256÷2.14,②8256÷2.14,③82.56÷0.214,④0.8256÷0.0214等四题,按它们商的大小排序,应该是(　)	','题目过于简单，请自行分析	',0,'A	',8,NULL),(115,7,'已知M>0,则下面式子中错误的是(　)	','题目过于简单，请自行分析	',0,'C	',8,NULL),(116,8,'当1.08÷N>1.08时,下面说法中正确的是(　)	','题目过于简单，请自行分析	',0,'D	',8,NULL),(117,9,'当7.2×E≤7.2÷E时,则E一定(　).	','题目过于简单，请自行分析	',0,'B	',8,NULL),(118,10,'0.753÷0.13得到的商是5.7,余数是(　).	','题目过于简单，请自行分析	',0,'D	',8,NULL),(119,11,' 如果a×0.9=b÷0.9=c÷1(a、 b、 c都大于0),那么a、 b、 c三个数的大小关系是(　).	','题目过于简单，请自行分析	',0,'B	',8,NULL),(120,12,'下列小数能用循环节表示的是(　).	','题目过于简单，请自行分析	',0,'C	',8,NULL),(121,13,'若0<a<b<1,则a÷b的商一定(　).	','题目过于简单，请自行分析	',0,'C	',8,NULL),(122,14,'如果a÷0.8=b÷1(a、 b都大于0),那么a(　)	','题目过于简单，请自行分析	',0,'B	',8,NULL),(123,15,'下列式子中, a<1的是(　).	','题目过于简单，请自行分析	',0,'A	',8,NULL),(124,16,'如果a÷0.09=b÷0.1(a、 b都大于0),那么a(　)b.	','题目过于简单，请自行分析	',0,'B	',8,NULL),(125,17,'2278.35÷2.6的商四舍五入到十分位是(　).	','题目过于简单，请自行分析	',0,'D	',8,NULL),(126,18,'一个两位小数,用四舍五入法得到的结果为6.8,它的取值范围为(　)	','题目过于简单，请自行分析	',0,'A	',8,NULL),(127,19,' a=1.28×0.25, b=1.28÷5, a与b比较,结果是( ).	','题目过于简单，请自行分析	',0,'A	',8,NULL),(128,20,' 一些三位小数四舍五入后约是0.80,则这些三位小数中,最大数与最小数相差(　).	','题目过于简单，请自行分析	',0,'C	',8,NULL),(129,21,'甲、乙两人的身份证号码分别为:31010219680806××14,31010719860704××28,那么下列说法正确的是(　).	','题目过于简单，请自行分析	',0,'C	',8,NULL),(130,22,'将0.98765用四舍五入法凑整到千分位约是(　).	','题目过于简单，请自行分析	',0,'B	',8,NULL),(131,23,'a÷b>a(a大于0),那么b可能是(　).	','题目过于简单，请自行分析	',0,'B	',8,NULL),(132,24,'下列式子中, a<1的是(　).	','题目过于简单，请自行分析	',0,'D	',8,NULL),(133,25,'与10.6×1.74的积相等的是(　).	','题目过于简单，请自行分析	',0,'D	',8,NULL),(134,26,'用四舍五入法得到约是5.70的数,它的范围是(　).	','题目过于简单，请自行分析	',0,'A	',8,NULL),(135,27,'小胖参加语文、数学、英语的期中测试,其中数学比三门功课的平均分高2分,英语比语文高5分,那么三门功课中,(　)分数最高	','题目过于简单，请自行分析	',0,'A	',8,NULL),(136,28,'与3.75×1.6结果相同的算式是(　).	','题目过于简单，请自行分析	',0,'C	',8,NULL),(137,1,'学校活动室里有6盒乒乓球,每个盒子里面分别有4只、6只、6只、6只、7只、7只乒乓球.平均每盒有乒乓球多少只?下面列式正确的是(　　).	','题目过于简单，请自行分析	',0,'A	',9,NULL),(138,2,'五年级两个班的学生为幼儿园小朋友做纸花.一班用了2小时,一共做了 120个,二班用了3小时,平均每小时做70个,那么两个班平均每小时做多少个纸花?下面列式正确的是(　　).	','题目过于简单，请自行分析	',0,'D	',9,NULL),(139,3,'五年级两个班的学生为幼儿园小朋友做纸花.一班用了2小时,平均每小时做60个,二班用了3小时,平均每小时做70个.那么平均每个班做多少个纸花?下面列式正确的是(　).	','题目过于简单，请自行分析	',0,'C	',9,NULL),(140,4,'服装商店第一天卖出服装360件,第二天卖出服装450件,第三天上午卖出160件,下午卖出180件,平均每天卖出服装多少件?下面算式中正确的是(　).	','题目过于简单，请自行分析	',0,'B	',9,NULL),(141,5,'某一周小胖练习踢毽子,每天分别踢200下、160下、190下、160下、190下、160下和200下,这一周平均每天踢毽子多少下?下面算式中正确的是(　).	','题目过于简单，请自行分析	',0,'D	',9,NULL),(142,6,'五年级(1)班一次数学测验,各组之间的平均成绩分别是:第一、二组平均为92分,第二、三组平均为91分,第三、四组平均为91.5分,求全班平均成绩的正确算式是(　).	','题目过于简单，请自行分析	',0,'D	',9,NULL),(143,7,'某班男同学的人数是女同学的一半,男同学的平均身高是154厘米,女同学的平均身高是148厘米,全班同学的平均身高是(　)厘米.	','题目过于简单，请自行分析	',0,'B	',9,NULL),(144,8,'203辆汽车6次共运大米68吨, 5辆汽车4次共运大米84吨.平均每辆汽车每次运大米多少吨?下面算式正确的是(　).	','题目过于简单，请自行分析	',0,'C	',9,NULL),(145,1,'a2与(　)相等.	','题目过于简单，请自行分析	',0,'C	',10,NULL),(146,2,'比较2x和x2的大小,结果是(　)	','题目过于简单，请自行分析	',0,'D	',10,NULL),(147,3,'丁丁比昕昕小,丁丁今年a岁,昕昕今年b岁,2年后丁丁比昕昕小(　)岁	','题目过于简单，请自行分析	',0,'B	',10,NULL),(148,4,'甲数是a,比乙数的4倍少b,乙数是(　).	','题目过于简单，请自行分析	',0,'C	',10,NULL),(149,5,'下列各式中不属于等式的是(　).	','题目过于简单，请自行分析	',0,'C	',10,NULL),(150,6,'下列各式中方程是(　).	','题目过于简单，请自行分析	',0,'D	',10,NULL),(151,7,' 已知:△+△+○=19,△+○=12,则:△=(　　),○=(　　).	','题目过于简单，请自行分析	',0,'C	',10,NULL),(152,8,'已知:○=△+△+△,○×△=108,则:○+△=(　).	','题目过于简单，请自行分析	',0,'B	',10,NULL),(153,9,' x=7是下面(　)方程的解.	','题目过于简单，请自行分析	',0,'D	',10,NULL),(154,10,'解方程0.25÷x=2.5,下列解答正确的是(　 ).	','题目过于简单，请自行分析	',0,'C	',10,NULL),(155,12,'下面的式子中,(　)是方程.	','题目过于简单，请自行分析	',0,'C	',10,NULL),(156,13,'x=3是下面方程(　)的解	','题目过于简单，请自行分析	',0,'B	',10,NULL),(157,14,'5个边长为a的正方形拼成一个长方形,拼出后的长方形周长是(　).	','题目过于简单，请自行分析	',0,'C	',10,NULL),(158,15,'五年级种树a棵,比四年级种树的2倍少b棵.四年级种树(　)棵	','题目过于简单，请自行分析	',0,'B	',10,NULL),(159,16,'若2a+2b+1=6,则5(a+b)-4的结果是(　).	','题目过于简单，请自行分析	',0,'C	',10,NULL);

/*Table structure for table `t_subject` */

DROP TABLE IF EXISTS `t_subject`;

CREATE TABLE `t_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sb_name` varchar(255) DEFAULT NULL,
  `sb_shortcut` varchar(255) DEFAULT NULL,
  `sb_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_subject` */

insert  into `t_subject`(`id`,`sb_name`,`sb_shortcut`,`sb_title`) values (1,'语文','yw','上海市考纲-语文知识点练习'),(2,'数学','sx','上海市考纲-数学知识点练习'),(3,'英语','yy','上海市考纲-英语知识点练习');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `chinesepkrecord` varchar(255) DEFAULT NULL,
  `englishpkrecord` varchar(255) DEFAULT NULL,
  `mathpkrecord` varchar(255) DEFAULT NULL,
  `pkrate` double DEFAULT NULL,
  `pkrecord` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pk_room_id` bigint(20) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `school_district` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`avatar`,`chinesepkrecord`,`englishpkrecord`,`mathpkrecord`,`pkrate`,`pkrecord`,`gender`,`grade`,`name`,`password`,`phone`,`pk_room_id`,`school`,`school_district`) values (1,'../../images/pic/9.jpg',NULL,NULL,NULL,0,'{\"fail\":\"0\",\"success\":\"0\",\"Info\":\"还差30场晋级LV2\"}','0','3','张三','123456','13127879086',1,'1201','12'),(2,'../images/pic/2.jpg',NULL,NULL,NULL,0,'{\"fail\":\"0\",\"success\":\"0\",\"Info\":\"还差30场晋级LV2\"}','1','3','hello','123456','13127879085',1,'1201','12');

/*Table structure for table `t_user_question_hist` */

DROP TABLE IF EXISTS `t_user_question_hist`;

CREATE TABLE `t_user_question_hist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qh_user_question_hist` text,
  `qh_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_425ekoffhm3p0vmjh7l2q6kn0` (`qh_user_id`),
  CONSTRAINT `FK_425ekoffhm3p0vmjh7l2q6kn0` FOREIGN KEY (`qh_user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_question_hist` */

insert  into `t_user_question_hist`(`id`,`qh_user_question_hist`,`qh_user_id`) values (1,'[{\"kpId\":1,\"answer\":\"B\",\"qid\":1,\"status\":2},{\"kpId\":1,\"answer\":\"C\",\"qid\":2,\"status\":2},{\"kpId\":1,\"answer\":\"D\",\"qid\":3,\"status\":2},{\"kpId\":1,\"answer\":\"A\",\"qid\":4,\"status\":2},{\"kpId\":1,\"answer\":\"C\",\"qid\":5,\"status\":2},{\"kpId\":1,\"answer\":\"C\",\"qid\":6,\"status\":2},{\"kpId\":1,\"answer\":\"D\",\"qid\":7,\"status\":2},{\"kpId\":1,\"answer\":\"B\",\"qid\":8,\"status\":2},{\"kpId\":1,\"answer\":\"A\",\"qid\":9,\"status\":2},{\"kpId\":1,\"answer\":\"B\",\"qid\":10,\"status\":2},{\"kpId\":1,\"answer\":\"B\",\"qid\":11,\"status\":1},{\"kpId\":1,\"answer\":\"A\",\"qid\":12,\"status\":2},{\"kpId\":3,\"answer\":\"B\",\"qid\":39,\"status\":1},{\"kpId\":3,\"answer\":\"C\",\"qid\":40,\"status\":2}]',1);

/*Table structure for table `test_ecnuer` */

DROP TABLE IF EXISTS `test_ecnuer`;

CREATE TABLE `test_ecnuer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ecnuer_name` varchar(255) DEFAULT NULL,
  `t_yx` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j0wtfqpg2vkmoy1w4n0tm45ds` (`t_yx`),
  CONSTRAINT `FK_j0wtfqpg2vkmoy1w4n0tm45ds` FOREIGN KEY (`t_yx`) REFERENCES `test_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test_ecnuer` */

/*Table structure for table `test_unit` */

DROP TABLE IF EXISTS `test_unit`;

CREATE TABLE `test_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test_unit` */

/*Table structure for table `zx_grade_subject` */

DROP TABLE IF EXISTS `zx_grade_subject`;

CREATE TABLE `zx_grade_subject` (
  `subjects` bigint(20) NOT NULL,
  `grades` bigint(20) NOT NULL,
  KEY `FK_32oref8r2ydytyawic2u4ubuh` (`grades`),
  KEY `FK_q5m8fohepwkdkmgfbd9c49tnn` (`subjects`),
  CONSTRAINT `FK_32oref8r2ydytyawic2u4ubuh` FOREIGN KEY (`grades`) REFERENCES `t_grade` (`id`),
  CONSTRAINT `FK_q5m8fohepwkdkmgfbd9c49tnn` FOREIGN KEY (`subjects`) REFERENCES `t_subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `zx_grade_subject` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
