package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import utils.InputScanner;
import utils.Session;
import connection.ConnectionManager;
import connection.FetchQueries;
import model.Instructor;
import model.Student;
import model.TeachingAssistant;
import model.User;

/**
 * 
 * @author pooja
 *
 */

public class Login {
	//Task List : password encryption
	public User performLoginAction(String username, String password) {
		User user = FetchQueries.fetchLoginUser(username, password);
		if(user == null) {
			System.out.println("Failed to login");
		}else {
			System.out.println("Login Successful");
			System.out.println("User name : "+user.getName());
		}
		return user;
	}
	
	public void showLoginMenu() throws ParseException, SQLException, IOException {
		Scanner sc = InputScanner.getScanner();
		User user = null;
		boolean flag = true;
		while (flag/*true*/) {
			System.out.println("Please Login: ");
			System.out.println("Username:");
			String username = sc.nextLine();
			System.out.println("Password: ");
			String password = sc.nextLine();
			user = performLoginAction(username, password);
			if (user == null) {
				System.out.println("Invalid Login Credentials");
				System.out.println("Choose an option : ");
				System.out.println("1. Login");
				System.out.println("2. Back");
				int option = Integer.valueOf(sc.nextLine());
				if (option == 2) {
					flag = false;
					break;
				}
			} else {
				Session.logIn(user);
				System.out.println("Login Succesful");
				flag = false;
			}
		}
		
		if(user != null) {
			Student std = isStudent(user);
			if(std == null) {
				InstructorMenu menu = new InstructorMenu();
				menu.showMenu();
			}else {
				TeachingAssistant ta = isTA(std);
				if (ta == null) {
					System.out.println("Is not a TA!");
					//For student
					new StudentMenu().showMenu();
				} else{
					System.out.println("is a TA");
				}
			}
		}
	}
	
	
	public Student isStudent(User user) {
		Student std = null;
		std = FetchQueries.checkIfStudent(user);
		return std;
	}
	
	public TeachingAssistant isTA(Student std) {
		TeachingAssistant ta = null;
		ta = FetchQueries.checkIfTA(std);
		return ta;
	}
	
}