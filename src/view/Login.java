package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

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
		Connection connection = new ConnectionManager().getConnection();
		User user = FetchQueries.fetchLoginUser(username, password, connection);
		if(user == null) {
			System.out.println("Failed to login");
		}else {
			System.out.println("Login Successful");
			System.out.println("User name : "+user.getName());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot close Connection");
			e.printStackTrace();
		}
		return user;
	}
	
	public void showLoginMenu() throws ParseException, SQLException, IOException {
		Scanner sc = new Scanner(System.in);
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
		sc.close();
		System.out.println("I am here");
		
		if(user != null) {
			//Check if the user is a student or a teacher
			System.out.println("My user is not null");
			Student std = isStudent(user);
			if(std == null) {
				System.out.println("Is not a student");
				System.out.println("Is an instructor");
				Instructor instr = fetchInstructor(user);
				
			}else {
				System.out.println("Student found!");
				TeachingAssistant ta = isTA(std);
				if (ta == null) {
					System.out.println("Is not a TA!");
				} else{
					System.out.println("is a TA");
				}
			}
		}
	}
	
	
	public Student isStudent(User user) {
		Student std = null;
		Connection connection = new ConnectionManager().getConnection();
		std = FetchQueries.checkIfStudent(connection,user);
		return std;
	}
	
	public TeachingAssistant isTA(Student std) {
		TeachingAssistant ta = null;
		Connection connection = new ConnectionManager().getConnection();
		ta = FetchQueries.checkIfTA(connection,std);
		return ta;
	}
	
	public Instructor fetchInstructor(User user) {
		Instructor instr = null;
		Connection connection = new ConnectionManager().getConnection();
		instr = FetchQueries.getInstructorDetails(connection,user);
		return instr;
	}
}