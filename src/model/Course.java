package model;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
	
	int course_id;
	String name;
	String courseCode;
	String department;
	String level;
	String startDate;
	String endDate;

	public void print() {
		System.out.println("************COURSE DETAILS************");
		System.out.println("Name: " + name);
		System.out.println("Code: " + courseCode);
		System.out.println("Dept: " + department);
		System.out.println("Level: " + level);
		System.out.println("Start date: " + startDate);
		System.out.println("End date: " + endDate);
	}
	
	public Course(ResultSet result) throws SQLException {
		course_id = result.getInt("course_id");
		name = result.getString("name");
		courseCode = result.getString("course_code");
		department = result.getString("department");
		level = result.getString("level");
		startDate = result.getString("start_date");
		endDate = result.getString("end_date");
	}
	
	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
