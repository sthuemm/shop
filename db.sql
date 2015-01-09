BEGIN TRANSACTION;
CREATE TABLE "produkt" (
	`preis`	double NOT NULL,
	`artikelNummer`	INTEGER NOT NULL,
	`artikelBezeichnung`	varchar(50) NOT NULL,
	`bildPfad`	varchar(20),
	`kategorie`	varchar(20) NOT NULL,
	`lagermenge`	INTEGER,
	PRIMARY KEY(artikelNummer)
);
INSERT INTO `produkt` VALUES('99.99',1,'Gartenzaun','images/Palissaden.jpg','aussen',90);
INSERT INTO `produkt` VALUES('119.99',2,'Palisaden fuer den Garten','images/Pfaehle.jpg','aussen',98);
INSERT INTO `produkt` VALUES('249.99',3,'Terassenbelaege','images/Terrasse.jpg','aussen',7);
INSERT INTO `produkt` VALUES('49.99',4,'Terassenmoebel','images/bruecke.jpg','aussen',9);
INSERT INTO `produkt` VALUES('49.99',5,'Esstisch','images/esstisch.jpg','innen',60);
INSERT INTO `produkt` VALUES('4.99',6,'Pellets','images/Pellets.jpg','brennbar',79);
INSERT INTO `produkt` VALUES('22.99',7,'Holzfahrrad','images/Holzfahrrad.jpg','aussen',9);
INSERT INTO `produkt` VALUES('19.99',12,'Stuhl','images/stuhl.jpg','innen',80);
INSERT INTO `produkt` VALUES('44.99',13,'Vertäfelung','images/Vertaefelung.jpg','innen',90);
INSERT INTO `produkt` VALUES('4.99',14,'Echtes Kiefernholz','images/Kiefernholz.jpg','brennbar',4);
INSERT INTO `produkt` VALUES('5.99',15,'Echtes Buchenholz','images/Buchenholz.jpg','brennbar',5);
CREATE TABLE "Warenkorb" (
	`wkn`	INTEGER NOT NULL,
	`kundenNummer`	INTEGER NOT NULL,
	`artikelNummer`	INTEGER NOT NULL,
	`bestellmenge`	INTEGER NOT NULL,
	PRIMARY KEY(wkn)
);
INSERT INTO `Warenkorb` VALUES(1,0,7,1);
INSERT INTO `Warenkorb` VALUES(2,0,14,1);
INSERT INTO `Warenkorb` VALUES(3,1011,1,1);
INSERT INTO `Warenkorb` VALUES(4,1011,1,1);
CREATE TABLE "Kunde" (
	`kundenNummer`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`anrede`	TEXT,
	`vorname`	varchar(20) NOT NULL,
	`nachname`	varchar(20) NOT NULL,
	`benutzername`	TEXT NOT NULL UNIQUE,
	`email`	TEXT,
	`strasse`	TEXT NOT NULL,
	`hausnummer`	TEXT NOT NULL,
	`plz`	TEXT NOT NULL,
	`ort`	TEXT NOT NULL,
	`telefon`	TEXT,
	`passwort`	varchar(10) NOT NULL,
	`isAdmin`	TEXT
);
INSERT INTO `Kunde` VALUES(1000,'Herr','Dumitru','Mihu','Dumitru','ist@bekannt.de','Brauneggerstrasse',55,78467,'Konstanz','','-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','ja');
INSERT INTO `Kunde` VALUES(1001,'Herr','Georg','Mohr','Georg','ist@bekannt.de','Brauneggerstrasse',55,'78467
','Konstanz','','-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','ja');
INSERT INTO `Kunde` VALUES(1002,'Herr','Sebastian','Thuemmel','Basti','ist@bekannt.de','Brauneggerstrasse',55,78467,'Konstanz','','-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','ja');
INSERT INTO `Kunde` VALUES(1003,'Herr','Max','Mustermann','Maxi','Max@Mustermann.de','Mustermannstrasse',1337,78467,'Musterstadt','0190/666666','-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1004,'Herr','Bernd','Brot','broti','ich@du.de','Pupsweg',13,78467,'Konstanz',0190,'-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1005,'Herr','Jim','Levenstein','Kuchenficker','hab@ich.de','weiß','ich','nicht','stadt',0191,'-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1006,'Frau','Gina','Wilde','Gina','asd@aa.de','Funstr','22a',74689,'KN',92381,'-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1007,'Frau','Anna','huh','AM','gsdad@asas.de','sadas',12,2112,'asasd','','-376-17122789612731-52-11010-4250-102109-14-33-103-92-24','nein');
INSERT INTO `Kunde` VALUES(1008,'F','Agnes','Klein','Agi','Agnes@Klein.de','hier',17,78467,'Konstanz','','-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1009,'H','depp','vom','depp','gibts@nicht.de','dienst',1337,12,'hier',01901,'-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1010,'Herr','sdf','sdf','deppi','sdf','sdf','sdf','sdf','sdf','sdfsdf','-8774-113-27-52-79-101-9028768115-45-111-23-121-10447-69-45','nein');
INSERT INTO `Kunde` VALUES(1011,'Frau','Agnes','Klein','Ackness','senga1@gmx.net','Buhlenweg 38','Buhlenweg 38',78467,'Konstanz','+4915115331366','113-60293-39-119123547962-69-76447279-1261012128','nein');
CREATE TABLE "Bestellung" (
	`kundenNummer`	INTEGER NOT NULL,
	`artikelNummer`	INTEGER NOT NULL,
	PRIMARY KEY(kundenNummer,artikelNummer)
);
;
;
COMMIT;
