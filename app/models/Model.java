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

import org.json.JSONArray;
import org.json.JSONObject;

public class Model {

	public static Model sharedInstance = new Model();
	private Kunde kunde = null;

	private Model() {
	}

	// private Statement dbAufruf() {
	//
	// Statement stmt = null;
	//
	// try {
	// System.out.println("Connected wird aufgerufen");
	// stmt = connect().createStatement();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	//
	// return stmt;
	// }

	public void produktInserieren(double preis, String artikelBezeichnung,
			String bildPfad, String kategorie, String lagermenge) {

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("insert into produkt values (" + preis + ","
					+ "(SELECT MAX (artikelNummer) FROM produkt)+1,'"
					+ artikelBezeichnung + "', '" + bildPfad + "','"
					+ kategorie + "'," + Integer.parseInt(lagermenge) + ");");
			stmt.close();
			conn.close();
			System.out.println("Produkt wurde hinzugefügt");
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt inserieren");
		}
	}

	public void setWarenkorb(String artikelnr, String menge) {
		// this.kunde = kunde;
		if (kunde != null) {
			try {
				Connection conn = DB.getConnection();
				Statement stmt = conn.createStatement();
				int anzahl = stmt
						.executeUpdate("insert into Warenkorb values ("
								+ kunde.getKundenNummer() + ","
								+ Integer.parseInt(artikelnr) + ");");
				System.out.println("updates: " + anzahl);

				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("Fehler Warenkorb inserieren");
			}
		}

	}

	public List<Produkt> produktSuchen(String gesuchterWert) {
		// suchergebnisseResetten();
		List<Produkt> gesuchteProdukte = new ArrayList<>();
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM produkt WHERE preis ='"
							+ gesuchterWert + "' OR artikelBezeichnung = '"
							+ gesuchterWert + "' OR kategorie = '"
							+ gesuchterWert + "';");

			while (rs.next()) {
				double preis = rs.getDouble("preis");
				int artikelNummer = rs.getInt("artikelNummer");
				String artikelBezeichnung = rs.getString("artikelBezeichnung");
				String bildPfad = rs.getString("bildPfad");
				String kategorie = rs.getString("kategorie");
				int lagermenge = rs.getInt("lagermenge");
				gesuchteProdukte.add(new Produkt(preis, artikelNummer,
						artikelBezeichnung, bildPfad, kategorie, lagermenge));
			}

			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return gesuchteProdukte;

	}

	public Produkt artikelnummerSuchen(String ausgewaehltesProdukt) {

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM produkt Where artikelNummer ="
							+ ausgewaehltesProdukt + ";");
			

			if (rs.next()) {
				double preis = rs.getDouble("preis");
				int artikelNummer = rs.getInt("artikelNummer");
				String artikelBezeichnung = rs.getString("artikelBezeichnung");
				String bildPfad = rs.getString("bildPfad");
				String kategorie = rs.getString("kategorie");
				int lagermenge = rs.getInt("lagermenge");
				System.out.println(new Produkt(preis, artikelNummer,
						artikelBezeichnung, bildPfad, kategorie, lagermenge));
				stmt.close();
				conn.close();
				return (new Produkt(preis, artikelNummer, artikelBezeichnung,
						bildPfad, kategorie, lagermenge));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
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
				System.out.println("stmt wird aufgerufen");
				break;
			case "aussen":
				rs = stmt
						.executeQuery("SELECT * FROM produkt WHERE kategorie = 'aussen' ;");
				System.out.println("stmt wird aufgerufen");
				break;
			case "innen":
				rs = stmt
						.executeQuery("SELECT * FROM produkt WHERE kategorie = 'innen' ;");
				System.out.println("stmt wird aufgerufen");
				break;
			case "brennbar":
				rs = stmt
						.executeQuery("SELECT * FROM produkt WHERE kategorie = 'brennbar' ;");
				System.out.println("stmt wird aufgerufen");
				break;
			case "warenkorb":
				if (this.kunde != null) {
					rs = stmt
							.executeQuery("SELECT DISTINCT p.* FROM produkt p, Warenkorb w WHERE "
									+ "p.artikelNummer = w.artikelNummer and w.kundenNummer = '"
									+ this.kunde.getKundenNummer() + "';");
					System.out.println("Statement wird aufgerufen");
				} else {
					stmt.close();
					conn.close();
					return produkte;
				}
				break;
			}
			
			while (rs.next()) {
				double preis = rs.getDouble("preis");
				int artikelNummer = rs.getInt("artikelNummer");
				String artikelBezeichnung = rs
						.getString("artikelBezeichnung");
				String bildPfad = rs.getString("bildPfad");
				String kategorie = rs.getString("kategorie");
				int lagermenge = rs.getInt("lagermenge");
				produkte.add(new Produkt(preis, artikelNummer,
						artikelBezeichnung, bildPfad, kategorie, lagermenge));
			}
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Fehler Produkt suchen");
			e.printStackTrace();
		}
		
		return produkte;
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
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM users WHERE username ='"
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
				stmt.close();
				conn.close();
				System.out.println("pw falsch");
				throw new wrongPasswordOrUsernameException();
			}

			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler login");
		}
	}

	public void addKunden(Kunde kunde) throws NoSuchAlgorithmException {
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			int rs = stmt
					.executeUpdate("insert into users values((SELECT MAX (kundenNummer) FROM users)+1, '"
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
							+ verschluesselPW(kunde.getPasswort())
							+ "','nein');");
			System.out.println(rs + " Kunde wurde hinzugefügt");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Fehler Kunden inserieren");
			e.printStackTrace();
		}
	}

	public void mengeAendern(int artikelnr, int menge) {
		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE produkt SET lagermenge = lagermenge - '"
					+ menge + "' WHERE artikelNummer = '" + artikelnr + "';");
			stmt.close();
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
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT artikelBezeichnung FROM produkt;");

			while (rs.next()) {
				produktbezeichnungen.add(rs.getString("artikelBezeichnung"));

			}

			stmt.close();
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

	public String getProduktJson(String artikelNummer) {

		try {
			Connection conn = DB.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM produkt WHERE artikelNummer = "
							+ artikelNummer + ";");

			if (rs.next()) {
				Integer menge = new Integer(rs.getInt("lagermenge"));
				stmt.close();
				conn.close();

				return menge.toString();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
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

}
