package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Kunde {
		
		public String vorname;
		public String name;
		public String kundenNummer;
		public String passwort;
		public boolean isAdmin;
		
		
		public Kunde(){
			this.vorname = "Guest";
			
		}
		
		public Kunde(String vorname, String name, String kundenNummer, String passwort, boolean isAdmin){
			this.name = name;
			this.vorname = vorname;
			this.kundenNummer = kundenNummer;
			this.passwort = passwort;
			this.isAdmin = isAdmin;
		}
		
	
	
}
