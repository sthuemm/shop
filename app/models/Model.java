package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.*;
import play.db.*;

import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

//import org.json.JSONArray;
//import org.json.JSONObject;




public class Model {

	public static Model sharedInstance = new Model();
//	private List<Produkt> gesuchteProdukte = new ArrayList<>();
	private Kunde kunde = null;
	private Connection conn;



	private Model() {


	}

	private Statement dbAufruf() {
		
		this.conn = DB.getConnection();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return stmt;
	}
	
	

	public List<Produkt> produktSuchen(String gesuchterWert) {

		List<Produkt> gesuchteProdukte = new ArrayList<>();
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
					gesuchteProdukte
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}
				

			}
			rs.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return gesuchteProdukte;

	}


	public void produktInserieren(double preis, String artikelBezeichnung,
			String bildPfad, String kategorie, String lagermenge) {

		try {

			dbAufruf().executeUpdate(
					"insert into produkt values ("
							+ preis + ","
							+ "(SELECT MAX (artikelNummer) FROM produkt)+1,'"
							+ artikelBezeichnung+ "', '" 
							+ bildPfad + "','" 
							+ kategorie + "',"
							+ Integer.parseInt(lagermenge) + ");");
			conn.close();
			System.out.println("Produkt wurde hinzugefügt");
		}

		catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt inserieren");
		}
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
			conn.close();

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
			conn.close();
		} catch (SQLException e) {
			System.out.println("Fehler Kunden inserieren");
			e.printStackTrace();
		}
	}

	public List<Produkt> getProdukteAussen() {
		List<Produkt> produkteAussen = new ArrayList<Produkt>();
		
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'aussen' ;");

			if (rs == null) {
				rs.close();
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
					produkteAussen
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}
				

				rs.close();
				conn.close();
				return produkteAussen;
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return null;
	}

	public List<Produkt> getProdukteInnen() {
		List<Produkt> produkteInnen = new ArrayList<Produkt>();
		
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'innen' ;");

			if (rs == null) {
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
					produkteInnen
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}
				

				rs.close();
				conn.close();
				return produkteInnen;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return produkteInnen;
	}

//	public void setProdukteInnen(ArrayList<Produkt> produkteInnen) {
//		this.produkteInnen = produkteInnen;
//	}

	public List<Produkt> getProdukteBrennholz() {
		List<Produkt> produkteBrennstoff = new ArrayList<Produkt>();
		
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt WHERE kategorie = 'brennbar' ;");

			if (rs == null) {
				
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
					produkteBrennstoff
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}
				

				rs.close();
				conn.close();
				return produkteBrennstoff;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return produkteBrennstoff;
	}

//	public void setProdukteBrennholz(ArrayList<Produkt> produkteBrennholz) {
//		this.produkteBrennholz = produkteBrennholz;
//	}

	public Kunde getKunde() {
		return kunde;
	}

	public Kunde logout() {
		kunde = null;

		return getKunde();
	}

	public List<Produkt> getProdukte() {

		List<Produkt> produkteAlleList = new ArrayList<>();
		
		try {
			ResultSet rs = dbAufruf().executeQuery("SELECT * FROM produkt;");

			if (rs == null) {
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
					produkteAlleList
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}
				

				rs.close();
				conn.close();
				return produkteAlleList;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}
		return produkteAlleList;
	}

	public void mengeAendern(int artikelnr, int menge) {		//keine Umlaute verwenden

		try {
			dbAufruf().executeUpdate(
					"UPDATE produkt SET lagermenge = lagermenge - '" + menge
							+ "' WHERE artikelNummer = '" + artikelnr + "';");
			conn.close();
		} catch (SQLException e) {
			System.out.println("Fehler beim ändern der Menge");
			e.printStackTrace();
		}

	}

	public List<Produkt> getWarenkorb() {
		List<Produkt> produkteAlleList = new ArrayList<Produkt>();
		
		try {
	
			ResultSet rs;
			if(kunde !=null){
			
				rs = dbAufruf()
					.executeQuery(
							"SELECT DISTINCT p.* FROM produkt p, Warenkorb w WHERE "
									+ "p.artikelNummer = w.artikelNummer and w.kundenNummer = '"
									+ kunde.getKundenNummer() + "';");
			}else{
				return produkteAlleList;
			}
						
			if (rs == null) {
				conn.close();
				return produkteAlleList; //nicht null zurückgeben, lieber ein leeres Array
			} else {
				while (rs.next()) {
					double preis = rs.getDouble("preis");
					int artikelNummer = rs.getInt("artikelNummer");
					String artikelBezeichnung = rs
							.getString("artikelBezeichnung");
					String bildPfad = rs.getString("bildPfad");
					String kategorie = rs.getString("kategorie");
					int lagermenge = rs.getInt("lagermenge");
					produkteAlleList
							.add(new Produkt(preis, artikelNummer,
									artikelBezeichnung, bildPfad, kategorie,
									lagermenge));
				}
			

				rs.close();
				conn.close();
				return produkteAlleList;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Warebkorb ausgabe");

		}
		return null;
	}

	public void setWarenkorb(Kunde kunde, int artikelnr) {
		//this.kunde = kunde;
		try {
			dbAufruf().executeUpdate(
					"insert into Warenkorb values ('" + kunde.getKundenNummer()
							+ "','" + artikelnr + "');");

			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Warenkorb inserieren");
		}

	}

	public String autovervollstaendigungSuche(String produkt) {
		ArrayList<String> produktbezeichnungen = new ArrayList<>();
//		suchergebnisseResetten();
		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT artikelBezeichnung FROM produkt;");

			while (rs.next()) {
				produktbezeichnungen.add(rs.getString("artikelBezeichnung"));
				
			}
			rs.close();
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

	public Produkt artikelnummerSuchen(String ausgewaehltesProdukt) {

		try {
			ResultSet rs = dbAufruf().executeQuery(
					"SELECT * FROM produkt Where artikelNummer ="
							+ ausgewaehltesProdukt + ";");

			if (rs == null) {
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
				conn.close();

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Fehler Produkt suchen");
		}

		ausgewaehltesProdukt = null;
		return null;
	}
	
	public String getProduktJson(String artikelNummer){
		
		try {
			ResultSet rs = dbAufruf().executeQuery("SELECT * FROM produkt WHERE artikelNummer = "+artikelNummer+";");

			if (rs == null) {
				conn.close();
				return null;
			} else {
				
				
				Integer menge = new Integer(rs.getInt("lagermenge"));
				rs.close();
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
	
//	public static JSONArray convertToJson(ResultSet resultSet) throws Exception{
//		JSONArray jsonArray = new JSONArray();
//		while(resultSet.next()){
//			int total_rows = resultSet.getMetaData().getColumnCount();
//			JSONObject obj = new JSONObject();
//			for(int i = 0; i < total_rows; i++){
//				obj.put(resultSet.getMetaData().getColumnLabel(i+1).toLowerCase(),resultSet.getObject(i+1));
//			}
//			jsonArray.put(obj);
//		}
//		return jsonArray;
//		
//		
//		
//		
//	}

}
