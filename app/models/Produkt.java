package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Produkt {
		
		
		public double price;
		public int artikelNummer;
		public String artikelBezeichnung;
		
		public Produkt(){
			this.price = 9.99;
			this.artikelNummer = 1337;
			this.artikelBezeichnung = "default";
			
		}
		
		public Produkt(double price, int artikelNummer, String artikelBezeichnung){
			this.price = price;
			this.artikelNummer = artikelNummer;
			this.artikelBezeichnung = artikelBezeichnung;
		}
		
	
	
}
