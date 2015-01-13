#Holzshop
<http://www.holzshop.herokuapp.com>
## Projektbeschreibung

Ein Shop für verschiedene Holzprodukte. Er beinhaltet Holzprodukte für den Innen- und Aussenbereich sowie Brennholz.

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

HTML - zur Erstellung des Grundgerüsts der Webseite;

CSS mit Bootstrap - Design der Webseite;

JavaScript mit jQuery - Animation, Fehlerdarstellung im Registrierungsprozess;

sqlite - Datenbank (Kunde, Produkt, Warenkorb); 

Play Framework mit Java für Backend;

Scala - einfache Logik in den Views;

AJAX - Um im Suchfenster Vorschläge direkt auf der Seite ohne Nachladen darstellen zu können;

WebSockets - Um anderen Kunden jede Änderung der Restmengen ohne Reload der Seite darzustellen;
 
JSON - Datenaustauschformat zwischen Websocket und View.  

====

## Gelerntes

Viel. Alles was im "Verwendete Technologien" steht. 

====

## Probleme

Häufig vorkommende Probleme waren Datenbank-Locks, wobei der Grund meist schnell gefunden werden konnte. 
Deployment auf Heroku hat am meisten Zeit beansprucht, da die Fehlermeldungen für uns nicht zielführend waren

====

## Code

Siehe `code`.
