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
		dbAufruf();
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

	public void produkteInDatenbankHinzufuegen(Produkt produkt) {						//die Methode löschen wenn Produkte in DB sind
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
		produkteInDatenbankHinzufuegen(new Produkt(99.99, "1", "ein Gartenzaun",
				"images/Palissaden.jpg", "aussen"));
		produkteInDatenbankHinzufuegen(new Produkt(119.99, "2", "Palisaden fuer den Garten",
				"images/Pfaehle.jpg", "aussen"));
		produkteInDatenbankHinzufuegen(new Produkt(249.99, "3", "Terassenbelaege",
				"images/Terrasse.jpg", "aussen"));
		produkteInDatenbankHinzufuegen(new Produkt(49.99, "4", "Terassenmoebel",
				"images/bruecke.jpg", "aussen"));

		produkteInDatenbankHinzufuegen(new Produkt(29.99, "5", "Esstisch", "innen"));
		produkteInDatenbankHinzufuegen(new Produkt(19.99, "6", "Stuhl", "innen"));
		produkteInDatenbankHinzufuegen(new Produkt(44.99, "7", "Vertaefelung", "innen"));

		produkteInDatenbankHinzufuegen(new Produkt(4.99, "8", "Echtes Kiefernholz", "brennbar"));
		produkteInDatenbankHinzufuegen(new Produkt(5.99, "9", "Echtes Buchenholz", "brennbar"));
		produkteInDatenbankHinzufuegen(new Produkt(2.99, "10", "Super brennbare Spanplatte",
				"brennbar"));
	}

	public Produkt[] produktSuchen(String gesuchterWert) {
		suchergebnisseResetten();

		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE preice ='" + gesuchterWert
							+ "' OR artikelBezeichnung = '" + gesuchterWert
							+ "' OR kategorie = '" + gesuchterWert + "';");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double r1 = rs.getDouble("preice");
					String r2 = rs.getString("artikelNummer");
					String r3 = rs.getString("artikelBezeichnung");
					String r4 = rs.getString("bildPfad");
					String r5 = rs.getString("kategorie");
					gesuchteProdukte.add(new Produkt(r1, r2, r3, r4, r5));
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

			ResultSet rs = dbAufruf().executeQuery(
					"insert into produkt values (" + preis + ",'"
							+ artikelNummer + "','" + artikelBezeichnung
							+ "', '" + bildPfad + "','" + kategorie + "';");
			rs.close();
			dbAufruf().close();

		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt inserieren");
		}
	}

	public Kunde loginUeberpruefung(String user, String userPass) {
		try {

			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM users WHERE vorname ='" + user
							+ "'AND pass = '" + userPass + "';");
			String r1 = "";
			String r2 = "";
			int r3 = 0;
			String r4 = "";
			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					r1 = rs.getString("vorname");
					r2 = rs.getString("vorname");
					r3 = rs.getInt("kundid");
					r4 = rs.getString("pass");
				}
				rs.close();
				dbAufruf().close();
				return kunde = new Kunde(r1, r2, r3, r4, true);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler login");
		}
		return null;
	}

	public void addKunden(String vorname, String name, int kundenNummer,
			String passwort, boolean isAdmin) {
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"insert into users values('" + vorname + "','" + name
							+ "', " + kundenNummer + ", '" + passwort + "');");
			rs.close();
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

}
