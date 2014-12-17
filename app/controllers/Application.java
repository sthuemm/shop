package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form;

import views.html.*;
import models.*;

public class Application extends Controller {

	final static Form<Kunde> userForm = Form.form(Kunde.class); 
	
	
	public static Result index() {

		return ok(mainPage.render(Model.sharedInstance.getKunde()));

	}

	public static Result agb() {

		return ok(agb.render(Model.sharedInstance.getKunde()));

	}

	public static Result artikel() {

		return ok(artikel.render(Model.sharedInstance.getKunde()));

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

		return ok(konto.render(Model.sharedInstance.getKunde()));

	}


	public static Result login() {
		
		return ok(login.render(userForm));
	}
	
	public static Result submit(){
		Form<Kunde> filledForm = userForm.bindFromRequest();
		System.out.println("Formtest: "+filledForm);
		Kunde created = filledForm.get();
		Model.sharedInstance.setKunde(created);
		
		System.out.println(created);
		return ok(mainPage.render(Model.sharedInstance.loginUeberpruefung(created)));
	}

	public static Result mainPage() {

		return ok(mainPage.render(Model.sharedInstance.getKunde()));

	}

	public static Result neuheiten() {

		return ok(neuheiten.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.getProdukte()));

	}

	public static Result registrierung() {

		return ok(registrierung.render(Model.sharedInstance.getKunde()));

	}

	public static Result logout() {

		return ok(mainPage.render(Model.sharedInstance.logout()));
	}

	public static Result autover(String produkt) {

		return ok(Model.sharedInstance.autovervollstaendigung(produkt));

	}

	public static Result neuerUser(String Vorname, String Nachname,
			String Username, String Email, String Str, String Hausnr, String Plz,
			String Ort, String Telefon, String Passwort) {
		Model.sharedInstance.addKunden(Vorname,Nachname,Username,Email,Str,Hausnr,Plz,
				Ort,Telefon,Passwort);
		return ok(registrierung.render(Model.sharedInstance.getKunde()));
	}

	public static Result suche(String produkt) {

		return ok(suchergebnisse.render(Model.sharedInstance.getKunde(),
				Model.sharedInstance.produktSuchen(produkt)));
	}
	
	public static Result produktInsert(String preis, String artikelNummer, String artikelBezeichnung, String bildPfad, String kategorie) {
		Model.sharedInstance.produktInserieren(Double.parseDouble(preis), artikelNummer, artikelBezeichnung, bildPfad, kategorie);
		return ok(neuesProdukt.render(Model.sharedInstance.getKunde()));
	}
	
	public static Result neuesProdukt(){
		return ok(neuesProdukt.render(Model.sharedInstance.getKunde()));
	}

}
