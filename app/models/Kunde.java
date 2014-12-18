package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Kunde {
		
		public String kundenNummer;
		public String vorname;
		public String nachname;
		public String benutzername;
		public String email;
		public String strasse;
		public String hausnummer;
		public String plz;
		public String ort;
		public String telefonnummer;
		public String passwort;
		public boolean isAdmin;
		
		
		public Kunde(){
			
		}
		
		public Kunde(String kundenNummer, String vorname, String nachname, String username, String email, String strasse,
					String hausnummer, String plz, String ort, String telefonnummer, String passwort, boolean isAdmin){
			this.kundenNummer = kundenNummer;
			this.nachname = nachname;
			this.vorname = vorname;
			this.benutzername = username;
			this.email = email;
			this.strasse = strasse;
			this.hausnummer = hausnummer;
			this.plz = plz;
			this.ort = ort;
			this.telefonnummer = telefonnummer;
			this.passwort = passwort;
			this.isAdmin = isAdmin;
		}

		@Override
		public String toString() {
			return "Kunde [kundenNummer=" + kundenNummer + ", vorname="
					+ vorname + ", nachname=" + nachname + ", username="
					+ benutzername + ", email=" + email + ", strasse=" + strasse
					+ ", hausnummer=" + hausnummer + ", plz=" + plz + ", ort="
					+ ort + ", telefonnummer=" + telefonnummer + ", passwort="
					+ passwort + ", isAdmin=" + isAdmin + "]";
		}

		
		
	
	
}
