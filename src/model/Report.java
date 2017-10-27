package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {
	private int reportId;
	private String studentId;
	private int homeworkId;
	private int attempts;
	private int score;
	
	
	public Report(ResultSet result) throws SQLException {
		reportId = result.getInt("report_id");
		studentId = result.getString("student_id");
		homeworkId = result.getInt("homework_id");
		attempts = result.getInt("attempts");
		score = result.getInt("score");
	}
	
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Attempts = "+attempts+"\tscore = "+score;
	}

}
