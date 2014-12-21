package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Produkt {
		
		
		public double preis;
		public String artikelNummer;
		public String artikelBezeichnung;
		public String bildPfad;
		public String kategorie;
		
		
		
		
		
		public Produkt(){
			this.preis = 9.99;
			this.artikelNummer = "1337";
			this.artikelBezeichnung = "default";
			
		}
		
		public Produkt(double price, String artikelNummer, String artikelBezeichnung, String pfad, String kategorie){
			this.preis = price;
			this.artikelNummer = artikelNummer;
			this.artikelBezeichnung = artikelBezeichnung;
			this.bildPfad = pfad;
			this.kategorie = kategorie;
		}
		
		public Produkt(double price, String artikelNummer, String artikelBezeichnung, String kategorie){
			this.preis = price;
			this.artikelNummer = artikelNummer;
			this.artikelBezeichnung = artikelBezeichnung;
			this.kategorie = kategorie;
			
		}
	
}
