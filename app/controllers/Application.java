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
	// Erstellt ein Formular für den Login
	final static Form<Kunde> userForm = Form.form(Kunde.class);

	/*
	 * Rendert die Main Seite
	 */


	public static Result index() {
		String username = session("User1");
		if (username != null) {
			return ok(mainPage.render(username,
					Model.sharedInstance.getProdukte("alle")));
		} else {
			return ok(mainPage.render("guest",
					Model.sharedInstance.getProdukte("alle")));
		}


	
	
	}

	/*
	 * Rendert die AGB Seite
	 */

	public static Result agb() {
		String username = session("User1");
		if (username != null) {
			return ok(agb.render(username));
		} else {
			return ok(agb.render("guest"));
		}

	}

	/*
	 * Rendert die Artikel Seite
	 */

	public static Result artikel(String ausgewaehltesProdukt) {
		String username = session("User1");
		if (username != null) {
			return ok(artikel.render(username, Model.sharedInstance
					.artikelnummerSuchen(ausgewaehltesProdukt), userForm));
		} else {
			return ok(artikel.render("guest", Model.sharedInstance
					.artikelnummerSuchen(ausgewaehltesProdukt), userForm));
		}

	}

	/*
	 * Rendert die Datenschutz Seite
	 */

	public static Result datenschutz() {
		String username = session("User1");
		if (username != null) {
			return ok(datenschutz.render(username));
		} else {
			return ok(datenschutz.render("guest"));
		}
	}

	/*
	 * Rendert die Holz in Aussenbereich Seite
	 */

	public static Result holzAussen() {
		String username = session("User1");
		if (username != null) {
			return ok(holzAussen.render(username,
					Model.sharedInstance.getProdukteAussen()));
		} else {
			return ok(holzAussen.render("guest",
					Model.sharedInstance.getProdukteAussen()));
		}

	}

	/*
	 * Rendert die Impressum Seite
	 */

	public static Result impressum() {
		String username = session("User1");
		if (username != null) {
			return ok(impressum.render(username));
		} else {
			return ok(impressum.render("guest"));
		}

	}

	/*
	 * Rendert die Holz in Innenbereich Seite
	 */

	public static Result holzInnen() {
		String username = session("User1");
		
		if (username != null) {
			return ok(holzInnen.render(username, Model.sharedInstance.getProdukteInnen()));
		} else {
			return ok(holzInnen.render("guest",Model.sharedInstance.getProdukteInnen()));
		}
	}

	/*
	 * Rendert die Brenstoffe Seite
	 */

	public static Result brennholz() {
		String username = session("User1");
		if (username != null) {
			return ok(brennholz.render(username,
					Model.sharedInstance.getProdukteBrennholz()));
		} else {
			return ok(brennholz.render("guest",
					Model.sharedInstance.getProdukteBrennholz()));
		}

	}

	/*
	 * Rendert die Kontakt Seite
	 */

	public static Result kontakt() {
		String username = session("User1");
		if (username != null) {
			return ok(kontakt.render(username));
		} else {
			return ok(kontakt.render("guest"));
		}
	}

	/*
	 * Rendert die Konto Seite
	 */

	public static Result konto() {
		String username = session("User1");
		if (username != null) {
			return ok(konto.render(username, userForm));
		} else {
			return ok(konto.render("guest", userForm));
		}

	}

	/*
	 * 
	 */

	public static Result bestellungAbschliessen() {
		String username = session("User1");
		if (username != null) {
			Model.sharedInstance
					.bestellArtikelAusWarenkorb(session("UserKundennummer"));
			return redirect("/");
		} else {
			return redirect("/login");
		}
	}

	/*
	 * 
	 */

	public static WebSocket<JsonNode> socket() {

		return new WebSocket<JsonNode>() {
			public void onReady(WebSocket.In<JsonNode> in,
					final WebSocket.Out<JsonNode> out) {
				System.out.println(Model.sharedInstance.getTime()
						+ ": WebSocketArtikel ready...");
				in.onMessage(new Callback<JsonNode>() {
					public void invoke(JsonNode obj) {

						out.write(Model.sharedInstance.zeigeAktuelleMenge(obj));

					}

				});

				in.onClose(new Callback0() {
					public void invoke() {
						System.out.println(Model.sharedInstance.getTime()
								+ ": Artikelansicht verlassen...");
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
				System.out.println(Model.sharedInstance.getTime()
						+ ": WebSocketArtikel ready...");
				in.onMessage(new Callback<String>() {
					public void invoke(String artikelNummer) {
						System.out.println("neue Menge...");
						out.write(Model.sharedInstance
								.getProduktJson(artikelNummer));

					}
				});

				in.onClose(new Callback0() {
					public void invoke() {
						System.out.println(Model.sharedInstance.getTime()
								+ ": Artikelansicht verlassen...");
					}
				});

			}
		};
	}

	/*
	 * Rendert die Login Seite
	 */





	/*
	 * 
	 */

	public static Result submitKundendaten() {
		Form<Kunde> filledForm = userForm.bindFromRequest();
		String username = session("User1");

		if (username != null) {
			return ok(mainPage.render(username,
					Model.sharedInstance.getProdukteAlle()));
		} else {
			return redirect("/");
		}

	}

	/*
	 * Rendert die MainPage Seite
	 */

	public static Result mainPage() {
		String username = session("User1");

		if (username != null) {
			return ok(mainPage.render(username,
					Model.sharedInstance.getProdukte("alle")));
		} else {
			return ok(mainPage.render("guest",
					Model.sharedInstance.getProdukte("alle")));
		}
	}

	/*
	 * Rendert die Neuheiten Seite
	 */

	public static Result neuheiten() {

		String username = session("User1");

		if (username != null) {
			return ok(neuheiten.render(username,
					Model.sharedInstance.getProdukteAlle()));
		} else {
			return ok(neuheiten.render("guest",
					Model.sharedInstance.getProdukteAlle()));
		}

	}

	/*
	 * Rendert die Registrierung Seite
	 */

	public static Result registrierung() {
		String username = session("User1");

		if (username != null) {
			return ok(registrierung.render(userForm, username));
		} else {
			return ok(registrierung.render(userForm, "guest"));
		}
		

	}

	/*
	 * Rendert die Logout Seite
	 */


	public static Result logout() {
		
			session().clear();
			return redirect("/mainPage");
		
		

	}

	public static Result autovervollstaendigungSuche(String produkt) {
		return ok(Model.sharedInstance.autovervollstaendigungSuche(produkt));

	}

	/*
	 * Rendert die Registrierungsseite
	 */

	public static Result neuerUser() {
		String username = session("User1");
		
		Form<Kunde> filledForm = userForm.bindFromRequest();
		System.out.println(filledForm.get());
		try {
			Model.sharedInstance.addKunden(filledForm.get());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (username != null) {
			return ok(mainPage.render(username,	Model.sharedInstance.getProdukteAlle()));
		} else {
			return ok(mainPage.render("guest", Model.sharedInstance.getProdukteAlle()));
		}
		
	}

	/*
	 * Suche
	 */

	public static Result suche(String produkt) {
		String username = session("User1");
		if (username != null) {
			return ok(suchergebnisse.render(username, Model.sharedInstance.produktSuchen(produkt)));
		} else {
			return ok(suchergebnisse.render("guest", Model.sharedInstance.produktSuchen(produkt)));
		}
		
	}



	

	/*
	 * ruft Warenkorb auf
	 */

	public static Result warenkorb() {
		String username = session("User1");
		if (username != null) {
			return ok(warenkorb.render(session("User1"), Model.sharedInstance.getWarenkorb(session("UserKundennummer"))));
		} else {
			return redirect("/login");
		}

	}

	/*
	 * Legt Artikel in den Warenkorb
	 */

	public static Result inWarenkorb(String ausgewaehltesProdukt, String menge) {
		String username = session("User1");
		if (username != null) {
			Model.sharedInstance.setWarenkorb(ausgewaehltesProdukt, menge);
			return ok(warenkorb.render(username, Model.sharedInstance.getWarenkorb(session("UserKundennummer"))));
		} else {
			return redirect("/login");
		}
		
		

	}

	/*
	 * Login
	 */

	public static Result login() {
		session().clear();
		return ok(login.render(userForm, "guest"));
	}
	
	public static Result loginError(){
		return ok(loginFehler.render("guest"));
	}

	public static Result submitLogin() {
		Form<Kunde> filledForm = userForm.bindFromRequest();

	

			session().clear();
			if(Model.sharedInstance.loginUeberpruefung(filledForm.get())!=null){
				session("User1",Model.sharedInstance.loginUeberpruefung(filledForm.get()).vorname);
				session("UserKundennummer",Model.sharedInstance.loginUeberpruefung(filledForm.get()).kundenNummer);
				System.out.println(session("User1"));
				System.out.println(session("UserKundennummer"));
				return redirect("/mainPage");
			} else {
				return redirect("/loginFehler");
			}
			

		
	}


}
