USE MASTER
GO
DROP DATABASE IF EXISTS Bus
GO
CREATE DATABASE Bus 
COLLATE SQL_Latin1_General_CP1_CI_AS; 
GO
use Bus

-- Script to create Route table
CREATE TABLE Route (
    id INT PRIMARY KEY IDENTITY(1,1),
    startPoint NVARCHAR(100) NOT NULL,
    endPoint NVARCHAR(100) NOT NULL,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    frequency INT NOT NULL
);

-- Script to create Bus table
CREATE TABLE Bus (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(20) NOT NULL,
    price INT NOT NULL,
    routeID INT FOREIGN KEY REFERENCES Route(id) ON DELETE CASCADE
);

-- Script to create Tram table
CREATE TABLE Station (
    id INT PRIMARY  KEY IDENTITY(1,1),
    stationName NVARCHAR(100) NOT NULL
);

-- Script to create TuyenDiQuaTram table
CREATE TABLE RoutePassesStation (
    routeID INT,
    stationID INT,
    PRIMARY KEY (routeID, stationID),
    FOREIGN KEY (routeID) REFERENCES Route(id),
    FOREIGN KEY (stationID) REFERENCES Station(id) ON DELETE CASCADE
);

-- Script to create TaiKhoan table
CREATE TABLE Users (
	id INT PRIMARY KEY IDENTITY(1,1),
    email NVARCHAR(50),
    password NVARCHAR(50) NOT NULL,
    role NVARCHAR(20) NOT NULL, -- Can be 'customer', 'employee', 'admin',
	name Nvarchar(20) 
);


-- Inserting values into Route table
INSERT INTO Route (startPoint, endPoint, startTime, endTime, frequency)
VALUES (N'Kim Mã ', N'Ngũ Hiệp (Thanh Trì)', '08:00', '20:30', 15),
       (N'Bến xe Mỹ Đình', N'Xuân Mai', '7:00', '22:00', 15);

-- Inserting values into Bus table
INSERT INTO Bus (name, price, routeID)
VALUES ('88', 9000, 1),
       ('77', 9000, 2);

-- Inserting values into Station table
INSERT INTO Station (stationName)
VALUES ( N'Nhà hàng Hàn Quốc'),
       (N'nhà CT4 ĐN KĐT Mỹ Đình'),
       (N'Tường rào Trạm y tế Mễ Trì'),
       (N'Sân Golf Mỹ Đình Pearl'),
	   (N'Đối diện 116 Hào Nam'),
	   (N'109 Ô Chợ Dừa'),
	   (N'Đàn Xã Tắc - Ngõ Xã Đàn 2'),
	   (N'Ngã 4 Xã Đàn - Nam Đồng');

-- Inserting values into RoutePassesStation table
INSERT INTO RoutePassesStation (routeID, stationID)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
	   (2, 5),
       (2, 6),
       (2, 7),
       (2, 8);

-- Inserting values into Account table
INSERT INTO Users (email, password, role, name)
VALUES ('admin@gmail.com', '123', 'admin', N'Nguyễn Văn Min'),
       ('employee@gmail.com', '123', 'employee', N'Hồ Thị Y')


	   select * from Route;
	   SELECT r.id as routeID, s.id AS StationID, s.stationName AS StationName
	   FROM Route r JOIN  RoutePassesStation rp ON r.id = rp.routeID
	JOIN 
    Station s ON rp.stationID = s.id
	where r.id =1

	select top(1) id from Station order by id desc