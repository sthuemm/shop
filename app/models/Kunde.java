package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Kunde {
		
		public String vorname;
		public String name;
		public int kundenNummer;
		public String passwort;
		public boolean isAdmin;
		
		
		public Kunde(){
			
		}
		
		public Kunde(String vorname, String name, int kundenNummer, String passwort, boolean isAdmin){
			this.name = name;
			this.vorname = vorname;
			this.kundenNummer = kundenNummer;
			this.passwort = passwort;
			this.isAdmin = isAdmin;
		}

		@Override
		public String toString() {
			return "Kunde [vorname=" + vorname + ", name=" + name
					+ ", kundenNummer=" + kundenNummer + ", passwort="
					+ passwort + ", isAdmin=" + isAdmin + "]";
		}
		
	
	
}
