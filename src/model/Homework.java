package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Homework {

	int homework_id;
	String name;
	String posted_date;
	String deadline;
	int allowed_attempts;
	int correct_points;
	int incorrect_points;
	int course_id;
	
	public Homework(ResultSet result) throws SQLException {
		homework_id = result.getInt("homework_id");
		allowed_attempts = result.getInt("allowed_attempts");
		correct_points = result.getInt("correct_points");
		incorrect_points = result.getInt("incorrect_points");
		course_id = result.getInt("course_id");
		name = result.getString("name");
		posted_date = result.getString("posted_date");
		deadline = result.getString("deadline");
	}
	
	public void print() {
		System.out.println("************HOMEWORK DETAILS************");
		System.out.println("Name: "+ name);
		System.out.println("Posted date: "+ posted_date);
		System.out.println("Deadline: "+ deadline);
		System.out.println("Allowed attempts: "+ allowed_attempts);
		System.out.println("Correct points: "+ correct_points);
		System.out.println("Incorrect points: "+ incorrect_points);
	}
}
