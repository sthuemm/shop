	package controllers;
	

	import play.*;
	import play.mvc.*;
	import views.html.*;
	import models.*;
	
	

	public class Application extends Controller {
	
		private static String name = "Basti";
		private static String kundennummer = "1";
		public static Produkt produkt = new Produkt();
		public static Kunde guest = new Kunde();
		public static Kunde kunde1 = new Kunde("Max","Mustermann","1337");
				
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
		public static Result holzaussen() {
			
			if (kundennummer == null){
				return ok(holzAussen.render(guest));
			} else {
				return ok(holzAussen.render(kunde1));
			}
		}
		public static Result impressum() {
			
			if (kundennummer == null){
				return ok(impressum.render(guest));
			} else {
				return ok(impressum.render(kunde1));
			}
		}
		public static Result kategorie2() {
			
			if (kundennummer == null){
				return ok(kategorie2.render(guest));
			} else {
				return ok(kategorie2.render(kunde1));
			}
		}
		public static Result kategorie3() {
			
			if (kundennummer == null){
				return ok(kategorie3.render(guest));
			} else {
				return ok(kategorie3.render(kunde1));
			}
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
			
			if (kundennummer == null){
				return ok(neuheiten.render(guest));
			} else {
				return ok(neuheiten.render(kunde1));
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
	
	
