CREATE TABLE `AccountsTable` (
  `Username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(555) COLLATE utf8_unicode_ci NOT NULL,
  `Firstname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Lastname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `ERTable` (
  `ER_id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Type` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `Date` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Hour` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `Time` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `LocNumb` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Street` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `City` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `ZipCode` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `State` varchar(2) COLLATE utf8_unicode_ci NOT NULL,
  `Description` varchar(145) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ER_id`),
  UNIQUE KEY `ER_id_UNIQUE` (`ER_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=46 ;
