package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Kunde {
		
		public String vorname;
		public String name;
		public String kundenNummer;
		
		
		public Kunde(){
			this.vorname = "Fremder";
			
		}
		
		public Kunde(String vorname, String name, String kundenNummer){
			this.name = name;
			this.vorname = vorname;
			this.kundenNummer = kundenNummer;
		}
		
	
	
}
