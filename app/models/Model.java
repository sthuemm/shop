package models;

import java.util.ArrayList;
import java.util.List;

import models.*;
import play.db.*;

import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Model {

	public static Model sharedInstance = new Model();
	private Kunde kunde = null;
//	private int dbAufrufe = 0;
	private Connection conn = null;

	private Model() {

	}
	
	private Connection connect(){
		
		System.out.println("Connected");
		
		return conn = DB.getConnection() ;
		
	}

//	private Statement dbAufruf() {
//		
//		Statement stmt = null;
//
//		try {
//			System.out.println("Connected wird aufgerufen");
//			stmt = connect().createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println(++dbAufrufe + ". db aufruf");
//
//		return stmt;
//	}

	public void produktInserieren(double preis, String artikelBezeichnung,
			String bildPfad, String kategorie, String lagermenge) {

		try {
			Statement stmt = connect().createStatement();
			int rs = stmt.executeUpdate(
					"insert into produkt values (" + preis + ",'"
							+ "(SELECT MAX (artikelNummer) FROM users)+1"
							+ "','" + artikelBezeichnung + "', '" + bildPfad
							+ "','" + kategorie + "','" + lagermenge + "');");

			
			stmt.close();
			connect().close();
			conn.close();
			System.out.println( rs+ " Produkt wurde hinzugefügt");
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt inserieren");
		}
	}
	
	public void setWarenkorb(String artikelnr) {
		// this.kunde = kunde;

		try {
			Statement stmt = connect().createStatement();
			stmt.executeUpdate(
					"insert into Warenkorb values ('" + kunde.getKundenNummer()
							+ "','" + Integer.parseInt(artikelnr) + "');");

			
			stmt.close();
			connect().close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Warenkorb inserieren");
		}

	}
	public List<Produkt> produktSuchen(String gesuchterWert) {
		// suchergebnisseResetten();
		List<Produkt> gesuchteProdukte = new ArrayList<>();
		try {
			Statement stmt = connect().createStatement();
			
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM produkt WHERE preis ='" + gesuchterWert
							+ "' OR artikelBezeichnung = '" + gesuchterWert
							+ "' OR kategorie = '" + gesuchterWert + "';");

			if (rs == null) {
				rs.close();
				stmt.close();
				connect().close();
				conn.close();
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
					gesuchteProdukte
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}

			}
			rs.close();
			stmt.close();
			connect().close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return gesuchteProdukte;

	}
	public Produkt artikelnummerSuchen(String ausgewaehltesProdukt) {

		try {
			Statement stmt = connect().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM produkt Where artikelNummer ="
							+ ausgewaehltesProdukt + ";");

			if (rs == null) {
				rs.close();
				stmt.close();
				connect().close();
				conn.close();
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
					System.out
							.println(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
					return (new Produkt(preis, artikelNummer,
							artikelBezeichnung, bildPfad, kategorie, lagermenge));
				}
				rs.close();
				stmt.close();
				connect().close();
				conn.close();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}

		ausgewaehltesProdukt = null;
		return null;
	}
	
	public List<Produkt> getProdukte(String wo){
	 List<Produkt> produkte = new ArrayList<Produkt>();
	 try {
		 Statement stmt = connect().createStatement();
		 ResultSet rs = null;
		 
		 switch (wo){
		 
		 case "alle": rs = stmt.executeQuery("SELECT * FROM produkt;");
		 System.out.println("stmt wird aufgerufen");
		 		break;
		 case "aussen": rs = stmt.executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'aussen' ;");
		 System.out.println("stmt wird aufgerufen");
		 		break;
		 case "innen":  rs = stmt.executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'innen' ;");
		 System.out.println("stmt wird aufgerufen");
		 		break;
		 case "brennbar":  rs = stmt.executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'brennbar' ;");
		 System.out.println("stmt wird aufgerufen");
		 		break;
		 case "warenkorb": 
			 if (this.kunde != null) {
				rs = stmt.executeQuery(
				"SELECT DISTINCT p.* FROM produkt p, Warenkorb w WHERE "
				+ "p.artikelNummer = w.artikelNummer and w.kundenNummer = '"
				+ this.kunde.getKundenNummer() + "';");
				System.out.println("Statement wird aufgerufen");
			} else {
				return produkte;
			} break;
			 }			 
		if (rs == null) {
			
			rs.close();
			stmt.close();
			connect().close();
			conn.close();
			System.out.println("Stmt closed");
			return null;
		} else {
			System.out.println("Hier gehts es in while");
			while (rs.next()) {
				double preis = rs.getDouble("preis");
				int artikelNummer = rs.getInt("artikelNummer");
				String artikelBezeichnung = rs
						.getString("artikelBezeichnung");
				String bildPfad = rs.getString("bildPfad");
				String kategorie = rs.getString("kategorie");
				int lagermenge = rs.getInt("lagermenge");
				produkte
						.add(new Produkt(preis, artikelNummer,
								artikelBezeichnung, bildPfad, kategorie,
								lagermenge));
			}
			rs.close();
			stmt.close();
			connect().close();
			conn.close();
			return produkte;
		}
		
	} catch (SQLException e) {
		System.out.println("Fehler Produkt suchen");
		e.printStackTrace();
	}
	  return null;
 }

	public List<Produkt> getProdukteAlle() {

		List<Produkt> produkteAlleList = getProdukte("alle");

		return produkteAlleList;
	}

	public List<Produkt> getProdukteAussen() {

		List<Produkt> produkteAussen = getProdukte("aussen");

		return produkteAussen;
	}

	public List<Produkt> getProdukteInnen() {

		List<Produkt> produkteInnen = getProdukte("innen");

		return produkteInnen;
	}

	public List<Produkt> getProdukteBrennholz() {

		List<Produkt> produkteBrennstoff = getProdukte("brennbar");

		return produkteBrennstoff;
	}
	public List<Produkt> getWarenkorb() {
		
		List<Produkt> produkteWarenkorb = getProdukte("warenkorb");

		return produkteWarenkorb;
	}


	public Kunde getKunde() {
		return kunde;
	}

	public Kunde logout() {
		kunde = null;
		return getKunde();
	}
	public void loginUeberpruefung(Kunde kunde) throws Exception {
		try {
			Statement stmt = connect().createStatement();
			ResultSet rs = stmt.executeQuery(
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
				rs.close();
				stmt.close();
				connect().close();
				conn.close();
				System.out.println("pw falsch");
				throw new wrongPasswordOrUsernameException();
			}
			stmt.close();
			connect().close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler login");
		}
	}

	public void addKunden(Kunde kunde) throws NoSuchAlgorithmException {
		try {
			Statement stmt = connect().createStatement();
			int rs = stmt.executeUpdate(
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
			stmt.close();
			connect().close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Fehler Kunden inserieren");
			e.printStackTrace();
		}
	}


	public void mengeAendern(int artikelnr, int menge) { 
		try {
			Statement stmt = connect().createStatement();
			stmt.executeUpdate(
					"UPDATE produkt SET lagermenge = lagermenge - '" + menge
							+ "' WHERE artikelNummer = '" + artikelnr + "';");
			stmt.close();
			connect().close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Fehler beim ändern der Menge");
			e.printStackTrace();
		}

	}

	public String autovervollstaendigungSuche(String produkt) {
		ArrayList<String> produktbezeichnungen = new ArrayList<>();
		// suchergebnisseResetten();
		try {
			Statement stmt = connect().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT artikelBezeichnung FROM produkt;");

			while (rs.next()) {
				produktbezeichnungen.add(rs.getString("artikelBezeichnung"));

			}
			rs.close();
			stmt.close();
			connect().close();
			conn.close();
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
				System.out.println(auswahl.toString());
				return auswahl.toString();
			}

		} catch (SQLException e) {
			System.out.println("Fehler beim Auslesen der Artikelbezeichnung");
			e.printStackTrace();
		}
		return "";
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


}
