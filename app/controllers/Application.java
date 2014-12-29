package controllers;

import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

public class Application extends Controller {

	final static Form<Kunde> userForm = Form.form(Kunde.class);

	public static Result index() {
		
		return ok(mainPage.render(Model.sharedInstance.getKunde(), Model.sharedInstance.getProdukte()));

	}

	public static Result agb() {

		return ok(agb.render(Model.sharedInstance.getKunde()));

	}

	public static Result artikel(String ausgewaehltesProdukt) {

		return ok(artikel.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.artikelnummerSuchen(ausgewaehltesProdukt)));

	}

	public static Result datenschutz() {

		return ok(datenschutz.render(Model.sharedInstance.getKunde()));

	}

	public static Result holzAussen() {

		return ok(holzAussen.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteAussen()));

	}

	public static Result impressum() {

		return ok(impressum.render(Model.sharedInstance.getKunde()));

	}

	public static Result holzInnen() {

		return ok(holzInnen.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteInnen()));

	}

	public static Result brennholz() {

		return ok(brennholz.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteBrennholz()));

	}

	public static Result kontakt() {

		return ok(kontakt.render(Model.sharedInstance.getKunde()));

	}

	public static Result konto() {

		return ok(konto.render(Model.sharedInstance.getKunde(), userForm));

	}
	
	public static WebSocket<String> socket() {
		
		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in,final WebSocket.Out<String> out) {
				in.onMessage(new Callback<String>() {
					public void invoke (String artikelNummer){
						System.out.println("neue Menge...");
							out.write(Model.sharedInstance.getProduktJson(artikelNummer));
						
						
					}
				});
				
				in.onClose(new Callback0(){
					public void invoke(){
						System.out.println("Artikelansicht verlassen :-(");
					}
				});
				
				
			}
		};
	}
		
	

	public static Result login() {

		return ok(login.render(userForm));
	}

	public static Result submitLogin() {
		Form<Kunde> filledForm = userForm.bindFromRequest();

		try {
			Model.sharedInstance.loginUeberpruefung(filledForm.get());
			System.out.println(Model.sharedInstance.getKunde());
			return ok(mainPage.render(Model.sharedInstance.getKunde(), Model.sharedInstance.getProdukte()));
		} catch (Exception e) {
			return ok(loginFehler.render(null));
		}
	}

	public static Result submitKundendaten() {
		Form<Kunde> filledForm = userForm.bindFromRequest();

		try {
			Model.sharedInstance.loginUeberpruefung(filledForm.get());
			System.out.println(Model.sharedInstance.getKunde());
			return ok(mainPage.render(Model.sharedInstance.getKunde(), Model.sharedInstance.getProdukte()));
		} catch (Exception e) {
			return ok(loginFehler.render(null));
		}
	}

	public static Result mainPage() {

		return ok(mainPage.render(Model.sharedInstance.getKunde(), Model.sharedInstance.getProdukte()));

	}

	public static Result neuheiten() {

		return ok(neuheiten.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukte()));
	}

	public static Result registrierung() {

		return ok(registrierung.render(userForm,
				Model.sharedInstance.getKunde()));

	}

	public static Result logout() {

		return ok(mainPage.render(Model.sharedInstance.logout(), Model.sharedInstance.getProdukte()));
	}

	public static Result autovervollstaendigungSuche(String produkt) {
		return ok(Model.sharedInstance.autovervollstaendigungSuche(produkt));

	}

	public static Result neuerUser() {
		Form<Kunde> filledForm = userForm.bindFromRequest();
		System.out.println(filledForm.get());
		try {
			Model.sharedInstance.addKunden(filledForm.get());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return ok(mainPage.render(Model.sharedInstance.getKunde(), Model.sharedInstance.getProdukte()));
	}

	public static Result suche(String produkt) {

		return ok(suchergebnisse.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.produktSuchen(produkt)));
	}

	public static Result produktInsert(String preis, String artikelBezeichnung,
			String bildPfad, String kategorie, String lagerm) {
		Model.sharedInstance.produktInserieren(Double.parseDouble(preis),
				artikelBezeichnung, bildPfad, kategorie,
				lagerm);
		return ok(neuesProdukt.render(Model.sharedInstance.getKunde()));
	}

	public static Result neuesProdukt() {
		return ok(neuesProdukt.render(Model.sharedInstance.getKunde()));
	}

	public static Result warenkorb() {

		return ok(warenkorb.render(Model.sharedInstance.getKunde(), Model.sharedInstance.getWarenkorb()));

	}

}
