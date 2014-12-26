package models;

import java.util.ArrayList;
import java.util.Arrays;

import models.*;
import play.db.*;

import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Model {

	public static Model sharedInstance = new Model();
	private ArrayList<Produkt> Produkte = new ArrayList<>();
	private ArrayList<Produkt> produkteInnen = new ArrayList<>();
	private ArrayList<Produkt> produkteBrennholz = new ArrayList<>();
	private ArrayList<Produkt> gesuchteProdukte = new ArrayList<>();
	private ArrayList<Produkt> artikel = new ArrayList<>();
	private Kunde kunde = null;

	public void suchergebnisseResetten() {

		gesuchteProdukte.clear();
	}

	private Model() {
		// dbAufruf(); // nee, die Methode gibt nur ein Statement zurück, der
		// Aufruf geschieht immer dann
		// wenn getProdukte() usw aufgerufen wird. Da hängt dbAufruf() immer
		// davor
		// produkteAusDatenbankInListe();

	}

	private Statement dbAufruf() {

		Connection conn = DB.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("db aufruf");
		return stmt;
	}

	public void produktInserieren(double preis, String artikelBezeichnung,
			String bildPfad, String kategorie, int lagermenge) {

		try {

			dbAufruf().executeUpdate("insert into produkt values (" + preis
							+ ",(SELECT MAX(artikelNummer) from produkt)+1,'"
							+ artikelBezeichnung + "', '" + bildPfad + "','"
							+ kategorie + "','" + lagermenge + "');");

			dbAufruf().close();
			System.out.println("Produkt wurde hinzugefügt");
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt inserieren");
		}
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
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
					int lagermenge = rs.getInt("lagermenge");
					gesuchteProdukte.add(new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie, lagermenge));
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

	public Produkt[] getProdukte() {

		ArrayList<Produkt> produkteAlleList = new ArrayList<>();
		Produkt[] produkteAlleArray;
		try {
			ResultSet rs = dbAufruf().executeQuery("SELECT * FROM produkt;");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
					produkteAlleList.add(new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie));
				}
				produkteAlleArray = produkteAlleList
						.toArray(new Produkt[produkteAlleList.size()]);

				rs.close();
				dbAufruf().close();
				return produkteAlleArray;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return null;
	}

	public Produkt[] getProdukteAussen() {
		ArrayList<Produkt> produkteAussen = new ArrayList<>();
		Produkt[] produkteAussenArray;
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'aussen' ;");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
<<<<<<< HEAD
					produkteAussen.add(new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie));
=======
					int lagermenge = rs.getInt("lagermenge");
					produkteAussen.add(new Produkt(preis, artikelNummer, artikelBezeichnung, bildPfad, kategorie, lagermenge));
>>>>>>> origin/master
				}
				produkteAussenArray = produkteAussen
						.toArray(new Produkt[produkteAussen.size()]);

				rs.close();
				dbAufruf().close();
				return produkteAussenArray;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return null;
	}

	public Produkt[] getProdukteInnen() {
		ArrayList<Produkt> produkteInnen = new ArrayList<>();
		Produkt[] produkteInnenArray;
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'innen' ;");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
<<<<<<< HEAD
					produkteInnen.add(new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie));
=======
					int lagermenge = rs.getInt("lagermenge");
					produkteInnen.add(new Produkt(preis, artikelNummer, artikelBezeichnung, bildPfad, kategorie, lagermenge));
>>>>>>> origin/master
				}
				produkteInnenArray = produkteInnen
						.toArray(new Produkt[produkteInnen.size()]);

				rs.close();
				dbAufruf().close();
				return produkteInnenArray;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return null;
	}

	public void setProdukteInnen(ArrayList<Produkt> produkteInnen) {
		this.produkteInnen = produkteInnen;
	}

	public Produkt[] getProdukteBrennholz() {
		ArrayList<Produkt> produkteBrennstoff = new ArrayList<>();
		Produkt[] produkteBrennstoffe;
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'brennbar' ;");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
<<<<<<< HEAD
					produkteBrennstoff.add(new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie));
=======
					int lagermenge = rs.getInt("lagermenge");
					produkteBrennstoff.add(new Produkt(preis, artikelNummer, artikelBezeichnung, bildPfad, kategorie, lagermenge));
