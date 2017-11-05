package model;

/**
 * 
 * @author pooja
 *
 */

public class Student extends User{
	private String studentId;
	private String level;
	
	public Student(User user) {
		super(user);
	}
	
	public Student(Student std) {
		studentId = std.studentId;
		level = std.level;
	}
	
	public Student(User user, String studentId, String level) {
		super(user);
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
	
	public void print() {
		System.out.println("PROFILE DETAILS");
		System.out.println("Name: " + getName());
		System.out.println("Level: " + getLevel());
		System.out.println("Email: " + getEmail());
	}
	
}
