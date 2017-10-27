package view;

import java.sql.Connection;
import java.util.Scanner;

import connection.ConnectionManager;
import connection.FetchQueries;
import model.Instructor;
import model.Student;
import model.User;
import utils.InputScanner;
import utils.Session;

public class StudentMenu {
	public void showMenu() {
		Scanner sc = InputScanner.getScanner();
		boolean flag = true;
		while (flag) {
			
			System.out.println("MAIN MENU");
			System.out.println("1. View profile");
			System.out.println("2. Edit profile");
			System.out.println("3. View courses");
			System.out.println("4. Log out");
			
			int choice = Integer.valueOf(sc.nextLine());
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				Session.logOut();
				flag = false;
				break;
			default: 
				System.out.println("Invalid choice, please try again!");
				
			}
			
		}
		
		System.out.println("Goodbye!");
		
	}
	
	private void viewProfile() {
		User user = Session.getUser();
		Connection connection = new ConnectionManager().getConnection();
		Student stu = FetchQueries.getStudentDetails(connection,user);
		String level = (stu.getLevel().compareToIgnoreCase("u")==0 )?"Undergraduate":"Graduate";
		
		System.out.println("************PROFILE************");
		System.out.println("Name:\t" + stu.getName());
		System.out.println("Email:\t" + stu.getEmail());
		System.out.println("ID:\t" + stu.getUserId());
		System.out.println("Level:\t" + level);
		System.out.println("************END************");
		
	}
	
	private void editProfile() {
		
	}
}
