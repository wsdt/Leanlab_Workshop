
﻿DROP DATABASE Leanlabworking;

CREATE DATABASE Leanlabworking;

USE Leanlabworking;

CREATE TABLE Product (
	ProductID INT NOT NULL auto_increment,
    	Productname VARCHAR(50),
    	PRIMARY KEY (ProductID)
    	);
    
CREATE TABLE Station (
	StationID INT NOT NULL auto_increment,
    	Stationname VARCHAR(50),
    	PRIMARY KEY (StationID)
    	);
    
CREATE TABLE Productionstep (
	ProductionstepID INT NOT NULL auto_increment,
    	ProductID INT,
    	StationID INT,
    	FOREIGN KEY (ProductID) REFERENCES Product (ProductID),
    	FOREIGN KEY (StationID) REFERENCES Station (StationID),
    	PRIMARY KEY (ProductionstepID)
	);

CREATE TABLE Workstep (
	WorkstepID INT NOT NULL auto_increment,
    	ProductionstepID INT,
    	FOREIGN KEY (ProductionstepID) REFERENCES Productionstep (ProductionstepID),
    	PRIMARY KEY (WorkstepID)
	);
    
CREATE TABLE Typ (
	TypID INT NOT NULL auto_increment,
    	Typname VARCHAR (50),
    	PRIMARY KEY (TypID)
	);
    
/*Content kann nicht wiederverwendent werden, muss bei jedem Arbeitsschritt neu gespeichert werden*/

CREATE TABLE Content (
	ContentID INT NOT NULL auto_increment,
    	Contenttext VARCHAR (500), /*Falls Typ1 als Content gespeichert wird würde es HTML Code sein, ansonsten die Referenz auf den Speicherort des Bildes (können wir natürlich anders lösen!*/
    	WorkstepID INT,
    	TypID INT,
    	FOREIGN KEY (WorkstepID) REFERENCES Workstep (WorkstepID),
    	FOREIGN KEY (TypID) REFERENCES Typ (TypID),
    	PRIMARY KEY (ContentID)   	
	);
    
CREATE TABLE User (
	/*UserID INT NOT NULL auto_increment,*/
   	Username VARCHAR (50),
    	Password VARCHAR (100), /*Bcrypt hat 60 Zeichen */
	Points INT,
    	PRIMARY KEY (Username)    
    	); 
	
INSERT INTO User VALUES
	('default','$2a$04$F0AYTWK54/jMvIY3a2Krm.gqQZwkBaoSyn7wuIjug13FwHoC5ARA.');
    
INSERT INTO Typ(Typname) VALUES
	('Bild'),
    	('Video'),
    	('HTML5');

INSERT INTO Station(Stationname) VALUES
	('Stanzen'),
	('Bohren'),
    	('Biegen'),
	('Nieten'),
	('Kleben'),
	('Verpacken'),
	('Montage');

    
INSERT INTO Product(Productname) VALUES
	('LEAN Watch');
    
INSERT INTO Productionstep(ProductID, StationID) VALUES
	('1', '1'),
    	('1', '2'),
    	('1', '3'),
    
INSERT INTO Workstep(ProductionstepID) VALUES
	('1'),
    	('1'),
    	('2'),
    	('2'),
	('3');

INSERT INTO Content(Contenttext, WorkstepID, TypID) VALUES
    	('/etc/local/usr/beispieluser/bilder/bild1.jpg', 1, 1),
    	('/etc/local/usr/beispieluser/videos/video1.mpg', 1, 2),
    	('Legen Sie den Zeiger auf die Uhr', 1, 3),
    	('/etc/local/usr/beispieluser/bilder/bild2.jpg', 2, 1),
    	('/etc/local/usr/beispieluser/videos/video2.mpg', 2, 2),
    	('Schrauben Sie irgendeine Schraube fest', 2, 3),
    	('/etc/local/usr/beispieluser/bilder/bild3.jpg', 3, 1),
    	('/etc/local/usr/beispieluser/videos/video3.mpg', 3, 2),
    	('Bringen Sie das Ziffernblatt an der Schraube an', 3, 3); 
    
    
    