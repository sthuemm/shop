	package controllers;
	

	import play.*;
	import play.mvc.*;
	import views.html.*;
	import models.*;
	import java.util.List;
	import java.lang.*;
	

	public class Application extends Controller {
	
		
		private static String kundennummer;
		
		
	
		public static Produkt gartenzaun = new Produkt(99.99,"1","ein Gartenzaun","images/Palissaden.jpg");
		public static Produkt palisaden = new Produkt(119.99,"2","Palisaden fuer den Garten","images/Pfaehle.jpg");
		public static Produkt terassenbelag = new Produkt(249.99,"3","Terassenbelaege","images/Terrasse.jpg");
		public static Produkt moebel = new Produkt(49.99,"4","Terassenmoebel","images/bruecke.jpg");
		
		public static Produkt tisch = new Produkt(29.99,"5","Esstisch");
		public static Produkt stuhl = new Produkt(19.99,"6","Stuhl");
		public static Produkt vertaefelung = new Produkt(44.99,"7","Vertaefelung");
		
		public static Produkt kiefer = new Produkt(4.99,"8","Echtes Kiefernholz");
		public static Produkt buche = new Produkt(5.99,"9","Echtes Buchenholz");
		public static Produkt spanplatte = new Produkt(2.99,"10","Super brennbare Spanplatte");
		
		public static Produkt[] produkteAussen = {gartenzaun,palisaden,terassenbelag,moebel};
		public static Produkt[] produkteInnen = {tisch,stuhl,vertaefelung};
		public static Produkt[] produkteBrennholz = {kiefer,buche,spanplatte};
		
		public static Produkt[] alleProdukte = {gartenzaun,palisaden,terassenbelag,moebel,tisch,stuhl,vertaefelung,kiefer,buche,spanplatte};
		static int zufallsZahl = (int)Math.random()*alleProdukte.length;
		
		
		
		
		public static Kunde guest;
		public static Kunde kunde = new Kunde("Max","Mustermann","1337","test");
		public static Kunde kunde1;
		public static Kunde[] kunden = {kunde};
				
	
		public static Result agb() {
			
		
		
				return ok(agb.render(kunde1));
			
		}
		
		public static Result artikel() {
			
				return ok(artikel.render(kunde1));
			
		}
		
		public static Result datenschutz() {
			
		
				return ok(datenschutz.render(kunde1));
			
		}
		
		public static Result holzAussen() {
			
			
				return ok(holzAussen.render(kunde1,produkteAussen));
			
			
		}
		
		public static Result impressum() {
			
			
				return ok(impressum.render(kunde1));
			
		}
		
		public static Result holzInnen() {
			
			
				return ok(holzInnen.render(kunde1,produkteInnen));
			
		}
		
		public static Result brennholz() {
			
			
				return ok(brennholz.render(kunde1,produkteBrennholz));
			
		}
		
		public static Result kontakt() {
			
			
				return ok(kontakt.render(kunde1));
			
		}	
		
		public static Result konto() {
			
			
				return ok(konto.render(kunde1));
			
		}
		
		public static Result login() {
			
		
			return ok(login.render(kunde1));
		}
		
		public static Result mainPage() {
			
			
				return ok(mainPage.render(kunde1));
			
		}
		
		public static Result neuheiten() {	
			
		
				return ok(neuheiten.render(kunde1,alleProdukte));
			
		}	
		
		public static Result registrierung() {
			
		
				return ok(Registrierung.render(kunde1));
			
			
		}	
		
		public static Result main(String user, String userPasswort) {
			for (Kunde kunde : kunden) {
				if(kunde.vorname == user){
					if(kunde.passwort == userPasswort){
						kunde1 = kunde;
						
					}
				} else {
					
				}
			}
			return ok(mainPage.render(kunde1));
		}
		
		public static Result logout() {
			kunde1 = null;
			return ok(mainPage.render(kunde1));
		}
		
		
	}
	
	
