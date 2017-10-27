package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Instructor;
import model.Student;
import model.TeachingAssistant;
import model.User;
import utils.StringUtils;

public class FetchQueries {
	
	public static User fetchLoginUser (String username, String password, Connection connection) {
		User currentUser = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.LOGIN);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				currentUser = new User(result.getString("user_id"), result.getString("name"), result.getString("email"), result.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the user!! "+e.getMessage());
			e.printStackTrace();
		} 
		return currentUser;
	}
	
	public static Student checkIfStudent(Connection connection, User user) {
		Student std = null;		
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_STUDENT);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("User is not a student!");
			}else {
				System.err.println("Student exists!!");
				while(result.next()) {
					std = new Student(result.getString("student_id"),result.getString("education_level"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db"+e.getMessage());
		}
		return std;
	}
	
	public static TeachingAssistant checkIfTA(Connection connection, Student std) {
		TeachingAssistant ta = null;		
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_TA);
			pst.setString(1, std.getStudentId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Student is not a TA!");
			}else {
				System.out.println("TA detected");
				while(result.next()) {
					ta = new TeachingAssistant(result.getString("ta_id"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		}
		return ta;
	}
	
	public static Instructor getInstructorDetails(Connection connection, User user) {
		Instructor instr = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_INSTRUCTORS);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				System.out.println("Instructor detected");
				while(result.next()) {
					instr = new Instructor(result.getString("instructor_id"), result.getString("office_location"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		}
		return instr;
	}
}
 