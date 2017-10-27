package utils;

public class StringUtils {

	//Access the user class
	public static final String GET_USER = "SELECT user_id as userId, name as name, email as email FROM user";
	//Prepared Statement for Login
	public static final String LOGIN = "SELECT * from user WHERE user_id = ? AND password = ?";
	//Check if the user is a student
	public static final String CHECK_STUDENT = "SELECT * FROM student std where std.student_id = ?";
	//Check if student is TA
	public static final String CHECK_TA = "SELECT * FROM teaching_assistant ta where ta.ta_id = ?";
	//Obtain instructor details
	public static final String GET_INSTRUCTORS = "SELECT * FROM instructor instr where instr.instructor_id = ?";
	//Obtain all courses
	public static final String GET_COURSES = "SELECT * FROM courses";
}
