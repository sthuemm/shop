	package controllers;

	import play.*;
	import play.mvc.*;
	import views.html.*;

	public class Application extends Controller {

		public static Result index() {
			return ok(index.render("whats up!"));
		}
		public static Result agb() {
			return ok(AGB.render());
		}
		public static Result artikel() {
			return ok(Artikel.render());
		}
		public static Result datenschutz() {
			return ok(Datenschutz.render());
		}
		public static Result holzaussen() {
			return ok(HolzAussen.render());
		}
		public static Result impressum() {
			return ok(Impressum.render());
		}
		public static Result kategorie2() {
			return ok(Kategorie2.render());
		}
		public static Result kategorie3() {
			return ok(Kategorie3.render());
		}
		public static Result kontakt() {
			return ok(kontakt.render());
		}	
		public static Result login() {
			return ok(login.render());
		}
		public static Result mainPage() {
			return ok(mainPage.render());
		}
		public static Result mainShopTest() {
			return ok(mainShopTest.render());
		}
		public static Result neuheiten() {
			return ok(Neuheiten.render());
		}	
		public static Result registrierung() {
			return ok(Registrierung.render());
		}	
	}
