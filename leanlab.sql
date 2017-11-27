DROP DATABASE Leanlab;

CREATE DATABASE Leanlab;

USE Leanlab;

CREATE TABLE Product (
	Productid INT(10) NOT NULL auto_increment,
    Productname VARCHAR(50),
    PRIMARY KEY (Productid)
    );
    
CREATE TABLE Station (
	Stationid INT(10) NOT NULL auto_increment,
    Stationname VARCHAR(50),
    PRIMARY KEY (Stationid)
    );
    
CREATE TABLE Productionstep (
	Productionstepid INT (10) NOT NULL auto_increment,
    Productid INT(10),
    Stationid INT(10),
    FOREIGN KEY (Productid) REFERENCES Product (Productid),
    FOREIGN KEY (Stationid) REFERENCES Station (Stationid),
    PRIMARY KEY (Productionstepid)
	);

CREATE TABLE Workstep (
	Workstepid INT(10) NOT NULL auto_increment,
    Productionstepid INT(10),
    FOREIGN KEY (Productionstepid) REFERENCES Productionstep (Productionstepid),
    PRIMARY KEY (Workstepid)
	);
    
CREATE TABLE Typ (
	Typid INT(10) NOT NULL auto_increment,
    Typname VARCHAR (50),
    PRIMARY KEY (Typid)
	);
    
/*Content kann nicht wiederverwendent werden, muss bei jedem Arbeitsschritt neu gespeichert werden*/

CREATE TABLE Content (
	Contentid INT(10) NOT NULL auto_increment,
    Contenttext VARCHAR (500), /*Falls Typ1 als Content gespeichert wird würde es HTML Code sein, ansonsten die Referenz auf den Speicherort des Bildes (können wir natürlich anders lösen!*/
    Workstepid INT(10),
    Typid INT(10),
    FOREIGN KEY (Workstepid) REFERENCES Workstep (Workstepid),
    FOREIGN KEY (Typid) REFERENCES Typ (Typid),
    PRIMARY KEY (Contentid)   	
	);
    
CREATE TABLE User (
	/*Userid INT (10) NOT NULL auto_increment,*/
    Username VARCHAR (50),
    Password VARCHAR (100), /*Bcrypt hat 60 Zeichen */ 
    PRIMARY KEY (Username)    
    ); 
	
INSERT INTO User VALUES
	('default','$2a$04$F0AYTWK54/jMvIY3a2Krm.gqQZwkBaoSyn7wuIjug13FwHoC5ARA.');
    
INSERT INTO Typ(Typname) VALUES
	('Bild'),
    ('Video'),
    ('HTML5');

INSERT INTO Station(Stationname) VALUES
	('Station1'),
	('Station2'),
    ('Station3');
    
INSERT INTO Product(Productname) VALUES
	('Uhr1'),
    ('Uhr2'),
    ('Uhr3');
    
INSERT INTO Productionstep(ProductID, StationID) VALUES
	('1', '1'),
    ('1', '2'),
    ('1', '3'),
    ('2', '1'),
    ('2', '2'),
    ('2', '3');
    
INSERT INTO Workstep(ProductionstepID) VALUES
	('1'),
    ('1'),
    ('2'),
    ('2'),
	('3');

INSERT INTO Content(Contenttext, Workstepid, Typid) VALUES
	('/etc/local/usr/beispieluser/bilder/bild1.jpg', 1, 1),
    ('/etc/local/usr/beispieluser/videos/video1.mpg', 1, 2),
    ('Legen Sie den Zeiger auf die Uhr', 1, 3),
    ('/etc/local/usr/beispieluser/bilder/bild2.jpg', 2, 1),
    ('/etc/local/usr/beispieluser/videos/video2.mpg', 2, 2),
    ('Schrauben Sie irgendeine Schraube fest', 2, 3),
    ('/etc/local/usr/beispieluser/bilder/bild3.jpg', 3, 1),
    ('/etc/local/usr/beispieluser/videos/video3.mpg', 3, 2),
    ('Bringen Sie das Ziffernblatt an der Schraube an', 3, 3); 
    
    
    