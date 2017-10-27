package model;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
	int course_id;
	String name;
	String courseCode;
	String department;
	String level;
	
	public Course(ResultSet result) throws SQLException {
		course_id = result.getInt("course_id");
		name = result.getString("name");
		courseCode = result.getString("cours_cCode");
		department = result.getString("department");
		level = result.getString("level");
	}
}
