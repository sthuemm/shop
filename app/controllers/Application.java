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
	//Erstellt ein Formular für den Login
	final static Form<Kunde> userForm = Form.form(Kunde.class);

	/*
	 * Rendert die Main Seite
	 */
	
	public static Result index() {

		return ok(mainPage.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteAlle()));

	}

	/*
	 * Rendert die AGB Seite
	 */
	
	public static Result agb() {

		return ok(agb.render(Model.sharedInstance.getKunde()));

	}

	/*
	 * Rendert die Artikel Seite
	 */
	
	public static Result artikel(String ausgewaehltesProdukt) {

		return ok(artikel.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.artikelnummerSuchen(ausgewaehltesProdukt),
				userForm));
	}

	/*
	 * Rendert die Datenschutz Seite
	 */
	
	public static Result datenschutz() {
		return ok(datenschutz.render(Model.sharedInstance.getKunde()));

	}
	
	/*
	 * Rendert die Holz in Aussenbereich Seite
	 */
	
	public static Result holzAussen() {

		return ok(holzAussen.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteAussen()));

	}
	
	/*
	 * Rendert die Impressum Seite
	 */
	
	public static Result impressum() {

		return ok(impressum.render(Model.sharedInstance.getKunde()));

	}
	
	/*
	 * Rendert die Holz in Innenbereich Seite
	 */
	
	public static Result holzInnen() {

		return ok(holzInnen.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteInnen()));

	}

	/*
	 * Rendert die Brenstoffe Seite
	 */
	
	public static Result brennholz() {

		return ok(brennholz.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteBrennholz()));

	}

	/*
	 * Rendert die Kontakt Seite
	 */
	
	public static Result kontakt() {

		return ok(kontakt.render(Model.sharedInstance.getKunde()));

	}

	/*
	 * Rendert die Konto Seite
	 */
	
	public static Result konto() {

		return ok(konto.render(Model.sharedInstance.getKunde(), userForm));

	}
	
	/*
	 * 
	 */
	
	public static Result bestellungAbschliessen(){
		
		Model.sharedInstance.bestellArtikelAusWarenkorb();
		
		return redirect("/warenkorb/");
		
	}

	/*
	 * 
	 */
	
	public static WebSocket<JsonNode> socket() {

		return new WebSocket<JsonNode>() {
			public void onReady(WebSocket.In<JsonNode> in,
					final WebSocket.Out<JsonNode> out) {
				System.out.println(Model.sharedInstance.getTime()+": WebSocketArtikel ready...");
				in.onMessage(new Callback<JsonNode>() {
					public void invoke(JsonNode obj) {
						
						out.write(Model.sharedInstance.zeigeAktuelleMenge(obj));

					}

				});

				in.onClose(new Callback0() {
					public void invoke() {
						System.out.println(Model.sharedInstance.getTime()+": Artikelansicht verlassen...");
					}
				});

			}
		};
	}
	
	/*
	 * 
	 */
	
	public static WebSocket<String> socketInWarenkorb() {

		return new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in,
					final WebSocket.Out<String> out) {
				System.out.println(Model.sharedInstance.getTime()+": WebSocketArtikel ready...");
				in.onMessage(new Callback<String>() {
					public void invoke(String artikelNummer) {
						System.out.println("neue Menge...");
						out.write(Model.sharedInstance
								.getProduktJson(artikelNummer));

					}
				});

				in.onClose(new Callback0() {
					public void invoke() {
						System.out.println(Model.sharedInstance.getTime()+": Artikelansicht verlassen...");
					}
				});

			}
		};
	}

	/*
	 * Rendert die Login Seite
	 */
	
	public static Result login() {

		return ok(login.render(userForm, Model.sharedInstance.getKunde()));
	}

	/*
	 * 
	 */
	
	public static Result submitLogin() {
		Form<Kunde> filledForm = userForm.bindFromRequest();

		try {
			Model.sharedInstance.loginUeberpruefung(filledForm.get());
			return ok(mainPage.render(Model.sharedInstance.getKunde(),
					Model.sharedInstance.getProdukteAlle()));
		} catch (Exception e) {
			return ok(loginFehler.render(null));
		}
	}

	/*
	 * 
	 */
	
	public static Result submitKundendaten() {
		Form<Kunde> filledForm = userForm.bindFromRequest();

		try {
			Model.sharedInstance.loginUeberpruefung(filledForm.get());
			System.out.println(Model.sharedInstance.getKunde());
			return ok(mainPage.render(Model.sharedInstance.getKunde(),
					Model.sharedInstance.getProdukteAlle()));
		} catch (Exception e) {
			return ok(loginFehler.render(null));
		}
	}

	/*
	 * Rendert die MainPage Seite
	 */
	
	public static Result mainPage() {

		return ok(mainPage.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteAlle()));

	}

	/*
	 * Rendert die Neuheiten Seite
	 */
	
	public static Result neuheiten() {

		return ok(neuheiten.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteAlle()));
	}

	/*
	 * Rendert die Registrierung Seite
	 */
	
	public static Result registrierung() {

		return ok(registrierung.render(userForm,
				Model.sharedInstance.getKunde()));

	}

	/*
	 * Rendert die Logout Seite
	 */
	
	public static Result logout() {

		return ok(mainPage.render(Model.sharedInstance.logout(),
				Model.sharedInstance.getProdukteAlle()));
	}

	public static Result autovervollstaendigungSuche(String produkt) {
		return ok(Model.sharedInstance.autovervollstaendigungSuche(produkt));

	}

	/*
	 * Rendert die Registrierungsseite
	 */

	public static Result neuerUser() {
		Form<Kunde> filledForm = userForm.bindFromRequest();
		System.out.println(filledForm.get());
		try {
			Model.sharedInstance.addKunden(filledForm.get());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return ok(mainPage.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukteAlle()));
	}

	/*
	 * Suche
	 */

	public static Result suche(String produkt) {

		return ok(suchergebnisse.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.produktSuchen(produkt)));
	}

	/*
	 * Fügt Produkte hinzu
	 */

	public static Result produktInsert(String preis, String artikelBezeichnung,
			String bildPfad, String kategorie, String lagerm) {
		Model.sharedInstance.produktInserieren(Double.parseDouble(preis),
				artikelBezeichnung, bildPfad, kategorie, lagerm);
		return ok(neuesProdukt.render(Model.sharedInstance.getKunde()));
	}

	/*
	 * Lädt Seite um neue Produkte hinzufügen zu können
	 */

	public static Result neuesProdukt() {
		return ok(neuesProdukt.render(Model.sharedInstance.getKunde()));
	}

	/*
	 * ruft Warenkorb auf
	 */

	public static Result warenkorb() {

		return ok(warenkorb.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getKunde().getWarenkorb()));

	}

	/*
	 * Legt Artikel in den Warenkorb
	 */

	public static Result inWarenkorb(String ausgewaehltesProdukt, String menge) {
		Model.sharedInstance.setWarenkorb(ausgewaehltesProdukt, menge);
		return ok(warenkorb.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getKunde().getWarenkorb()));

	}
}