>>>>>>> origin/master
				}
				produkteBrennstoffe = produkteBrennstoff
						.toArray(new Produkt[produkteBrennstoff.size()]);

				rs.close();
				dbAufruf().close();
				return produkteBrennstoffe;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return null;
	}

	public void setProdukteBrennholz(ArrayList<Produkt> produkteBrennholz) {
		this.produkteBrennholz = produkteBrennholz;
	}

	public Produkt[] Artikel() {
		try {
			ResultSet rs = dbAufruf().executeQuery("SELECT * FROM produkt;");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
<<<<<<< HEAD
					artikel.add(new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie));
=======
					int lagermenge = rs.getInt("lagermenge");
					produkteAlleList.add(new Produkt(preis, artikelNummer, artikelBezeichnung, bildPfad, kategorie, lagermenge));
>>>>>>> origin/master
				}
				rs.close();
				dbAufruf().close();

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}

		Produkt[] artikelAll = artikel.toArray(new Produkt[artikel.size()]);
		return artikelAll;
	}

	public String autovervollstaendigung(String produkt) {
		ArrayList<String> produktbezeichnungen = new ArrayList<>();
		suchergebnisseResetten();
		try {
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
			if (null != eingabe && 0 < eingabe.trim().length()) {
				if (!sorted) {
					java.util.Arrays.sort(meinTextArray);
					sorted = true;
				}
				StringBuffer auswahl = new StringBuffer();
				boolean resultFound = false;
				for (int i = 0; i < meinTextArray.length; i++) {
					if (meinTextArray[i].toUpperCase().startsWith(
							eingabe.toUpperCase())) {
						auswahl.append(meinTextArray[i]).append(";");
						resultFound = true;
					} else {
						if (resultFound)
							break;
					}
				}
				if (0 < auswahl.length()) {
					auswahl.setLength(auswahl.length() - 1);
				}
				return auswahl.toString();
			}

		} catch (SQLException e) {
			System.out.println("Fehler beim Auslesen der Artikelbezeichnung");
			e.printStackTrace();
		}
		return "";
	}

	public void loginUeberpruefung(Kunde kunde) throws Exception {
		try {

			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM users WHERE username ='"
							+ kunde.getBenutzername() + "'AND pass = '"
							+ verschluesselPW(kunde.getPasswort()) + "';");

			if (rs.next()) {
				System.out.println("pw richtig");

				boolean isAdmin = false;

				if (rs.getString("admin").equals("ja")) {
					isAdmin = true;
				}
				this.kunde = new Kunde(
						// instanziiert Kunden der aktuell eingeloggt ist
						rs.getString("kundenNummer"), rs.getString("vorname"),
						rs.getString("anrede"), rs.getString("nachname"),
						rs.getString("username"), rs.getString("email"),
						rs.getString("str"), rs.getString("hausnr"),
						rs.getString("plz"), rs.getString("ort"),
						rs.getString("telefonnr"), rs.getString("pass"),
						isAdmin);
			} else {
				System.out.println("pw falsch");
				throw new wrongPasswordOrUsernameException();
			}
			rs.close();
			dbAufruf().close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler login");
		}
	}

	public void addKunden(Kunde kunde) throws NoSuchAlgorithmException {
		try {
			int rs = dbAufruf().executeUpdate(
					"insert into users values((SELECT MAX (kundenNummer) FROM users)+1, '"
							+ kunde.getAnrede() + "', '" + kunde.getVorname()
							+ "', '" + kunde.getNachname() + "', '"
							+ kunde.getBenutzername() + "', '"
							+ kunde.getEmail() + "', '" + kunde.getStrasse()
							+ "', '" + kunde.getHausnummer() + "', '"
							+ kunde.getPlz() + "', '" + kunde.getOrt() + "', '"
							+ kunde.getTelefonnummer() + "', '"
							+ verschluesselPW(kunde.getPasswort())
							+ "','nein');");
			System.out.println(rs + " Kunde wurde hinzugefügt");
			dbAufruf().close();
		} catch (SQLException e) {
			System.out.println("Fehler Kunden inserieren");
			e.printStackTrace();
		}
	}

	public Kunde getKunde() {
		return kunde;
	}

	public Kunde logout() {
		kunde = null;

		return getKunde();
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public static String verschluesselPW(String pass)
			throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(pass.getBytes());
		String passwortString = "";
		for (byte b : md.digest()) {
			passwortString += Byte.toString(b);
		}

		return passwortString;
	}
<<<<<<< HEAD
=======
	
	
	public Produkt[] Artikel(){
			try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt;");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
					int lagermenge = rs.getInt("lagermenge");
					artikel.add(new Produkt(preis, artikelNummer, artikelBezeichnung, bildPfad, kategorie, lagermenge));
				}
				rs.close();
				dbAufruf().close();
			
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
	
	Produkt[] artikelAll = artikel.toArray(new Produkt[artikel.size()]);
	return artikelAll;
	}
	
>>>>>>> origin/master
}
