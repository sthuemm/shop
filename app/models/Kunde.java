package models;

import play.*;
import play.mvc.*;
import views.html.*;
import play.db.ebean.Model;

public class Kunde {
		
		public String vorname;
		public String name;
		public int kundenNummer;
		
		
		public Kunde(){
			this.name = "Mustermann";
			this.vorname = "Max";
			this.kundenNummer = 1337;
			
		}
		
		public Kunde(String vorname, String name, int kundenNummer){
			this.name = name;
			this.vorname = vorname;
			this.kundenNummer = kundenNummer;
		}
		
	
	
}
