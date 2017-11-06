package utils;
import java.util.Scanner;

public class InputScanner {
	
	static private Scanner sc = new Scanner(System.in);
	
	static public void closeScanner() {
		sc.close();
	}
	
	public static String scanString() {
		return sc.nextLine();
	}
	
	public static int scanInt() {
		return Integer.valueOf(sc.nextLine());
	}
	
}
