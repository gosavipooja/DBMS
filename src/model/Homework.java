package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Homework {

	int homeworkId;
	String name;
	String postedDate;
	String deadline;
	int allowedAttempts;
	int correctPoints;
	int incorrectPoints;
	int courseId;
	boolean is_adaptive;
	
	public Homework() {
		
	}
	
	public Homework(ResultSet result) throws SQLException {
		homeworkId = result.getInt("homework_id");
		allowedAttempts = result.getInt("allowed_attempts");
		correctPoints = result.getInt("correct_points");
		incorrectPoints = result.getInt("incorrect_points");
		courseId = result.getInt("course_id");
		name = result.getString("name");
		postedDate = result.getString("posted_date");
		deadline = result.getString("deadline");
		is_adaptive = result.getInt("is_adaptive")==1;
	}
	
	public void print() {
		System.out.println("************HOMEWORK DETAILS************");
		System.out.println("Name: "+ name);
		System.out.println("Posted date: "+ postedDate);
		System.out.println("Deadline: "+ deadline);
		System.out.println("Allowed attempts: "+ allowedAttempts);
		System.out.println("Correct points: "+ correctPoints);
		System.out.println("Incorrect points: "+ incorrectPoints);
	}
	
	public boolean isAdaptive() {
		return is_adaptive;
	}
	
	public int getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getAllowedAttempts() {
		return allowedAttempts;
	}

	public void setAllowedAttempts(int allowedAttempts) {
		this.allowedAttempts = allowedAttempts;
	}

	public int getCorrectPoints() {
		return correctPoints;
	}

	public void setCorrectPoints(int correctPoints) {
		this.correctPoints = correctPoints;
	}

	public int getIncorrectPoints() {
		return incorrectPoints;
	}

	public void setIncorrectPoints(int incorrectPoints) {
		this.incorrectPoints = incorrectPoints;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return name;
	}
}
