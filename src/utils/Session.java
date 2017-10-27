package utils;
import model.User;

public class Session {
	private static User currentUser;
	
	public static void logIn(User user) {
		currentUser = user;
	}
	
	public static boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public static void logOut() {
		currentUser = null;
	}
	
	public static User getUser() {
		return currentUser;
	}
	
}
