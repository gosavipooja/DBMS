package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import connection.FetchQueries;
import connection.UpdateQueries;
import model.Course;
import model.CourseReport;
import model.Homework;
import model.Instructor;
import model.Question;
import model.User;
import utils.InputScanner;
import utils.Session;

public class InstructorMenu {
	public void showMenu() {
		boolean flag = true;
		while (flag) {
			
			System.out.println("MAIN MENU");
			System.out.println("1. View profile");
			System.out.println("2. View courses");
			System.out.println("3. Add a course");
			System.out.println("4. Enroll student in course");
			System.out.println("5. Drop student from course");
			System.out.println("6. View report");
			System.out.println("7. Setup TA");
			System.out.println("8. View exercise");
			System.out.println("9. Add exercise");
			System.out.println("10. Search question in bank");
			System.out.println("11. Add question in bank");
			System.out.println("12. Remove question from exercise");
			System.out.println("13. Log out");
			
			int choice = InputScanner.scanInt();
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				viewCourses();
				break;
			case 3:
				addCourse();
				break;
			case 4:
			{
				System.out.println("Enter Student id: ");
				String student = InputScanner.scanString();
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				enrollStudentInCourse(student, course);
				break;
			}
			case 5:
			{
				System.out.println("Enter Student id: ");
				String student = InputScanner.scanString();
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				dropStudentFromCourse(student, course);
				break;
			}
			case 6:
			{
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				viewReport(course);
				break;
			}
			case 7:
			{
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				System.out.println("Enter TA id you want to add to this course: ");
				String ta = InputScanner.scanString();
				addTAsToCourse(course, ta);
				break;
			}
			case 8:
			{
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				viewExerciseDetails(course);
				break;
			}
				
			case 9:
			{
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				addExercise(course);
				break;
			}
				
			case 10:
				System.out.println("Enter a word or part of the question text");
				String keyword = InputScanner.scanString();
				showQuestionDetails(keyword);
				break;
			case 11:
				break;
			case 12:
				break;
			case 13:
				Session.logOut();
				flag = false;
				break;
			default: 
				System.out.println("Invalid choice, please try again!");
				
			}
			
		}
		InputScanner.closeScanner();
		System.out.println("Goodbye!");
		
	}
	
	
	private void showQuestionDetails(String keyword) {
		User user = Session.getUser();
		ArrayList<Question> questions = FetchQueries.fetchQuestionsByKeyword(user, keyword);
		System.out.println("************SEARCH RESULTS FOR QUESTIONS************");
		for(int idx = 0; idx<questions.size(); idx++) {
			questions.get(idx).print();
		}
	}


	void viewProfile() {
		User user = Session.getUser();
		Instructor instr = FetchQueries.getInstructorDetails(user);
		instr.print();
	}
	
	void viewCourses() {
		List<Course> courses = FetchQueries.fetchCourses();
		if(courses.size() == 0) {
			System.out.println("No courses for this instructor!");
			return;
		}
		System.out.format("%15s%25s%25s%25s", "Course id", "Name", "Start", "End");
		System.out.println();
		for(Course course: courses) {
			System.out.format("%15s%25s%25s%25s", course.getCourse_id(), course.getName(), course.getStartDate(), course.getEndDate());
			System.out.println();
		}
		System.out.println();
		System.out.println("Enter course id to see more details or -1 to return");
		int id = InputScanner.scanInt();
		if(id == -1) return;
		
		Course course = null;
		for(Course scourse: courses) {
			if(scourse.getCourse_id() == id) course = scourse;
		} 
		
		if(course == null) {
			System.out.println("You entered incorrect id");
			return;
		} else {
			viewCourseDetails(course);
		}
	}
	
	void viewCourseDetails(Course course) {
		
		while(true) {
			System.out.println("************COURSE MENU************");
			System.out.println("0. Back to main menu");
			System.out.println("1. View exercises");
			System.out.println("2. Add exercises");
			System.out.println("3. View TAs");
			System.out.println("4. Add TA");
			System.out.println("5. Enroll student");
			System.out.println("6. Drop student");
			System.out.println("7. View report");
			int choice = InputScanner.scanInt();
			switch(choice) {
			case 0: return;
			case 1: 
				viewExerciseDetails(course.getCourse_id());
				break;
			case 2:
				addExercise(course.getCourse_id());
				break;
			case 3:
				viewTAs(course.getCourse_id());
				break;
			case 4: 
			{
				System.out.println("Enter TA id you want to add to this course: ");
				String ta = InputScanner.scanString();
				addTAsToCourse(course.getCourse_id(), ta);
				break;
			}
			case 5: 
			{
				System.out.println("Enter Student id you want to enroll in this course: ");
				String student = InputScanner.scanString();
				enrollStudentInCourse(student, course.getCourse_id());
				break;
			}
			case 6: 
			{ 
				System.out.println("Enter Student id you want to drop from this course: ");
				String student = InputScanner.scanString();
				dropStudentFromCourse(student, course.getCourse_id());
				break;
			}
			case 7:
			{
				viewReport(course.getCourse_id());
				break;
			}
			default: System.out.println("Invalid Input");
			}
		}
		
	}
	
	
	void addCourse() {
		Course course = new Course();
		System.out.println("Enter id: ");
		course.setCourse_id(InputScanner.scanInt());
		System.out.println("Enter name: ");
		course.setName(InputScanner.scanString());
		System.out.println("Enter code: ");
		course.setCourseCode(InputScanner.scanString());
		System.out.println("Enter dept: ");
		course.setDepartment(InputScanner.scanString());
		System.out.println("Enter start date (YYYY-MM-DD HH:MM:SS) ");
		course.setStartDate(InputScanner.scanString());
		System.out.println("Enter end date (YYYY-MM-DD HH:MM:SS) ");
		course.setEndDate(InputScanner.scanString());
		System.out.println("Enter level (UG/Grad): ");
		course.setLevel(InputScanner.scanString());
		System.out.println("Enter max students allowed: ");
		course.setMax_students_allowed(InputScanner.scanInt());
		UpdateQueries.addCourse(course);
	}
	
	void viewExerciseDetails(int course_id) {
		if(!FetchQueries.checkIfCourseAllowed(course_id)) return;
		List<Integer> ids = FetchQueries.getExerciseIDsForCourse(course_id);
		if(ids.isEmpty()) System.out.println("No exercises exist for this course or the course doesnt exist, sorry!!");
		else {
			System.out.println("Valid exercise ids for this course are " + Arrays.toString(ids.toArray()));
			System.out.println("Enter which one you want to view: ");
			int choice = InputScanner.scanInt();
			Homework hw = FetchQueries.getExerciseByID(choice);
			if(hw == null) System.out.println("Wrong input!");
			else {
				hw.print();
			}
			
		}
	}
	
	void addExercise(int course_id) {
		if(!FetchQueries.checkIfCourseAllowed(course_id)) return;
		Homework hw = new Homework();
		
		hw.setCourseId(course_id);
		
		System.out.print("Enter exercise id: ");
		int id = InputScanner.scanInt();
		hw.setHomeworkId(id);
		
		System.out.println("Enter exercise name: ");;
		String name = InputScanner.scanString();
		hw.setName(name);
		
		System.out.println("Enter posted date (YYYY-MM-DD HH:MM:SS): ");;
		String pdate = InputScanner.scanString();
		hw.setPostedDate(pdate);
		
		System.out.println("Enter deadline (YYYY-MM-DD HH:MM:SS): ");;
		String deadline = InputScanner.scanString();
		hw.setDeadline(deadline);
		
		System.out.println("Enter allowed attempts: ");;
		int attempts = InputScanner.scanInt();
		hw.setAllowedAttempts(attempts);
		
		System.out.println("Enter correct points: ");;
		int correctPoints = InputScanner.scanInt();
		hw.setCorrectPoints(correctPoints);
		
		System.out.println("Enter incorrect points: ");;
		int incorrectPoints = InputScanner.scanInt();
		hw.setIncorrectPoints(incorrectPoints);
		
		UpdateQueries.addHomework(hw);
	}
	
	void viewTAs(int course_id) {
		List<String> tas = FetchQueries.getTAsForCourse(course_id);
		System.out.println("TAs for this course are: " + Arrays.toString(tas.toArray()));
	}
	
	void addTAsToCourse(int course_id, String ta) {
		if(!FetchQueries.checkIfCourseAllowed(course_id)) return;
		UpdateQueries.addTAToCourse(ta, course_id);
	}
	
	void enrollStudentInCourse(String student, int course_id) {
		if(!FetchQueries.checkIfCourseAllowed(course_id)) return;
		UpdateQueries.addStudentToCourse(student, course_id);
	}
	
	void dropStudentFromCourse(String student, int course_id) {
		if(!FetchQueries.checkIfCourseAllowed(course_id)) return;
		UpdateQueries.dropStudentFromCourse(student, course_id);
	}
	
	void viewReport(int courseid) {
		if(!FetchQueries.checkIfCourseAllowed(courseid)) return;
		List<CourseReport> reports = FetchQueries.getReportsForCourse(courseid);
		CourseReport.printHeader();
		for(CourseReport report: reports) {
			report.print();
		}
			
	}
	
}
