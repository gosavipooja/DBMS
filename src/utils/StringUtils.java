package utils;

public class StringUtils {

	/** USER **/
	//Access the user class
	public static final String GET_USER = "SELECT user_id as userId, name as name, email as email FROM user";
	//Update User name
	public static final String UPDATE_USER_NAME = "UPDATE user SET name = ? WHERE user_id = ?";
	//Update User Password
	public static final String UPDATE_USER_PASSWORD = "UPDATE user SET password = ? WHERE user_id = ?";
	//Prepared Statement for Login
	public static final String LOGIN = "SELECT * from user WHERE user_id = ? AND password = ?";
	//Check if the user is a student
	public static final String CHECK_STUDENT = "SELECT * FROM student std where std.student_id = ?";
	//Check if student is TA
	public static final String CHECK_TA = "SELECT * FROM teaching_assistant ta where ta.ta_id = ?";
	//Obtain instructor details
	public static final String GET_INSTRUCTORS = "SELECT * FROM instructor instr where instr.instructor_id = ?";
	//Obtain all courses
	public static final String GET_COURSES_FOR_INSTRUCTOR = "SELECT * FROM course c, instructor_teaches i where "
			+ "c.course_id = i.course_id and i.instructor_id = ?";
	public static final String GET_EXERCISE_BY_COURSE = "SELECT * FROM homework where course_id = ?";
	public static final String GET_EXERCISE_BY_ID = "SELECT * FROM homework where homework_id = ?";
	public static final String GET_TA_FOR_COURSE = 
			"SELECT t.ta_id as ta_id "
			+ "FROM teaching_assistant_assists t, teaching_assistant ta "
			+ "where t.ta_id = ta.ta_id and t.course_id = ?";
	public static final String REMOVE_QUESTION_FROM_EXERCISE = "delete from homework_question_bank "
			+ "where homework_id = ? and question_bank_id in ( "
			+ "select h.homework_id "
			+ "from homework_question_bank h, question_bank qb, question q "
			+ "where q.question_id = ? and q.question_id = qb.question_id and qb.question_bank_id = h.question_bank_id)";
	
	
	/** STUDENTS **/
	//Get Students' details
	public static final String GET_STUDENTS = "SELECT * FROM student stu where stu.student_id = ?";
	
	//Get Students' details
	public static final String UPDATE_STUDENTS = "SELECT * FROM student stu where stu.student_id = ?";
	
	//Get courses for a student
	public static final String GET_COURSES_BY_STUDENTS = "SELECT * FROM Gradiance.enrollment e "
			+"JOIN Gradiance.course c ON  e.course_id=c.course_id "
			+"WHERE e.student_id = ?";
	
	//Get past exercise by course
	public static final String GET_PAST_EXERCISES_BY_COURSE = "SELECT * FROM homework where course_id = ? AND deadline < ?";

	//Get current exercise by course
	public static final String GET_CURRENT_EXERCISES_BY_COURSE = "SELECT * FROM homework where course_id = ? AND posted_date <= ? AND deadline >= ?";
	
	//Get report by homework
	public static final String GET_REPORT_BY_EXERCISE = "SELECT * FROM report r WHERE r.student_id = ? AND r.homework_id = ?";
	
	/** COURSE **/
	// Add TA to course
	public static final String ADD_TA_TO_COURSE = "INSERT into teaching_assistant_assists(ta_id, course_id) values (?, ?)";
	
	// Add Student to course
	public static final String ADD_STUDENT_TO_COURSE = "INSERT into enrollment(student_id, course_id) values (?, ?)";
	
	// Drop student from course
	public static final String DROP_STUDENT_FROM_COURSE = "DELETE FROM enrollment where student_id = ? and course_id = ?";
	
	// Add homework
	public static final String ADD_HOMEWORK = "INSERT into "
			+ "homework(homework_id, name, posted_date, deadline, allowed_attempts, correct_points, incorrect_points, course_id) "
			+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
	
	// Get report for course
	public static final String GET_REPORT_FOR_COURSE = 
			"select s.student_id, u.name, r.homework_id, r.score " + 
			"from enrollment e, student s, homework h, report r, user u " + 
			"where e.course_id = ? and e.student_id = s.student_id " + 
			"and h.course_id = e.course_id and r.student_id = s.student_id " + 
			"and r.homework_id = h.homework_id " + 
			"and s.student_id = u.user_id " + 
			"order by s.student_id, homework_id;"; 

	//Get feedback for homework
	public static final String GET_EXERCISE_FEEDBACK = "SELECT q.text as question,ans.text as response, qb.correct as isCorrect, (qb.correct*hw.correct_points + (1-qb.correct)*hw.incorrect_points) as score, atm.attempt_id "
														+ " FROM attempt as atm"
														+ " JOIN question_bank as qb ON atm.question_bank_id=qb.question_bank_id"
														+ " JOIN question as q ON q.question_id=qb.question_id"
														+ " JOIN answer as ans ON ans.answer_id=qb.answer_id"
														+ " JOIN homework as hw ON atm.homework_id=hw.homework_id"
														+ " WHERE atm.student_id = ? AND atm.homework_id = ?"
														+ " ORDER BY atm.attempt_id, question";
	
	public static final String ADD_COURSE = "INSERT into "
			+ "course(course_id, name, course_code, department, max_students_allowed, level, start_date, end_date) "
			+ "values(?,?,?,?,?,?,?,?)";

}
