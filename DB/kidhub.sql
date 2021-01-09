/*
*SCRIPT PARA CREAR BBDD QUE UTILIZARA LA APLICACION KIDHUB
*/
CREATE DATABASE IF NOT EXISTS kidhub;

use kidhub;

/*
*Primero borra todas las tablas por si existieran en la bbdd
*/
SET FOREIGN_KEY_CHECKS = 0;
SET GROUP_CONCAT_MAX_LEN=32768;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
  FROM information_schema.tables
  WHERE table_schema = (SELECT DATABASE());
SELECT IFNULL(@tables,'dummy') INTO @tables;

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;

/*
*Creamos todas las tablas y sus columnas
*/
CREATE TABLE USERS(
	Username NVARCHAR(30) not null,
	DNI NVARCHAR(9) not null,
	UserPassword NVARCHAR(30) not null,
	Email NVARCHAR(40) not null,
	FirstName NVARCHAR(30) not null,
	SecondName NVARCHAR(60) not null,
	BirthDate NVARCHAR(10) null,
	Age int null,
	Type NVARCHAR(7) not null,
	constraint U_USERS_DNI UNIQUE(DNI),
	constraint PK_USERS PRIMARY KEY(Username));

CREATE TABLE PARENTS(
	Username NVARCHAR(30) not null,
	PhoneNumber NVARCHAR(9) not null,
	constraint PK_PARENTS PRIMARY KEY(Username));


CREATE TABLE KIDS(
	Username NVARCHAR(30) not null,
	constraint PK_KIDS PRIMARY KEY(Username));


CREATE TABLE MONITORS(
	Username NVARCHAR(30) not null,
	PhoneNumber NVARCHAR(9) not null,
	Specialty NVARCHAR(30),
	constraint PK_MONITORS PRIMARY KEY(Username));


CREATE TABLE ParentKid(
	ParentUsername NVARCHAR(30) not null,
	KidUsername NVARCHAR(30) not null,
	constraint PK_ParentKid PRIMARY KEY(ParentUsername, KidUsername));


CREATE TABLE SPECIALITIES(
	MonitorUsername NVARCHAR(30) not null,
	Speciality NVARCHAR(30) not null,
	constraint PK_SPECIALITIES PRIMARY KEY(MonitorUsername, Speciality));


CREATE TABLE ActivityKid(
	KidUsername NVARCHAR(30) not null,
	ActivityID int not null,
	constraint PK_ParentActivityKid PRIMARY KEY(KidUsername, ActivityID));

CREATE TABLE ACTIVITIES(
	ActivityID int not null AUTO_INCREMENT,
	MonitorUsername NVARCHAR(30) not null,
	Name NVARCHAR(30) not null,
	StartDate NVARCHAR(16) not null,
	Duration int not null,
	EndDate NVARCHAR(16) not null,
	PlacesAvailable int not null,
	Capacity int not null,
	Address NVARCHAR(50) not null,
	Town NVARCHAR(20) not null,
	Type NVARCHAR(30) not null,
	constraint PK_ACTIVITIES PRIMARY KEY(ActivityID, MonitorUsername));


CREATE TABLE RIDES(
	ActivityID int not null,
	RideID int not null AUTO_INCREMENT,
	ParentUsername NVARCHAR(30) not null,
	PlacesAvailable int not null,
	Capacity int not null,
	Type NVARCHAR(6) not null, /*Ida o vuelta*/
	constraint PK_RIDES PRIMARY KEY(RideID/*ActivityID, ParentUsername, Type*/));


CREATE TABLE RideKid(
	RideID int not null,
	KidUsername NVARCHAR(30) not null,
	constraint PK_RideKid PRIMARY KEY(RideID, KidUsername));

CREATE TABLE STOPS(
	RideID int not null,
	StopDate NVARCHAR(16) not null,
/*	Duration int not null, ESTO HAY QUE QUITARLO*/
	Address NVARCHAR(50) not null,
	Town NVARCHAR(20) not null,
	Type NVARCHAR(10) not null, /*Origen o destino*/
	constraint PK_STOPS PRIMARY KEY(RideID, Type));

