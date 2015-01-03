package models;



//import play.*;
//import play.mvc.*;
//import views.html.*;
//import play.db.ebean.Model;

public class Produkt  {

	public double preis;
	public String artikelNummer;
	public String artikelBezeichnung;
	public String bildPfad;
	public String kategorie;
	public int lagermenge;
	public int bestellmenge = 0;

	public Produkt() {}	// Standardkonstruktor

	/*
	 * Konstruktor ohne Bestellmenge
	 */
	public Produkt(double price, String artikelNummer,
			String artikelBezeichnung, String pfad, String kategorie, int lagermenge) {
		this.preis = price;
		this.artikelNummer = artikelNummer;
		this.artikelBezeichnung = artikelBezeichnung;
		this.bildPfad = pfad;
		this.kategorie = kategorie;
		this.lagermenge = lagermenge;
		
	}
	
	/*
	 * Konstruktor mit Bestellmenge
	 */
	public Produkt(double price, String artikelNummer,
			String artikelBezeichnung, String pfad, String kategorie, int lagermenge, int bestellmenge) {
		this.preis = price;
		this.artikelNummer = artikelNummer;
		this.artikelBezeichnung = artikelBezeichnung;
		this.bildPfad = pfad;
		this.kategorie = kategorie;
		this.lagermenge = lagermenge;
		this.bestellmenge = bestellmenge;
	}
	

	@Override
	public String toString() {
		return "Artikelnummer:"+artikelNummer+" Preis: "+preis;
	}

	
	
	
}
