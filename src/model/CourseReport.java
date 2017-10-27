package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseReport {

	String student_id;
	String name;
	int homework_id;
	int score;
	
	public CourseReport(ResultSet result) throws SQLException {
		student_id = result.getString("student_id");
		name = result.getString("name");
		homework_id = result.getInt("homework_id");
		score = result.getInt("score");
	}
	
	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHomework_id() {
		return homework_id;
	}
	public void setHomework_id(int homework_id) {
		this.homework_id = homework_id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public static void printHeader() {
		System.out.format("%15s%25s%15s%15s", "Student id", "Name", "Homework id", "Score");
		System.out.println();
	}
	
	public void print() {
		System.out.format("%15s%25s%15s%15s", student_id, name, homework_id, score);
		System.out.println();
	}
	
	
}
