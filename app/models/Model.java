package models;

import java.util.ArrayList;

import models.*;
import play.db.*;

import java.sql.*;

public class Model {

	public static Model sharedInstance = new Model();
	private ArrayList<Produkt> Produkte = new ArrayList<>();
	private ArrayList<Produkt> produkteAussen = new ArrayList<>();
	private ArrayList<Produkt> produkteInnen = new ArrayList<>();
	private ArrayList<Produkt> produkteBrennholz = new ArrayList<>();
	private ArrayList<Produkt> gesuchteProdukte = new ArrayList<>();
	private Kunde kunde = null;

	public void suchergebnisseResetten() {

		gesuchteProdukte.clear();

	}

	private Model() {
		//dbAufruf();      // brauch man den Aufruf?
		produkteAusDatenbankInListe();
		
	}

	private Statement dbAufruf() {

		Connection conn = DB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	public void produkteInDatenbankHinzufuegen(Produkt produkt) { // die Methode
																	// löschen
																	// wenn
																	// Produkte
																	// in DB
																	// sind
		Produkte.add(produkt);
		switch (produkt.kategorie) {
		case ("aussen"):
			produkteAussen.add(produkt);
			break;
		case ("innen"):
			produkteInnen.add(produkt);
			break;
		case ("brennbar"):
			produkteBrennholz.add(produkt);
			break;
		}
	}

	public void produkteAusDatenbankInListe() {
		produkteInDatenbankHinzufuegen(new Produkt(99.99, "1",
				"ein Gartenzaun", "images/Palissaden.jpg", "aussen"));
		produkteInDatenbankHinzufuegen(new Produkt(119.99, "2",
				"Palisaden fuer den Garten", "images/Pfaehle.jpg", "aussen"));
		produkteInDatenbankHinzufuegen(new Produkt(249.99, "3",
				"Terassenbelaege", "images/Terrasse.jpg", "aussen"));
		produkteInDatenbankHinzufuegen(new Produkt(49.99, "4",
				"Terassenmoebel", "images/bruecke.jpg", "aussen"));

		produkteInDatenbankHinzufuegen(new Produkt(29.99, "5", "Esstisch",
				"innen"));
		produkteInDatenbankHinzufuegen(new Produkt(19.99, "6", "Stuhl", "innen"));
		produkteInDatenbankHinzufuegen(new Produkt(44.99, "7", "Vertaefelung",
				"innen"));

		produkteInDatenbankHinzufuegen(new Produkt(4.99, "8",
				"Echtes Kiefernholz", "brennbar"));
		produkteInDatenbankHinzufuegen(new Produkt(5.99, "9",
				"Echtes Buchenholz", "brennbar"));
		produkteInDatenbankHinzufuegen(new Produkt(2.99, "10",
				"Super brennbare Spanplatte", "brennbar"));
	}

	public Produkt[] produktSuchen(String gesuchterWert) {
		suchergebnisseResetten();

		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE preis ='" + gesuchterWert
							+ "' OR artikelBezeichnung = '" + gesuchterWert
							+ "' OR kategorie = '" + gesuchterWert + "';");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					String artikelNummer = rs.getString("artikelNummer");
					String artikelBezeichnung = rs.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
					gesuchteProdukte.add(new Produkt(preis, artikelNummer, artikelBezeichnung, bildPfad, kategorie));
				}
				rs.close();
				dbAufruf().close();

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return getGesuchteProdukte();

	}

	public Produkt[] getGesuchteProdukte() {
		Produkt[] gesuchteProd = gesuchteProdukte
				.toArray(new Produkt[gesuchteProdukte.size()]);
		return gesuchteProd;
	}

	public void produktInserieren(double preis, String artikelNummer,
			String artikelBezeichnung, String bildPfad, String kategorie) {

		try {

			dbAufruf().executeUpdate("insert into produkt values ("
							+ preis + ",'"
							+ artikelNummer + "','" 
							+ artikelBezeichnung+ "', '" 
							+ bildPfad + "','" 
							+ kategorie + "';");
			dbAufruf().close();

		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt inserieren");
		}
	}

	public Kunde loginUeberpruefung(String user, String userPass) {
		try {
			System.out.println("login");
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM users WHERE vorname ='" + user
							+ "'AND pass = '" + userPass + "';");
			
			String vorname = "";
			String benutzerName = "";
			int kundenNummer = 0;
			String passwort = "";
			boolean isAdmin = false;
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					vorname = rs.getString("vorname");
					benutzerName = rs.getString("vorname");
					kundenNummer = rs.getInt("kundenNummer");
					passwort = rs.getString("pass");
					if(rs.getString("admin").equals("ja")){
						isAdmin = true;
					}
					
				}
				rs.close();
				dbAufruf().close();
				System.out.println("Admin: "+isAdmin);
				return kunde = new Kunde(vorname, benutzerName, kundenNummer, passwort, isAdmin);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler login");
		}
		return null;
	}

	public int NewKundennr() {

		int kundennr = 0;
		ResultSet rs;
		try {
			rs = dbAufruf().executeQuery("select MAX(kundenNummer) from users");
			if(rs.next()){
				kundennr = rs.getInt("kundenNummer") + 1;
			}
			System.out.println(kundennr);
			dbAufruf().close();
//			kundennr = kundennr + 1;
		} catch (SQLException e1) {
			System.out.println("Fehler KundenID");
			e1.printStackTrace();
		}
		
		return kundennr;
	}

	public void addKunden(String Vorname, String Nachname, String Username,
			String Email, String Str, String Hausnr, String Plz, String Ort,
			String Telefon, String Passwort) {
		try {
			int rs = dbAufruf().executeUpdate(
					"insert into users values(" + NewKundennr() + ", '"
							+ Vorname + "', '" + Nachname + "', '" + Username
							+ "', '" + Email + "', '" + Str + "', '" + Hausnr
							+ "', '" + Plz + "', '" + Ort + "', '" + Telefon
							+ "', '" + Passwort + "','nein');");
			System.out.println(rs + "Kunde wurde hinzugefügt");
			dbAufruf().close();
		} catch (SQLException e) {
			System.out.println("Fehler Kunden inserieren");
			e.printStackTrace();
		}
	}

	public Produkt[] getProdukteAussen() {
		Produkt[] produkteAussenArray = produkteAussen
				.toArray(new Produkt[produkteAussen.size()]);
		return produkteAussenArray;
	}

	public void setProdukteAussen(ArrayList<Produkt> produkteAussen) {
		this.produkteAussen = produkteAussen;
	}

	public Produkt[] getProdukteInnen() {
		Produkt[] produkteInnenArray = produkteInnen
				.toArray(new Produkt[produkteInnen.size()]);
		return produkteInnenArray;
	}

	public void setProdukteInnen(ArrayList<Produkt> produkteInnen) {
		this.produkteInnen = produkteInnen;
	}

	public Produkt[] getProdukteBrennholz() {
		Produkt[] brennholzArray = produkteBrennholz
				.toArray(new Produkt[produkteBrennholz.size()]);
		return brennholzArray;
	}

	public void setProdukteBrennholz(ArrayList<Produkt> produkteBrennholz) {
		this.produkteBrennholz = produkteBrennholz;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public Kunde logout() {
		kunde = null;

		return getKunde();
	}

	public Produkt[] getProdukte() {

		Produkt[] produkteArray = Produkte
				.toArray(new Produkt[Produkte.size()]);
		return produkteArray;
	}
	
	public String autovervollstaendigung(String produkt){
	ArrayList<String> produktbezeichnungen = new ArrayList<>();
	 suchergebnisseResetten();
		try{
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT artikelBezeichnung FROM produkt;");

				while (rs.next()) {
				produktbezeichnungen.add(rs.getString("artikelBezeichnung"));
				
				}
				rs.close();
				dbAufruf().close();	
				  boolean sorted = false;
				  String[] meinTextArray = produktbezeichnungen
								.toArray(new String[produktbezeichnungen.size()]);
				  String eingabe = produkt;
				  if( null != eingabe && 0 < eingabe.trim().length() ) {
					if( !sorted ) {
					  java.util.Arrays.sort( meinTextArray );
					  sorted = true;
					}
					StringBuffer auswahl = new StringBuffer();
					boolean resultFound = false;
					for( int i=0; i<meinTextArray.length; i++ ) {
					  if( meinTextArray[i].startsWith( eingabe ) ) {
						auswahl.append( meinTextArray[i] ).append( ";" );
						resultFound = true;
					  } else {
						if( resultFound ) break;
					  }
					}
					if( 0 < auswahl.length() ) {
					  auswahl.setLength( auswahl.length() - 1 );
					}
					return auswahl.toString() ;
				  }
					
					
				}catch(SQLException e){
				System.out.println("Fehler beim Auslesen der Artikelbezeichnung");
						e.printStackTrace();
				}
				return null;
	}
}