ALTER TABLE PARENTS
ADD CONSTRAINT FK_PARENTS_Username FOREIGN KEY(Username) REFERENCES USERS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE KIDS
ADD CONSTRAINT FK_KIDS_Username FOREIGN KEY(Username) REFERENCES USERS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE MONITORS
ADD CONSTRAINT FK_MONITORS_Username FOREIGN KEY(Username) REFERENCES USERS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ParentKid
ADD CONSTRAINT FK_ParentKid_ParentUsername FOREIGN KEY(ParentUsername) REFERENCES PARENTS(Username) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE ParentKid
ADD CONSTRAINT FK_ParentKid_KidUsername FOREIGN KEY(KidUsername) REFERENCES KIDS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE SPECIALITIES
ADD CONSTRAINT FK_SPECIALITIES_MonitorUsername FOREIGN KEY(MonitorUsername) REFERENCES MONITORS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ActivityKid
ADD CONSTRAINT FK_ActivityKid_KidUsername FOREIGN KEY(KidUsername) REFERENCES KIDS(Username) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE ActivityKid
ADD CONSTRAINT FK_ActivityKid_ActivityID FOREIGN KEY(ActivityID) REFERENCES ACTIVITIES(ActivityID) ON UPDATE CASCADE ON DELETE CASCADE;	

ALTER TABLE ACTIVITIES
ADD CONSTRAINT FK_ACTIVITIES_MonitorUsername FOREIGN KEY(MonitorUsername) REFERENCES MONITORS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE RIDES
ADD CONSTRAINT FK_RIDES_ActivityID FOREIGN KEY(ActivityID) REFERENCES ACTIVITIES(ActivityID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE RIDES
ADD CONSTRAINT FK_RIDES_ParentUsername FOREIGN KEY(ParentUsername) REFERENCES PARENTS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE RideKid
ADD CONSTRAINT FK_RideKid_RideID FOREIGN KEY(RideID) REFERENCES RIDES(RideID) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE RideKid
ADD CONSTRAINT FK_RideKid_KidUsername FOREIGN KEY(KidUsername) REFERENCES KIDS(Username) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE STOPS
ADD CONSTRAINT FK_STOPS_RideID FOREIGN KEY(RideID) REFERENCES RIDES(RideID) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE USERS
ADD CONSTRAINT CK_USERS_DNI CHECK(DNI LIKE '^[0-9]{8}[A-Z]' OR DNI LIKE '^[0-9]{7}[A-Z]' OR DNI LIKE '^[0-9]{7}[a-z]' or DNI LIKE '^[0-9]{8}[a-z]');

ALTER TABLE USERS
ADD CONSTRAINT CK_USERS_Email CHECK(Email LIKE '^%@%.%');

ALTER TABLE USERS
ADD CONSTRAINT CK_USERS_BirthDate CHECK(BirthDate <= getdate());

ALTER TABLE PARENTS
ADD CONSTRAINT CK_PARENTS_PhoneNumber CHECK(PhoneNumber LIKE '^[0-9]{9}');

ALTER TABLE RIDES
ADD CONSTRAINT CK_RIDES_Type CHECK(Type LIKE '^IDA' or Type LIKE '^VUELTA');

ALTER TABLE STOPS
ADD CONSTRAINT CK_STOPS_Type CHECK(Type LIKE '^SALIDA' or Type LIKE '^LLEGADA' or Type LIKE 'INTERMEDIA');

ALTER TABLE ACTIVITIES
ADD CONSTRAINT CK_ACTIVITIES_Duration CHECK(Duration >= 0);

ALTER TABLE STOPS
ADD CONSTRAINT CK_STOPS_Duration CHECK(Duration >= 0);

ALTER TABLE ACTIVITIES
ADD CONSTRAINT CK_ACTIVITIES_Capacity CHECK(Capacity >= 0);

ALTER TABLE RIDES
ADD CONSTRAINT CK_RIDES_Capacity CHECK(Capacity >= 0);

