package model;

/**
 * 
 * @author pooja
 *
 */

public class Student extends User{
	private String studentId;
	private String level;
	
	public Student() {
		super();
	}
	
	public Student(String studentId, String level) {
		super();
		this.studentId = studentId;
		this.level = level;
	}

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
}
