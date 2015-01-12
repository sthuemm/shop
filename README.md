#Holzshop
<http://www.amazon.de/>
## Projektbeschreibung

Ein Shop zur bestellen von verschiedenen Holzprodukte. Es beinhaltet entweder Produkte die im Außenbereich, 
Innenbereich zu finden sind oder Produkte die zum Heizen benutzt werden.

====

## Anforderungsanalyse

![Use-Case Diagramm](/public/images/UseCase.jpg "Use-Case")

====
## Erste Ideen

- Login / Registrieren
- Zahlung
- Rezessionen
- Testergebnisse
- Warenkorb
- Suche
- Kategorien
- Kundenservice
- Neuheiten
- Sprachen

![Main](/public/images/wireframe/main.JPG "Main")
![Kategorien](/public/images/wireframe/kategorien.JPG "Kategorien")
![Artikelseite](/public/images/wireframe/artikel.JPG "Artikelseite")
![Login](/public/images/wireframe/login.JPG "Login")

====

## Architektur

![Klassendiagramm](/public/images/Klassendiagramm.jpg "Klassendiagramm")

====

## Verwendete Technologien

HTML5 - für die Erstellung der Webseiten; 
CSS - Design der Artikel; 
Bootstrap - Design der Webseiten;
JavaScript - Registrierung;
jQuery - Animation der Kategorien in der main und in neuheiten;
Postgresql - Datenbank (Kunde, Produkt, Warenkorb); 
Java mit Playframework - bei der modelierung von Model Application ; 
Scala - vom Webseeiten Produkt oder Kunde an der Application weiterzugeben, Übergabe der Werte aus der Registrierung;
AJAX - für die Suche entweder nach Preis, Kategorie oder Artikelname; 
WebSockets - Änderung der Produktenmänge; 
JSON. 

====

## Gelerntes

Viel.

====

## Probleme

Die grössten Probleme waren bei der Playframework Einführung, Datenbanken locks und Deployment auf Heroku.

====

## Code

