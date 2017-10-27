package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.Instructor;
import model.Student;
import model.TeachingAssistant;
import model.User;
import utils.StringUtils;

public class UpdateQueries {
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
