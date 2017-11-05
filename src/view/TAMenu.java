package view;

import java.util.Arrays;
import java.util.List;
import connection.FetchQueries;
import connection.UpdateQueries;
import model.Course;
import model.CourseReport;
import model.Homework;
import model.Student;
import model.User;
import utils.InputScanner;
import utils.Session;

public class TAMenu {
	public void showMenu() {
		boolean flag = true;
		while (flag) {
			
			System.out.println("MAIN MENU");
			System.out.println("1. View profile");
			System.out.println("2. View courses");
			System.out.println("3. Enroll student in course");
			System.out.println("4. Drop student from course");
			System.out.println("5. View report");
			System.out.println("6. Logout");
			
			int choice = InputScanner.scanInt();
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				viewCourses();
				break;
			
			case 3:
			{
				System.out.println("Enter Student id: ");
				String student = InputScanner.scanString();
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				enrollStudentInCourse(student, course);
				break;
			}
			case 4:
			{
				System.out.println("Enter Student id: ");
				String student = InputScanner.scanString();
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				dropStudentFromCourse(student, course);
				break;
			}
			case 5:
			{
				System.out.println("Enter course id: ");
				int course = InputScanner.scanInt();
				viewReport(course);
				break;
			}
			case 6:
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
	
	

	void viewProfile() {
		User user = Session.getUser();
		Student s = FetchQueries.getStudentDetails(user);
		s.print();
		List<Course> c = FetchQueries.fetchCoursesForTA();
		System.out.println("Your courses ---");
		for(Course course: c) {
			System.out.println(course.getCourseCode() + ":" + course.getName());
		}
	}
	
	void viewCourses() {
		List<Course> courses = FetchQueries.fetchCoursesForTA();
		if(courses.size() == 0) {
			System.out.println("No courses for this ta!");
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
			System.out.println("2. Enroll student");
			System.out.println("3. Drop student");
			System.out.println("4. View report");
			int choice = InputScanner.scanInt();
			switch(choice) {
			case 0: return;
			case 1: 
				viewExerciseDetails(course.getCourse_id());
				break;
			
			case 2: 
			{
				System.out.println("Enter Student id you want to enroll in this course: ");
				String student = InputScanner.scanString();
				enrollStudentInCourse(student, course.getCourse_id());
				break;
			}
			case 3: 
			{ 
				System.out.println("Enter Student id you want to drop from this course: ");
				String student = InputScanner.scanString();
				dropStudentFromCourse(student, course.getCourse_id());
				break;
			}
			case 4:
			{
				viewReport(course.getCourse_id());
				break;
			}
			default: System.out.println("Invalid Input");
			}
		}
		
	}
	
	
	void viewExerciseDetails(int course_id) {
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
	
	
	void enrollStudentInCourse(String student, int course_id) {
		if(!FetchQueries.checkIfCourseAllowedForTA(course_id)) return;
		UpdateQueries.addStudentToCourse(student, course_id);
	}
	
	void dropStudentFromCourse(String student, int course_id) {
		if(!FetchQueries.checkIfCourseAllowedForTA(course_id)) return;
		UpdateQueries.dropStudentFromCourse(student, course_id);
	}
	
	void viewReport(int courseid) {
		if(!FetchQueries.checkIfCourseAllowedForTA(courseid)) return;
		List<CourseReport> reports = FetchQueries.getReportsForCourse(courseid);
		CourseReport.printHeader();
		for(CourseReport report: reports) {
			report.print();
		}
			
	}
	
}
