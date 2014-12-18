package models;

public class wrongPasswordOrUsernameException extends Exception {
	
	wrongPasswordOrUsernameException(){
		super("Passwort oder Benutzername falsch");
	}
	
}
