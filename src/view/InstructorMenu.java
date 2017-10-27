package view;

import java.util.List;
import java.sql.Connection;
import java.util.Scanner;

import connection.ConnectionManager;
import connection.FetchQueries;
import model.Course;
import model.Instructor;
import model.User;
import utils.InputScanner;
import utils.Session;

public class InstructorMenu {
	public void showMenu() {
		Scanner sc = InputScanner.getScanner();
		boolean flag = true;
		while (flag) {
			
			System.out.println("MAIN MENU");
			System.out.println("1. View profile");
			System.out.println("2. View courses");
			System.out.println("3. Add a course");
			System.out.println("4. Enroll student in course");
			System.out.println("5. Drop student from course");
			System.out.println("6. View report");
			System.out.println("7. Setup TA");
			System.out.println("8. View exercise");
			System.out.println("9. Add exercise");
			System.out.println("10. Search question in bank");
			System.out.println("11. Add question in bank");
			System.out.println("12. Remove question from exercise");
			System.out.println("13. Log out");
			
			int choice = Integer.valueOf(sc.nextLine());
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				viewCourses();
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			case 13:
				Session.logOut();
				flag = false;
				break;
			default: 
				System.out.println("Invalid choice, please try again!");
				
			}
			
		}
		
		System.out.println("Goodbye!");
		
	}
	
	void viewProfile() {
		User user = Session.getUser();
		Connection connection = new ConnectionManager().getConnection();
		Instructor instr = FetchQueries.getInstructorDetails(user);
		System.out.println("************PROFILE************");
		System.out.println("Name: " + instr.getName());
		System.out.println("Email: " + instr.getEmail());
		System.out.println("ID: " + instr.getUserId());
		System.out.println("Office address: " + instr.getOfficeAddress());
		System.out.println("************END************");
	}
	
	void viewCourses() {
		List<Course> courses = FetchQueries.fetchCourses();
	}
}
