
DROP DATABASE Leanlabworking;

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
	('default','$2a$04$F0AYTWK54/jMvIY3a2Krm.gqQZwkBaoSyn7wuIjug13FwHoC5ARA.', 0);
    
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
    	('1', '3');
    
INSERT INTO Workstep(ProductionstepID) VALUES
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1'),
	('1')
    ;

		
		INSERT INTO Content(Contenttext, WorkstepID, TypID) VALUES
('Bereiten Sie alle nötigen Werkzeuge und die Bestandteile der Uhr vor',1,3),
('Hier sehen Sie die Uhr  im Vorher/Nachher-Vergleich',2,3),
('Los gehts: Nehmen Sie das schwarze Uhrwerk und die silberne Scheibe zur Hand',3,3),
('Legen Sie die Scheibe auf das Uhrwerk',4,3),
('Nehmen Sie den schwarzen Ring zur Hand',5,3),
('Legen Sie den Ring auf die silberne Scheibe',6,3),
('Nehmen Sie die silberne Standfigur und montieren Sie das Uhrwerk an der Hinterseite',7,3),
('Nehmen Sie die  blaue Scheibe zur Hand. Sie wird das Ziffernblatt der Uhr.',8,3),
('Legen Sie die blaue Scheibe auf die Vorderseite der Standfigur und nehmen Sie den goldenen Ring',9,3),
('Montieren Sie das Uhrwerk auf der Standfigur mithilfe des Ringes, indem Sie beide Teile fest zusammenschrauben',10,3),
('Hier sehen Sie das bisherige Zwischenergebnis',11,3),
('In den nachfolgenden Schritten werden die Uhrzeiger hergestellt. Nehmen Sie zuerst den roten Sekundenzeiger zur Hand',12,3),
('Messen Sie den Sekundenzeiger mithilfe des Lineals auf 3,5 cm ab',13,3),
('Kürzen Sie den Zeiger mithilfe der Zange auf 3,5 cm',14,3),
('Entfernen Sie auch den überschüssigen, oberen Teil des Zeigers',15,3),
('Feilen Sie die unebene Schnittstelle glatt ab',16,3),
('Feilen Sie ebenso die Schnittstelle am oberen Teil glatt ab',17,3),
('Nehmen Sie den mittleren schwarzen Minutenzeiger mit dem kleineren Loch',18,3),
('Messen Sie den Minutenzeiger mithilfe des Lineals auf 4 cm ab',19,3),
('Kürzen Sie den Zeiger mithilfe der Zange auf 4 cm',20,3),
('Entfernen Sie auch den überschüssigen, oberen Teil des Zeigers',21,3),
('Feilen Sie die beiden unebenen Schnittstellen glatt ab',22,3),
('Nehmen Sie den schwarzen Stundenzeiger mit dem größeren Loch',23,3),
('Messen Sie den Stundenzeiger mithilfe des Lineals auf 2,5 cm ab',24,3),
('Kürzen Sie den Zeiger mithilfe der Zange auf 2,5 cm',25,3),
('Entfernen Sie auch den überschüssigen, oberen Teil des Zeigers',26,3),
('Feilen Sie die beiden unebenen Schnittstellen glatt ab',27,3),
('Sie haben nun alle 3 Uhrzeiger bereit ',28,3),
('Legen Sie die Uhrzeiger nacheinander auf die blaue Scheibe: zuerst den kleinen Stundenzeiger, dann den etwas größeren Minutenzeiger und befestigen Sie das Ganze mit dem roten Sekundenzeiger',29,3),
('So sieht Ihre fertige Uhr aus',30,3);