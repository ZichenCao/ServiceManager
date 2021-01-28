CREATE TABLE `Service` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) UNIQUE NOT NULL,
  `URL` varchar(200) NOT NULL,
  `Service_State` varchar(10) NOT NULL,
  `Created_By` varchar(10) NOT NULL,
  `Create_Date` datetime NOT NULL,
  `Udpate_Date` datetime,
  PRIMARY KEY (`Id`)
)
