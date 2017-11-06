package utils;
import java.util.Scanner;

public class InputScanner {
	
	static private Scanner sc = new Scanner(System.in);
	
	static public Scanner getScanner() {
		if(sc == null) {
			sc = new Scanner(System.in);
		}
		return sc;
	}
	
	static public void closeScanner() {
		sc.close();
	}
	
	public static String scanString() {
		return sc.nextLine();
	}
	
	public static int scanInt() {
		return sc.nextInt();//Integer.valueOf(sc.nextLine());
	}
	
}
