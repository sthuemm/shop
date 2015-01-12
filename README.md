#Holzshop
<http://www.holzshop.herokuapp.com>
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

HTML - für die Erstellung der Webseiten;

CSS - Design Artikel; 

Bootstrap - Design der Webseiten;

JavaScript - überall (Registrierung, artikel usw.);

jQuery - Animation der Kategorien in der main und in neuheiten;

Postgresql - Datenbank (Kunde, Produkt, Warenkorb); 

Java mit Playframework - bei der modelierung von Model, Application; 

Scala - einfache Lögik in den views;

AJAX - für die Suche entweder nach Preis, Kategorie oder Artikelname;

WebSockets - Änderung der Produktenmenge;
 
JSON - Übergabe der aktuellen Menge der einzelnen Produkte.  

====

## Gelerntes

Viel. Alles was im "Verwendete Technologien" steht. 

====

## Probleme

Die grössten Probleme waren bei der **Datenbanken locks**, Playframework Einführung, Deployment auf Heroku.

====

## Code

Siehe 'code'.
