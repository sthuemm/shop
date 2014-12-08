package controllers;
	

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;
import java.util.List;
import java.lang.*;


	public class Application extends Controller {
	
	

	
		
		public static Result index() {
//			Model.sharedInstance.generateKunden();
			Model.sharedInstance.generateProdukts();
		
			return ok(mainPage.render(Model.sharedInstance.getGuest()));
		
	}
		
		public static Result agb() {
			
		
		
				return ok(agb.render(Model.sharedInstance.getGuest()));
			
		}
		
		public static Result artikel() {
			
				return ok(artikel.render(Model.sharedInstance.getGuest()));
			
		}
		
		public static Result datenschutz() {
			
		
				return ok(datenschutz.render(Model.sharedInstance.getGuest()));
			
		}
		
		public static Result holzAussen() {
			
			
				return ok(holzAussen.render(Model.sharedInstance.getGuest(),Model.sharedInstance.getProdukteAussen()));
			
			
		}
		
		public static Result impressum() {
			
			
				return ok(impressum.render(Model.sharedInstance.getGuest()));
			
		}
		
		public static Result holzInnen() {
			
			
				return ok(holzInnen.render(Model.sharedInstance.getGuest(),Model.sharedInstance.getProdukteInnen()));
			
		}
		
		public static Result brennholz() {
			
			
				return ok(brennholz.render(Model.sharedInstance.getGuest(),Model.sharedInstance.getProdukteBrennholz()));
			
		}
		
		public static Result kontakt() {
			
			
				return ok(kontakt.render(Model.sharedInstance.getGuest()));
			
		}	
		
		public static Result konto() {
			
			
				return ok(konto.render(Model.sharedInstance.getGuest()));
			
		}
		
		public static Result login() {
			
		
			return ok(login.render(Model.sharedInstance.getGuest()));
		}
		
		public static Result mainPage() {
				
			
				return ok(mainPage.render(Model.sharedInstance.getGuest()));
			
		}
		
		public static Result neuheiten() {	
				
				
		
				return ok(neuheiten.render(Model.sharedInstance.getGuest(),Model.sharedInstance.getProdukte()));
			
		}	
		
		public static Result registrierung() {
			
		
				return ok(registrierung.render(Model.sharedInstance.getGuest()));
			
			
		}	
		
		public static Result main(String user, String userPasswort) {
	
			return ok(mainPage.render(Model.sharedInstance.loginUeberpruefung(user, userPasswort)));
			
		}
		
		
		public static Result logout() {
			
			return ok(mainPage.render(Model.sharedInstance.getGuest()));
		}
		
		public static Result autover(){
		
		return ok(main.render(Model.sharedInstance.))
		
		}
		
		
	}
	
	
