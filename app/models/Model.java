package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import models.*;
import play.db.*;

import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Model extends Observable {

	public static Model sharedInstance = new Model();
	private Kunde kunde = new Kunde();
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

	private Model() {
	}

	private String getTime() {
		Date currentTime = new Date(System.currentTimeMillis());
		return formatter.format(currentTime);
	}

	public void produktInserieren(double preis, String artikelBezeichnung,
			String bildPfad, String kategorie, String lagermenge) {

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into produkt values (" + preis + ","
					+ "(SELECT MAX (artikelNummer) FROM produkt)+1,'"
					+ artikelBezeichnung + "'," + " '" + bildPfad + "','"
					+ kategorie + "'," + "" + lagermenge + ");");
			System.out.println(getTime() + ":" + stmt
					+ "Produkt(e) hinzugefügt");

			stmt.close(); conn.close();

		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(getTime() + ": Fehler Produkt inserieren");
		}
	}

	public void setWarenkorb(String artikelnr, String menge) {
		if (kunde != null) {
			try {
				Connection conn = DB.getConnection();
				Statement stmt = conn.createStatement();
				int anzahl = stmt
						.executeUpdate("insert into Warenkorb values ("
								+ "(SELECT MAX (wkn) FROM warenkorb)+1, "
								+ kunde.getKundenNummer() + "," + artikelnr
								+ "," + menge + ");");
				System.out.println(getTime() + ": " + anzahl
						+ " Artikel in Warenkorb gelegt");

				stmt.close(); conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println(getTime() + ": Fehler Warenkorb inserieren");
			}
		} else {
			System.out.println(getTime() + ": nicht eingeloggt");
		}
		getWarenkorb();

	}

	public Produkt artikelnummerSuchen(String ausgewaehltesProdukt) {

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM produkt Where artikelNummer ="
							+ ausgewaehltesProdukt + ";");
			System.out.println("artikelNummerSuchen");
			if (rs.next()) {
				double preis = rs.getDouble("preis");
				String artikelNummer = rs.getString("artikelNummer");
				String artikelBezeichnung = rs.getString("artikelBezeichnung");
				String bildPfad = rs.getString("bildPfad");
				String kategorie = rs.getString("kategorie");
				int lagermenge = rs.getInt("lagermenge");
				System.out.println(getTime()
						+ ": "
						+ new Produkt(preis, artikelNummer, artikelBezeichnung,
								bildPfad, kategorie, lagermenge));

				rs.close(); stmt.close(); conn.close();
				return (new Produkt(preis, artikelNummer, artikelBezeichnung,
						bildPfad, kategorie, lagermenge));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(getTime() + ": Fehler Produkt suchen");
		}

		ausgewaehltesProdukt = null;
		return null;
	}

	public List<Produkt> getProdukte(String wo) {
		List<Produkt> produkte = new ArrayList<Produkt>();
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;

			switch (wo) {

			case "alle":
				rs = stmt.executeQuery("SELECT * FROM produkt;");
				
				break;
			case "aussen":
				rs = stmt
						.executeQuery("SELECT * FROM produkt WHERE kategorie = 'aussen' ;");
				
				break;
			case "innen":
				rs = stmt
						.executeQuery("SELECT * FROM produkt WHERE kategorie = 'innen' ;");
				
				break;
			case "brennbar":
				rs = stmt
						.executeQuery("SELECT * FROM produkt WHERE kategorie = 'brennbar' ;");
				
				break;
//			case "warenkorb":					//eigene Methode, weil die Abfragen beim Warenkorb abweichen
//				if (this.kunde != null) {
//					rs = stmt
//							.executeQuery("SELECT DISTINCT p.* FROM produkt p, Warenkorb w WHERE "
//									+ "p.artikelNummer = w.artikelNummer and w.kundenNummer = '"
//									+ this.kunde.getKundenNummer() + "';");
//				} else {
//					rs.close(); stmt.close(); conn.close();
//					return produkte;
//				}
//				break;
			default:
				stmt.executeQuery("SELECT * FROM produkt WHERE preis ='" + wo
						+ "' OR artikelBezeichnung = '" + wo
						+ "' OR kategorie = '" + wo + "';");
				
				break;
			}

			while (rs.next()) {
				double preis = rs.getDouble("preis");
				String artikelNummer = rs.getString("artikelNummer");
				String artikelBezeichnung = rs.getString("artikelBezeichnung");
				String bildPfad = rs.getString("bildPfad");
				String kategorie = rs.getString("kategorie");
				int lagermenge = rs.getInt("lagermenge");
//				int bestellmenge = rs.getInt("bestellmenge");
				produkte.add(new Produkt(preis, artikelNummer,
						artikelBezeichnung, bildPfad, kategorie, lagermenge));
			}
			System.out.println("getProdukte");
			
			rs.close(); stmt.close(); conn.close();

		} catch (SQLException e) {
			System.out.println(getTime() + ": Fehler Produkt suchen");
			e.printStackTrace();
		}

		return produkte;
	}

	public List<Produkt> produktSuchen(String gesuchterWert) {

		List<Produkt> gesuchteProdukte = getProdukte(gesuchterWert);

		return gesuchteProdukte;

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

	public void getWarenkorb() {
		Connection conn = DB.getConnection();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			if (this.kunde != null) {
				rs = stmt
						.executeQuery("SELECT DISTINCT p.*, w.bestellmenge FROM produkt p, Warenkorb w WHERE "
								+ "p.artikelNummer = w.artikelNummer and w.kundenNummer = '"
								+ this.kunde.getKundenNummer() + "';");
				System.out.println("get Warenkorb");
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					String artikelNummer = rs.getString("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
					int lagermenge = rs.getInt("lagermenge");
					int bestellmenge = rs.getInt("bestellmenge");
					
					this.kunde
							.setWarenkorb(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge, bestellmenge));

				}

				if (this.kunde.getWarenkorb().isEmpty()) {
					System.out.println(getTime() + ": Warenkorb leer");
				} else {
					this.kunde.zeigeInhaltWarenkorb();
				}

			} else {
				rs.close(); stmt.close(); conn.close();
				System.out.println(getTime() + ": Kunde nicht eingeloggt");
			}

			rs.close(); stmt.close(); conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Kunde getKunde() {

		return kunde;
	}

	public Kunde logout() {
		kunde = new Kunde();
		return getKunde();
	}

	public void loginUeberpruefung(Kunde kunde) throws Exception {
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM users WHERE username ='"
							+ kunde.getBenutzername() + "'AND pass = '"
							+ verschluesselPW(kunde.getPasswort()) + "';");

			if (rs.next()) {
				System.out.println(getTime() + ": pw richtig");

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
				System.out.println(getTime() + ": Login von:\n " + this.kunde);

				this.getWarenkorb(); // Läd Warenkorb aus der DB in die ArrayList des
								// Kunden
			} else {

				rs.close(); stmt.close(); conn.close();
				System.out.println(getTime() + ": pw falsch");
				throw new wrongPasswordOrUsernameException();
			}

			rs.close(); stmt.close(); conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(getTime() + ": Fehler login");
		}
	}

	public void addKunden(Kunde kunde) throws NoSuchAlgorithmException {
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into users values((SELECT MAX (kundenNummer) FROM users)+1, '"
					+ kunde.getAnrede()
					+ "', '"
					+ kunde.getVorname()
					+ "', '"
					+ kunde.getNachname()
					+ "', '"
					+ kunde.getBenutzername()
					+ "', '"
					+ kunde.getEmail()
					+ "', '"
					+ kunde.getStrasse()
					+ "', '"
					+ kunde.getHausnummer()
					+ "', '"
					+ kunde.getPlz()
					+ "', '"
					+ kunde.getOrt()
					+ "', '"
					+ kunde.getTelefonnummer()
					+ "', '"
					+ verschluesselPW(kunde.getPasswort()) + "','nein');");
			System.out.println(getTime() + ": " + stmt
					+ " Kunde wurde hinzugefügt");

			stmt.close(); conn.close();
		} catch (SQLException e) {
			System.out.println(getTime() + ": Fehler Kunden inserieren");
			e.printStackTrace();
		}
	}

	

	public String autovervollstaendigungSuche(String produkt) {
		ArrayList<String> produktbezeichnungen = new ArrayList<>();
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT artikelBezeichnung FROM produkt;");
			System.out.println("Aufruf autovervollstaendigung");
			while (rs.next()) {
				produktbezeichnungen.add(rs.getString("artikelBezeichnung"));

			}

			rs.close(); stmt.close(); conn.close();
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
			System.out.println(getTime()
					+ ": Fehler beim Auslesen der Artikelbezeichnung");
			e.printStackTrace();
		}
		return "";
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/*
	 * Verschlüsselung des Passworts
	 */

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

	public String getProduktJson(String artikelNummer) {

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM produkt WHERE artikelNummer = "
							+ artikelNummer + ";");
			System.out.println("json produkt");
			if (rs.next()) {

				Integer menge = new Integer(rs.getInt("lagermenge"));

				rs.close(); stmt.close(); conn.close();

				return menge.toString();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(getTime() + ": Fehler Produkt suchen");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static JSONArray convertToJson(ResultSet resultSet) throws Exception {
		JSONArray jsonArray = new JSONArray();
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			JSONObject obj = new JSONObject();
			for (int i = 0; i < total_rows; i++) {
				obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
						.toLowerCase(), resultSet.getObject(i + 1));
			}
			jsonArray.put(obj);
		}
		return jsonArray;

	}
	
	public void mengeAendern(String artikelnr, int menge) {
			Connection conn = DB.getConnection();
		try {
			
			
			Statement stmt = conn.createStatement();
			
			int anzahl = stmt.executeUpdate("UPDATE produkt SET lagermenge = lagermenge - "
						+ menge + " WHERE artikelNummer = '" + artikelnr + "';");
			if(anzahl != 0){
				System.out.println(getTime()+" ArtikelNummer: "+artikelnr+" bestellt");
			}
			stmt.close(); conn.close();
		} catch (SQLException e) {
			System.out.println(getTime() + ": Fehler beim aendern der Menge");
			e.printStackTrace();
		}
	}
	
	public void bestellArtikelAusWarenkorb(){
		
			for(Produkt produkt : this.kunde.getWarenkorb()){
				mengeAendern(produkt.artikelNummer, produkt.bestellmenge);
			}
			this.kunde.clearWarenkorb();
			warenkorbDatenbankLeeren();
			getWarenkorb();
		
	}
	
	public void warenkorbDatenbankLeeren(){
		Connection conn = DB.getConnection();
		try {
			
			
			Statement stmt = conn.createStatement();
			
			int anzahl = stmt.executeUpdate("DELETE FROM Warenkorb WHERE kundenNummer = '" + this.kunde.getKundenNummer() + "';");
			
				System.out.println(getTime()+": "+anzahl+" Eintraege aus Warenkorb geloescht");
			
			stmt.close(); conn.close();
		} catch (SQLException e) {
			System.out.println(getTime() + ": Fehler beim aendern der Menge");
			e.printStackTrace();
		}
	}

}
