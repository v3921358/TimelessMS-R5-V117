--
-- Definition of table `mostplayers`
--

DROP TABLE IF EXISTS `mostplayers`;
CREATE TABLE `mostplayers` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mostPlayers` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mostplayers`
--
--
-- Definition of table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `playername` varchar(25) NOT NULL,
  `title` varchar(75) NOT NULL,
  `message` varchar(500) NOT NULL,
  `timestamp` bigint(20) NOT NULL DEFAULT '0',
  `bug` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--