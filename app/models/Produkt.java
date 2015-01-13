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
	public String statusBestellung = "";
	public String bestellDatum;

	public Produkt() {}	// Standardkonstruktor

	/*
	 * Konstruktor ohne Bestellmenge
	 */
	public Produkt(double preis, String artikelNummer,
			String artikelBezeichnung, String pfad, String kategorie, int lagermenge) {
		this.preis = preis;
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
	
	/*
	 * Konstruktor für Artikel in der Übersicht des Kundenkontos
	 */
	
	public Produkt(String artikelNummer, String artikelBezeichnung, int menge, String status, double preis, String bestellDatum){
		this.artikelNummer = artikelNummer;
		this.artikelBezeichnung = artikelBezeichnung;
		this.preis = preis;
		this.bestellmenge = menge;
		this.statusBestellung = status;
		this.bestellDatum = bestellDatum;
	}

	@Override
	public String toString() {
		return "Artikelnummer:"+artikelNummer+" Preis: "+preis;
	}

	
	
	
}
