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
	private ArrayList<Kunde> Kunden = new ArrayList<>();
	

	public void addProdukt(Produkt produkt){
		Produkte.add(produkt);
		switch(produkt.kategorie){
			case("aussen"): produkteAussen.add(produkt);
							break;
			case("innen"): produkteInnen.add(produkt);
							break;
			case("brennbar"):produkteBrennholz.add(produkt);
							break;
		}
	}
	
	public void generateProdukts(){
		addProdukt(new Produkt(99.99,"1","ein Gartenzaun","images/Palissaden.jpg","aussen"));
		addProdukt(new Produkt(119.99,"2","Palisaden fuer den Garten","images/Pfaehle.jpg","aussen"));
		addProdukt(new Produkt(249.99,"3","Terassenbelaege","images/Terrasse.jpg","aussen"));
		addProdukt(new Produkt(49.99,"4","Terassenmoebel","images/bruecke.jpg","aussen"));
		
		addProdukt(new Produkt(29.99,"5","Esstisch","innen"));
		addProdukt(new Produkt(19.99,"6","Stuhl","innen"));
		addProdukt(new Produkt(44.99,"7","Vertaefelung","innen"));
		
		addProdukt(new Produkt(4.99,"8","Echtes Kiefernholz","brennbar"));
		addProdukt(new Produkt(5.99,"9","Echtes Buchenholz","brennbar"));
		addProdukt(new Produkt(2.99,"10","Super brennbare Spanplatte","brennbar"));
	}
	
	public void generateKunden(){


	}

	
	
public Kunde loginUeberpruefung(String user, String userPass){
		try{
		Connection conn = DB.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE vorname ='"+user+"'AND pass = '"+userPass+"'");
		String r1 = "";
		String r2 = "";
		int r3 = 0;
		String r4 = "";
		if(rs == null){
		return null;}
		else{
		while (rs.next()) {
				r1 = rs.getString("vorname");
				r2 = rs.getString("vorname");
				r3 = rs.getInt("kundid");
				r4 = rs.getString("pass");}
				rs.close(); stmt.close(); conn.close();
				return new Kunde(r1,r2,r3,r4, true);
				}
		
		}
		catch (SQLException ex)
		{ 
		System.out.println("Fehler");
		}
	return null;
	}
	
	private Model() {
	}
	
	
	
	public Produkt[] getProdukteAussen() {
		Produkt[] produkteAussenArray = produkteAussen.toArray(new Produkt[produkteAussen.size()]);
		return produkteAussenArray;
	}

	public void setProdukteAussen(ArrayList<Produkt> produkteAussen) {
		this.produkteAussen = produkteAussen;
	}

	public Produkt[] getProdukteInnen() {
		Produkt[] produkteInnenArray = produkteInnen.toArray(new Produkt[produkteInnen.size()]);
		return produkteInnenArray;
	}

	public void setProdukteInnen(ArrayList<Produkt> produkteInnen) {
		this.produkteInnen = produkteInnen;
	}

	public Produkt[] getProdukteBrennholz() {
		Produkt[] brennholzArray = produkteBrennholz.toArray(new Produkt[produkteBrennholz.size()]);
		return brennholzArray;
	}

	public void setProdukteBrennholz(ArrayList<Produkt> produkteBrennholz) {
		this.produkteBrennholz = produkteBrennholz;
	}

	public void addKunden(Kunde kunde){
		Kunden.add(kunde);
	}
	
	public ArrayList<Kunde> getKunden(){
		return Kunden;
	}
	
	public Kunde getGuest(){
		return null;
	}
	
	public Produkt[] getProdukte(){
	
		Produkt[] produkteArray = Produkte.toArray(new Produkt[Produkte.size()]);
	    return produkteArray;
	}
	
	
	
}
