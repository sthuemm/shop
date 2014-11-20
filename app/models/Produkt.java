package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Produkt {
		
		
		public double price;
		public String artikelNummer;
		public String artikelBezeichnung;
		public String bildPfad;
		
		public Produkt(){
			this.price = 9.99;
			this.artikelNummer = "1337";
			this.artikelBezeichnung = "default";
			
		}
		
		public Produkt(double price, String artikelNummer, String artikelBezeichnung, String pfad){
			this.price = price;
			this.artikelNummer = artikelNummer;
			this.artikelBezeichnung = artikelBezeichnung;
			this.bildPfad = pfad;
		}
		
		public Produkt(double price, String artikelNummer, String artikelBezeichnung){
			this.price = price;
			this.artikelNummer = artikelNummer;
			this.artikelBezeichnung = artikelBezeichnung;
			
		}
	
}
