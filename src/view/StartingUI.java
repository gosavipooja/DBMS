package view;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import utils.InputScanner;

public class StartingUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		beginApplication();
	}
	
	public static void beginApplication() {
		Scanner sc = InputScanner.getScanner();
		int input = 0;
		boolean flag = true;
		while(flag) {
			System.out.println("Choose an option : ");
			System.out.println("1. Login");
			System.out.println("2. Exit");
			System.out.println("Enter Choice: ");
			input = Integer.valueOf(sc.nextLine());	
			
			switch (input) {
			case 1:
				Login login = new Login();
				try {
					login.showLoginMenu();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				flag = false;
				break;			
			case 2:
				System.out.println("Thank you");
				flag = false;
				break;
			default:
				System.out.println("Invalid input, try again.!");						
				break;
			}
		}
	}
}
