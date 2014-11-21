	package controllers;
	

	import play.*;
	import play.mvc.*;
	import views.html.*;
	import models.*;
	import java.util.List;
	import java.lang.*;
	

	public class Application extends Controller {
	
		private static String name = "Basti";
		private static String kundennummer = "1";
		
		
	
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
		
		
		
		public static Kunde guest = new Kunde();
		public static Kunde kunde1 = new Kunde("Max","Mustermann","1337");
				
	
		public static Result agb() {
			
			
			
			if (kundennummer == null){
				return ok(agb.render(guest));
			} else {
				return ok(agb.render(kunde1));
			}
		}
		public static Result artikel() {
			
			if (kundennummer == null){
				return ok(artikel.render(guest));
			} else {
				return ok(artikel.render(kunde1));
			}
		}
		public static Result datenschutz() {
			
			if (kundennummer == null){
				return ok(datenschutz.render(guest));
			} else {
				return ok(datenschutz.render(kunde1));
			}
		}
		public static Result holzAussen() {
			
			
				return ok(holzAussen.render(kunde1,produkteAussen));
			
		}
		public static Result impressum() {
			
			if (kundennummer == null){
				return ok(impressum.render(guest));
			} else {
				return ok(impressum.render(kunde1));
			}
		}
		public static Result holzInnen() {
			
			
				return ok(holzInnen.render(kunde1,produkteInnen));
			
		}
		public static Result brennholz() {
			
			
				return ok(brennholz.render(kunde1,produkteBrennholz));
			
		}
		public static Result kontakt() {
			
			if (kundennummer == null){
				return ok(kontakt.render(guest));
			} else {
				return ok(kontakt.render(kunde1));
			}
		}	
		
		public static Result konto() {
			
			if (kundennummer == null){
				return ok(konto.render(guest));
			} else {
				return ok(konto.render(kunde1));
			}
		}
		
		public static Result login() {
			return ok(login.render());
		}
		
		public static Result mainPage() {
			
			if (kundennummer == null){
				return ok(mainPage.render(guest));
			} else {
				return ok(mainPage.render(kunde1));
			}
			
		}
		public static Result neuheiten() {
			
		
				return ok(neuheiten.render(kunde1,alleProdukte));
			
		}	
		public static Result registrierung() {
			
			if (kundennummer == null){
				return ok(Registrierung.render(guest));
			} else {
				return ok(Registrierung.render(kunde1));
			}
			
		}	
		
		
		
		
	}
	
	
