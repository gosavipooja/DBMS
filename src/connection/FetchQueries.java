package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.Homework;
import model.Instructor;
import model.Student;
import model.TeachingAssistant;
import model.User;
import utils.StringUtils;

public class FetchQueries {
	
	public static List<Integer> getExerciseIDsForCourse(int course_id) {
		List<Integer> ids = new ArrayList<Integer>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_EXERCISE_BY_COURSE);
			pst.setLong(1, course_id);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				ids.add(result.getInt("homework_id"));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return ids;
	}
	
	public static Homework getExerciseByID(int id) {
		Homework hw = null;
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_EXERCISE_BY_ID);
			pst.setLong(1, id);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				hw = new Homework(result);
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return hw;
	}
	
	public static List<String> getTAsForCourse(int id) {
		List<String> ta = new ArrayList<String>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_TA_FOR_COURSE);
			pst.setLong(1, id);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				ta.add(result.getString("ta_id"));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return ta;
	}
	
	public static Course fetchCourseByCode(String code) {
		Course course = null;
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_COURSES);
			pst.setString(1, code);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				course = new Course(result);
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return course;
	}
	
	public static User fetchLoginUser (String username, String password) {
		Connection connection = new ConnectionManager().getConnection();
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
		} finally {
			close(connection);
		}
		return currentUser;
	}
	
	public static Student checkIfStudent(User user) {
		Connection connection = new ConnectionManager().getConnection();
		Student std = null;		
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_STUDENT);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("User is not a student!");
			}else {
				while(result.next()) {
					std = new Student(user, result.getString("student_id"),result.getString("education_level"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db"+e.getMessage());
		} finally {
			close(connection);
		}
		return std;
	}
	
	public static TeachingAssistant checkIfTA(Student std) {
		Connection connection = new ConnectionManager().getConnection();
		TeachingAssistant ta = null;		
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_TA);
			pst.setString(1, std.getStudentId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Student is not a TA!");
			}else {
				while(result.next()) {
					ta = new TeachingAssistant(std, result.getString("ta_id"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return ta;
	}
	
	public static Instructor getInstructorDetails(User user) {
		Connection connection = new ConnectionManager().getConnection();
		Instructor instr = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_INSTRUCTORS);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result.next()) {
					instr = new Instructor(result.getString("instructor_id"), user, result.getString("office_location"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return instr;
	}
	
	public static void close(Connection connection) {
		try {
			connection.close();
			} catch (SQLException e) {
				System.out.println("Failed to close connection");
			}
	}
	public static Student getStudentDetails(Connection connection, User user) {
		Student stu = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_STUDENTS);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result.next()) {
					stu = new Student(user,result.getString("student_id"),  result.getString("level"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		}
		return stu;
	}
}
 