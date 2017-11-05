package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;
import utils.StringUtils;
import utils.DateUtils;
import utils.Session;

public class FetchQueries {
	
	public static List<Integer> getExerciseIDsForCourse(int course_id) {
		List<Integer> ids = new ArrayList<Integer>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_EXERCISE_BY_COURSE);
			pst.setLong(1, course_id);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				ids.add(result.getInt("homework_id"));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return ids;
	}
	
	public static List<CourseReport> getReportsForCourse(int courseid) {
		List<CourseReport> report = new ArrayList<CourseReport>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_REPORT_FOR_COURSE);
			pst.setLong(1, courseid);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				report.add(new CourseReport(result));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return report;
	}
	
	public static Homework getExerciseByID(int id) {
		Homework hw = null;
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_EXERCISE_BY_ID);
			pst.setLong(1, id);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				hw = new Homework(result);
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return hw;
	}
	
	public static List<String> getTAsForCourse(int id) {
		List<String> ta = new ArrayList<String>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_TA_FOR_COURSE);
			pst.setLong(1, id);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				ta.add(result.getString("ta_id"));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the course!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return ta;
	}
	
	public static List<Course> fetchCourses() {
		List<Course> courses = new ArrayList<Course>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_COURSES_FOR_INSTRUCTOR);
			pst.setString(1, Session.getUser().getUserId());
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				courses.add(new Course(result));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			close(connection);
		}
		return courses;
	}
	
	public static List<Course> fetchCoursesForTA() {
		List<Course> courses = new ArrayList<Course>();
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_COURSES_FOR_TA);
			pst.setString(1, Session.getUser().getUserId());
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				courses.add(new Course(result));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			close(connection);
		}
		return courses;
	}
	
	public static boolean checkIfCourseAllowed(int course_id) {
		Connection connection = new ConnectionManager().getConnection();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_IF_COURSE_ID_ALLOWED);
			pst.setInt(1, course_id);
			pst.setString(2, Session.getUser().getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null || result.getFetchSize() == 0) {
				System.out.println("Access to course not allowed.");
				return false;
			}
			else return true;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			close(connection);
		}
		return false;
	}
	
	public static boolean checkIfCourseAllowedForTA(int course_id) {
		List<Course> courses = fetchCoursesForTA();
		for(Course c: courses) {
			if(c.getCourse_id() == course_id) return true;
		} 
		return false;
	}
	
	public static User fetchLoginUser (String username, String password) {
		Connection connection = new ConnectionManager().getConnection();
		User currentUser = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.LOGIN);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				currentUser = new User(result.getString("user_id"), result.getString("name"), result.getString("email"), result.getString("password"));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch the user!! "+e.getMessage());
			e.printStackTrace();
		} finally {
			close(connection);
		}
		return currentUser;
	}
	
	public static Student checkIfStudent(User user) {
		Connection connection = new ConnectionManager().getConnection();
		Student std = null;		
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_STUDENT);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("User is not a student!");
			}else {
				while(result.next()) {
					std = new Student(user, result.getString("student_id"),result.getString("education_level"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db"+e.getMessage());
		} finally {
			close(connection);
		}
		return std;
	}
	
	public static TeachingAssistant checkIfTA(Student std) {
		Connection connection = new ConnectionManager().getConnection();
		TeachingAssistant ta = null;		
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.CHECK_TA);
			pst.setString(1, std.getStudentId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Student is not a TA!");
			}else {
				while(result.next()) {
					ta = new TeachingAssistant(std, result.getString("ta_id"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return ta;
	}
	
	public static Instructor getInstructorDetails(User user) {
		Connection connection = new ConnectionManager().getConnection();
		Instructor instr = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_INSTRUCTORS);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result.next()) {
					instr = new Instructor(result.getString("instructor_id"), user, result.getString("office_location"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return instr;
	}
	
	
	
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Failed to close connection");
		}
	}
	
	public static Student getStudentDetails(User user) {
		Connection connection = new ConnectionManager().getConnection();
		Student stu = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_STUDENTS);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result.next()) {
					stu = new Student(user,result.getString("student_id"),  result.getString("education_level"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return stu;
	}
	
	public static List<Course> getCoursesByStudent(User user) {
		Connection connection = new ConnectionManager().getConnection();
		List <Course> cList = new ArrayList<>();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_COURSES_BY_STUDENTS);
			pst.setString(1, user.getUserId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result != null && result.next()) {
					cList.add( new Course(result)) ;
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return cList;
	}
	
	public static List<Homework> getPastExercisesByCourse(User user, Course course) {
		Connection connection = new ConnectionManager().getConnection();
		List <Homework> hList = new ArrayList<>();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_PAST_EXERCISES_BY_COURSE);
			pst.setInt(1, course.getCourse_id());
			pst.setString(2, DateUtils.getCurrentDate());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result != null && result.next()) {
					hList.add( new Homework(result)) ;
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return hList;
	}
	
	public static List<Homework> getCurrentExercisesByCourse(User user, Course course) {
		Connection connection = new ConnectionManager().getConnection();
		List <Homework> hList = new ArrayList<>();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_CURRENT_EXERCISES_BY_COURSE);
			pst.setInt(1, course.getCourse_id());
			pst.setString(2, DateUtils.getCurrentDate());
			pst.setString(3, DateUtils.getCurrentDate());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result != null && result.next()) {
					hList.add( new Homework(result)) ;
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return hList;
	}
	
	public static Report getReportByExercise(User user, Homework hw) {
		Connection connection = new ConnectionManager().getConnection();
		Report report = null;
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_REPORT_BY_EXERCISE);
			pst.setString(1, user.getUserId());
			pst.setInt(2, hw.getHomeworkId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result != null && result.next()) {
					report = new Report(result);
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return report;
	}
	
	public static List<QuestionFeedback> getHwFeedback(User user, Homework hw) {
		Connection connection = new ConnectionManager().getConnection();
		List<QuestionFeedback> hwFeedback = new ArrayList<>();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_EXERCISE_FEEDBACK);
			pst.setString(1, user.getUserId());
			pst.setInt(2, hw.getHomeworkId());
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result != null && result.next()) {
					hwFeedback.add(new QuestionFeedback(result));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return hwFeedback ;
	}

	public static ArrayList<Question> fetchQuestionsByKeyword(User user, String keyword) {
		Connection connection = new ConnectionManager().getConnection();
		ArrayList<Question> questions = new ArrayList<>();
		try {
			PreparedStatement pst = connection.prepareStatement(StringUtils.GET_QUESTIONS);
			pst.setString(1, user.getUserId());
			pst.setString(2, "%" + keyword + "%");
			ResultSet result = pst.executeQuery();
			if(result == null) {
				System.out.println("Some issue....");
			}else {
				while(result != null && result.next()) {
					questions.add(new Question(result));
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to check from db");
		} finally {
			close(connection);
		}
		return questions;
	}
}
 