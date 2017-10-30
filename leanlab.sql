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
    
CREATE TABLE TYP (
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
    

