package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.Instructor;
import model.Student;
import model.TeachingAssistant;
import model.User;
import utils.StringUtils;

public class UpdateQueries {
	
	public static boolean addTAToCourse(String ta, int course) {
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.ADD_TA_TO_COURSE);
			pst.setString(1, ta);
			pst.setInt(2, course);
			int result = pst.executeUpdate();
			if(result == 0) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public static boolean addStudentToCourse(String student, int course) {
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.ADD_STUDENT_TO_COURSE);
			pst.setString(1, student);
			pst.setInt(2, course);
			int result = pst.executeUpdate();
			if(result == 0) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public static boolean dropStudentFromCourse(String student, int course) {
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.DROP_STUDENT_FROM_COURSE);
			pst.setString(1, student);
			pst.setInt(2, course);
			int result = pst.executeUpdate();
			if(result == 0) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static void updateUserName(Connection connection, User user, String name) {
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.UPDATE_USER_NAME);
			pst.setString(1, name);
			pst.setString(2, user.getUserId());
			int result = pst.executeUpdate();
			if(result == 0) {
				System.out.println("Some issue....");
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		}
	}
	
	public static void updateUserPassword(Connection connection, User user, String passwd) {
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.UPDATE_USER_PASSWORD);
			pst.setString(1, passwd);
			pst.setString(2, user.getUserId());
			int result = pst.executeUpdate();
			if(result == 0) {
				System.out.println("Some issue....");
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		}
	}
	
}
