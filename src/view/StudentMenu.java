package view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import connection.ConnectionManager;
import connection.FetchQueries;
import connection.UpdateQueries;
import model.*;
import utils.InputScanner;
import utils.Session;

public class StudentMenu {
	public void showMenu() {
		Scanner sc = InputScanner.getScanner();
		boolean flag = true;
		while (flag) {
			System.out.println("\n\n");
			System.out.println("******* STUDENT MENU *******");
			System.out.println("1. View profile");
			System.out.println("2. Edit profile");
			System.out.println("3. View/Select courses");
			System.out.println("4. Log out");
			
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				editProfile();
				break;
			case 3:
				selectCourses();
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
		System.out.println("Name:\t\t" + stu.getName());
		System.out.println("Email:\t\t" + stu.getEmail());
		System.out.println("Student ID:\t\t" + stu.getUserId());
		System.out.println("Level:\t\t" + level);
		System.out.println("************END************");
		System.out.println("\n\n");
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to close connection");
		}
		
	}
	
	private void editProfile() {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		Connection connection = new ConnectionManager().getConnection();
		Student old_stu = FetchQueries.getStudentDetails(connection,user);
		
		boolean flag = true;
		while(flag) {
			System.out.println("\n\n***** EDIT STUDENT PROFILE *****");
			System.out.println("1. Edit Name");
			System.out.println("2. Edit Password");
			System.out.println("3. Go Back");
	
			int choice = Integer.valueOf(sc.nextLine());
			switch (choice) {
			case 0:
				flag=false; 
				break;
			case 1:
				System.out.println("\n\nPlease enter the new name:");
				String name = sc.nextLine();
				// Don't update if blank string is given
				if (name.length() > 0) {
					UpdateQueries.updateUserName(connection, user, name);
				}
				break;
			case 2:
				System.out.println("\n\nPlease enter the new password:");
				String passwd = sc.nextLine();
				// Don't update if blank string is given
				if (passwd.length() > 0) {
					UpdateQueries.updateUserPassword(connection, user, passwd);
				}
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid choice, please try again!");
			}
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to close connection");
		}
	}
	
	private void selectCourses() {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		Connection connection = new ConnectionManager().getConnection();
		List <Course> cList = FetchQueries.getCoursesByStudent(connection, user);
		
		while(true) {
			System.out.println("\n\n***** SELECT COURSES *****");
			for(int i=0; i<cList.size(); i++) {
				Course course = cList.get(i);
				System.out.println(""+(i+1)+". "+course);
			}
			
			if(cList.size()==0) {
				System.out.println("You have not enrolled in any course");
				return;
			}
			
			System.out.println("\nEnter your choice for more information about course:");
			int choice = sc.nextInt();
			
			if(choice <= 0) return;
			else if (choice > cList.size()) {
				System.out.println("Invalid choice. Please try again");
				continue;
			} 
			
			// Retrieve the appropriate course
			Course course = cList.get(choice-1);
			getHomeworksByCourse(course);
		}
		
	}
	
	
	private void getHomeworksByCourse(Course course) {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		while(true) {
			System.out.println("\n\n***** SELECT Homeworks for "+course.getCourseCode()+" *****");
			System.out.println("1. Current Open Homeworks");
			System.out.println("2. Past Homeworks");
			System.out.println("\nEnter your choice:");
			int ch = sc.nextInt();
			
			if(ch==1) {
				getCurrentHomeworksForCourse(course);
			} else if (ch==2) {
				getPastHomeworksForCourse(course);
			} else if (ch == 0) return;
			else {
				System.out.println("\nInvalid choice");
				continue;
			}
		}
		
	}
	
	private void getPastHomeworksForCourse(Course course) {
		Connection connection = new ConnectionManager().getConnection();
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		List<Homework> hList = FetchQueries.getPastExercisesByCourse(connection, user, course);
		
		if(hList.isEmpty()) {
			System.out.println("\nNo past homeworks found");
			return;
		}
		
		System.out.println("\n\n***** Your Report for Past Homeworks of "+course.getCourseCode()+" *****");
		
		for(int i=0;i<hList.size();i++) {
			Homework hw = hList.get(i);
			connection = new ConnectionManager().getConnection();
			Report report = FetchQueries.getReportByExercise(connection, user, hw);
			System.out.println(""+(i+1)+". "+hw+"\t--\t"+((report!=null)?report:"UNATTEMPTED"));
		}			
	}
	
	private void getCurrentHomeworksForCourse(Course course) {
		Connection connection = new ConnectionManager().getConnection();
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		List<Homework> hList = FetchQueries.getCurrentExercisesByCourse(connection, user, course);
		
		if(hList.isEmpty()) {
			System.out.println("\nNo current homeworks");
			return;
		}
		
		while (true) {
			System.out.println("\n\n***** Homeworks Due for "+course.getCourseCode()+" *****");
			
			for(int i=0;i<hList.size();i++) {
				Homework hw = hList.get(i);
				System.out.println(""+(i+1)+". "+hw);
			}	
			
			System.out.println("\nSelect the HW which you would like to attempt:");
			int ch =sc.nextInt();
			
			if (ch<=0) return;
			else if (ch>hList.size()) {
				System.out.println("\nInvalid Choice");
				continue;
			} else {
				//Allow the user to attempt the HW
				attemptHw(hList.get(ch-1));
			}
		}
	}
	
	private void attemptHw(Homework hw) {
		System.out.println("\n\n***** Attempting "+hw+" *****");
		System.out.println("Max attempts allowed = " + ((hw.getAllowedAttempts()<0)?"Unlimited":hw.getAllowedAttempts()) );
		System.out.println("Points for correct answer = "+hw.getCorrectPoints());
		System.out.println("Points for incorrect answer = "+hw.getIncorrectPoints());
	}
	
}
