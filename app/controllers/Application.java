	package controllers;
	

	import play.*;
	import play.mvc.*;
	import views.html.*;
	import models.*;
	
	

	public class Application extends Controller {
	
		private static String name = "Basti";
		private static String kundennummer = "1";
		public static Produkt produkt = new Produkt();
		public static Kunde guest = new Kunde("Fremder","",0);
		public static Kunde kunde1 = new Kunde();
				
		public static Result index() {
			return ok(index.render("whats up!"));
		}
		public static Result agb() {
			
			if (kundennummer == null){
				return ok(AGB.render(guest));
			} else {
				return ok(AGB.render(kunde1));
			}
		}
		public static Result artikel() {
			
			if (kundennummer == null){
				return ok(Artikel.render(guest));
			} else {
				return ok(Artikel.render(kunde1));
			}
		}
		public static Result datenschutz() {
			
			if (kundennummer == null){
				return ok(Datenschutz.render(guest));
			} else {
				return ok(Datenschutz.render(kunde1));
			}
		}
		public static Result holzaussen() {
			
			if (kundennummer == null){
				return ok(HolzAussen.render(guest));
			} else {
				return ok(HolzAussen.render(kunde1));
			}
		}
		public static Result impressum() {
			
			if (kundennummer == null){
				return ok(Impressum.render(guest));
			} else {
				return ok(Impressum.render(kunde1));
			}
		}
		public static Result kategorie2() {
			
			if (kundennummer == null){
				return ok(Kategorie2.render(guest));
			} else {
				return ok(Kategorie2.render(kunde1));
			}
		}
		public static Result kategorie3() {
			
			if (kundennummer == null){
				return ok(Kategorie3.render(guest));
			} else {
				return ok(Kategorie3.render(kunde1));
			}
		}
		public static Result kontakt() {
			
			if (kundennummer == null){
				return ok(kontakt.render(guest));
			} else {
				return ok(kontakt.render(kunde1));
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
			
			if (kundennummer == null){
				return ok(Neuheiten.render(guest));
			} else {
				return ok(Neuheiten.render(kunde1));
			}
		}	
		public static Result registrierung() {
			
			if (kundennummer == null){
				return ok(Registrierung.render(guest));
			} else {
				return ok(Registrierung.render(kunde1));
			}
			
		}	
		
		
	}
	
	
