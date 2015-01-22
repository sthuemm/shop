package controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukte("alle")));
		} else {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukte("alle")));
		}

	}

	public static Result agb() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(agb.render(Model.sharedInstance.getCustomerName(kunde)));
		} else {
			return ok(agb.render(Model.sharedInstance.getCustomerName("0000")));
		}

	}

	public static Result artikel(String ausgewaehltesProdukt) {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(artikel.render(Model.sharedInstance
					.getCustomerName(kunde), Model.sharedInstance
					.artikelnummerSuchen(ausgewaehltesProdukt), userForm));
		} else {
			return ok(artikel.render(Model.sharedInstance
					.getCustomerName("0000"), Model.sharedInstance
					.artikelnummerSuchen(ausgewaehltesProdukt), userForm));
		}

	}

	public static Result datenschutz() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(datenschutz.render(Model.sharedInstance
					.getCustomerName(kunde)));
		} else {
			return ok(datenschutz.render(Model.sharedInstance
					.getCustomerName("0000")));
		}
	}

	public static Result holzAussen() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(holzAussen.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukteAussen()));
		} else {
			return ok(holzAussen.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukteAussen()));
		}

	}

	public static Result impressum() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(impressum.render(Model.sharedInstance
					.getCustomerName(kunde)));
		} else {
			return ok(impressum.render(Model.sharedInstance
					.getCustomerName("0000")));
		}

	}

	public static Result holzInnen() {
		String kunde = session("Kundennummer");

		if (kunde != null) {
			return ok(holzInnen.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukteInnen()));
		} else {
			return ok(holzInnen.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukteInnen()));
		}
	}

	public static Result brennholz() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(brennholz.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukteBrennholz()));
		} else {
			return ok(brennholz.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukteBrennholz()));
		}

	}

	public static Result kontakt() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(kontakt.render(Model.sharedInstance
					.getCustomerName(kunde)));
		} else {
			return ok(kontakt.render(Model.sharedInstance
					.getCustomerName("0000")));
		}
	}


	public static Result konto() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(konto.render(Model.sharedInstance.getCustomer(kunde),Model.sharedInstance.bestellungenKunde(kunde),
					userForm));
		} else {
			return redirect("/login");
		}

	}

	public static Result bestellungAbschliessen() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			Model.sharedInstance.bestellArtikelAusWarenkorb(kunde);
			return redirect("/");
		} else {
			return redirect("/login");
		}
	}

	public static WebSocket<JsonNode> socket() {

		return new WebSocket<JsonNode>() {

			public void onReady(WebSocket.In<JsonNode> in,
					final WebSocket.Out<JsonNode> out) {
				System.out.println(Model.sharedInstance.getTime()
						+ ": WebSocketArtikel ready...");
				final ShopObserver obs = new ShopObserver();
				obs.shop = out;
				System.out.println(Model.sharedInstance.getTime()
						+ ": Anzahl observer: "
						+ Model.sharedInstance.countObservers());
				in.onMessage(new Callback<JsonNode>() {
					public void invoke(JsonNode obj) {

						// out.write(Model.sharedInstance.zeigeAktuelleMenge(obj));

					}

				});

				in.onClose(new Callback0() {
					public void invoke() {
						// observer.remove(id);
						Model.sharedInstance.deleteObserver(obs);

						System.out.println(Model.sharedInstance.getTime()
								+ ": Artikelansicht verlassen...");
						System.out.println(Model.sharedInstance.getTime()
								+ ": Anzahl observer: "
								+ Model.sharedInstance.countObservers());
					}
				});

			}
		};
	}

	// public static WebSocket<String> socketInWarenkorb() {
	//
	// return new WebSocket<String>() {
	// public void onReady(WebSocket.In<String> in,
	// final WebSocket.Out<String> out) {
	// System.out.println(Model.sharedInstance.getTime()
	// + ": WebSocketArtikel ready...");
	// in.onMessage(new Callback<String>() {
	// public void invoke(String artikelNummer) {
	// System.out.println("neue Menge...");
	// out.write(Model.sharedInstance
	// .getProduktJson(artikelNummer));
	//
	// }
	// });
	//
	// in.onClose(new Callback0() {
	// public void invoke() {
	// System.out.println(Model.sharedInstance.getTime()
	// + ": Artikelansicht verlassen...");
	// }
	// });
	//
	// }
	// };
	// }

	public static Result submitKundendaten() {
		Form<Kunde> filledForm = userForm.bindFromRequest();
		System.out.println(filledForm);
		String kunde = session("Kundennummer");
		Model.sharedInstance.aendereKundendaten(filledForm.get());
		if (kunde != null) {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukteAlle()));
		} else {
			return redirect("/registrierung");
		}

	}

	public static Result mainPage() {
		String kunde = session("Kundennummer");

		if (kunde != null) {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukte("alle")));
		} else {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukte("alle")));
		}
	}

	public static Result neuheiten() {

		String kunde = session("Kundennummer");

		if (kunde != null) {
			return ok(neuheiten.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukteAlle()));
		} else {
			return ok(neuheiten.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukteAlle()));
		}

	}

	public static Result registrierung() {
		String kunde = session("Kundennummer");

		if (kunde != null) {
			return ok(registrierung.render(userForm,
					Model.sharedInstance.getCustomerName(kunde)));
		} else {
			return ok(registrierung.render(userForm,
					Model.sharedInstance.getCustomerName("0000")));
		}

	}

	public static Result logout() {
		String kunde = session("Kundennummer");
		Model.sharedInstance.removeCustomerFromMap(kunde);
		session().clear();

		return redirect("/mainPage");

	}

	public static Result neuerUser() {
		String kunde = session("Kundennummer");
		boolean kundeAngelegt = true;
		Form<Kunde> filledForm = userForm.bindFromRequest();
		System.out.println(filledForm.get());
		try {
			if(!Model.sharedInstance.addCustomer(filledForm.get())){
				kundeAngelegt = false;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (kunde != null && kundeAngelegt) {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getProdukteAlle()));
		} else if (!kundeAngelegt){
			return redirect("/registrierung");
		} else {
			return ok(mainPage.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.getProdukteAlle()));
		}
	}

	public static Result login() {
		session().clear();
		return ok(login.render(userForm, "guest"));
	}

	public static Result loginError() {
		return ok(loginFehler.render(Model.sharedInstance
				.getCustomerName("0000")));
	}

	public static Result submitLogin() {
		Form<Kunde> filledForm = userForm.bindFromRequest();
System.out.println(filledForm);
		session().clear();
		Kunde kunde = Model.sharedInstance.loginUeberpruefung(filledForm.get());
		if (kunde != null) {
			session("Kundennummer",kunde.kundenNummer);
			return redirect("/mainPage");
		} else {
			return redirect("/loginFehler");
		}

	}

	public static Result suche(String produkt) {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(suchergebnisse.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.produktSuchen(produkt)));
		} else {
			return ok(suchergebnisse.render(
					Model.sharedInstance.getCustomerName("0000"),
					Model.sharedInstance.produktSuchen(produkt)));
		}

	}

	public static Result warenkorb() {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			return ok(warenkorb.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getWarenkorb(session("Kundennummer"))));
		} else {
			return redirect("/login");
		}

	}

	public static Result inWarenkorb(String ausgewaehltesProdukt, String menge) {
		String kunde = session("Kundennummer");
		if (kunde != null) {
			Model.sharedInstance.setWarenkorb(ausgewaehltesProdukt, menge,
					session("Kundennummer"));
			return ok(warenkorb.render(
					Model.sharedInstance.getCustomerName(kunde),
					Model.sharedInstance.getWarenkorb(session("Kundennummer"))));
		} else {
			return redirect("/login");
		}
	}

	public static Result autovervollstaendigungSuche(String produkt) {
		return ok(Model.sharedInstance.autovervollstaendigungSuche(produkt));

	}
}
