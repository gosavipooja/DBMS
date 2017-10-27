package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import connection.FetchQueries;
import connection.UpdateQueries;
import model.Course;
import model.CourseReport;
import model.Homework;
import model.Instructor;
import model.User;
import utils.InputScanner;
import utils.Session;

public class InstructorMenu {
	public void showMenu() {
		Scanner sc = InputScanner.getScanner();
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
			
			int choice = Integer.valueOf(sc.nextLine());
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				viewCourses();
				break;
			case 3:
				break;
			case 4:
			{
				System.out.println("Enter Student id: ");
				String student = InputScanner.getScanner().nextLine();
				System.out.println("Enter course id: ");
				int course = Integer.valueOf(InputScanner.getScanner().nextLine());
				enrollStudentInCourse(student, course);
				break;
			}
			case 5:
			{
				System.out.println("Enter Student id: ");
				String student = InputScanner.getScanner().nextLine();
				System.out.println("Enter course id: ");
				int course = Integer.valueOf(InputScanner.getScanner().nextLine());
				dropStudentFromCourse(student, course);
				break;
			}
			case 6:
			{
				System.out.println("Enter course id: ");
				int course = Integer.valueOf(InputScanner.getScanner().nextLine());
				viewReport(course);
				break;
			}
			case 7:
			{
				System.out.println("Enter course id: ");
				int course = Integer.valueOf(InputScanner.getScanner().nextLine());
				System.out.println("Enter TA id you want to add to this course: ");
				String ta = InputScanner.getScanner().nextLine();
				addTAsToCourse(course, ta);
				break;
			}
			case 8:
			{
				System.out.println("Enter course id: ");
				int course = Integer.valueOf(InputScanner.getScanner().nextLine());
				viewExerciseDetails(course);
			}
				break;
			case 9:
				break;
			case 10:
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
		
		System.out.println("Goodbye!");
		
	}
	
	void viewProfile() {
		User user = Session.getUser();
		Instructor instr = FetchQueries.getInstructorDetails(user);
		instr.print();
	}
	
	void viewCourses() {
		System.out.println("Enter course code: ");
		String code = InputScanner.getScanner().nextLine();
		Course course = FetchQueries.fetchCourseByCode(code);
		if(course == null) {
			System.out.println("Course not found!!");
			return;
		}
		course.print();
		
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
			int choice = Integer.valueOf(InputScanner.getScanner().nextLine());
			switch(choice) {
			case 0: return;
			case 1: 
				viewExerciseDetails(course.getCourse_id());
				break;
			case 2:
				break;
			case 3:
				viewTAs(course.getCourse_id());
				break;
			case 4: 
			{
				System.out.println("Enter TA id you want to add to this course: ");
				String ta = InputScanner.getScanner().nextLine();
				addTAsToCourse(course.getCourse_id(), ta);
				break;
			}
			case 5: 
			{
				System.out.println("Enter Student id you want to enroll in this course: ");
				String student = InputScanner.getScanner().nextLine();
				enrollStudentInCourse(student, course.getCourse_id());
				break;
			}
			case 6: 
			{ 
				System.out.println("Enter Student id you want to drop from this course: ");
				String student = InputScanner.getScanner().nextLine();
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
	
	void viewExerciseDetails(int course_id) {
		List<Integer> ids = FetchQueries.getExerciseIDsForCourse(course_id);
		if(ids.isEmpty()) System.out.println("No exercises exist for this course or the course doesnt exist, sorry!!");
		else {
			System.out.println("Valid exercise ids for this course are " + Arrays.toString(ids.toArray()));
			System.out.println("Enter which one you want to view: ");
			int choice = Integer.valueOf(InputScanner.getScanner().nextLine());
			Homework hw = FetchQueries.getExerciseByID(choice);
			if(hw == null) System.out.println("Wrong input!");
			else {
				hw.print();
			}
			
		}
	}
	
	void viewTAs(int course_id) {
		List<String> tas = FetchQueries.getTAsForCourse(course_id);
		System.out.println("TAs for this course are: " + Arrays.toString(tas.toArray()));
	}
	
	void addTAsToCourse(int course_id, String ta) {
		
		if(UpdateQueries.addTAToCourse(ta, course_id)) {
			System.out.println("Success!");
		} else {
			System.out.println("Something went wrong, maybe the TA doesn't exist, try again!");
		}
	}
	
	void enrollStudentInCourse(String student, int course_id) {
		if(UpdateQueries.addStudentToCourse(student, course_id)) {
			System.out.println("Success!");
		} else {
			System.out.println("Something went wrong, maybe the student doesn't exist, try again!");
		}
	}
	
	void dropStudentFromCourse(String student, int course_id) {
		if(UpdateQueries.dropStudentFromCourse(student, course_id)) {
			System.out.println("Success!");
		} else {
			System.out.println("Something went wrong, maybe the student doesn't exist, try again!");
		}
	}
	
	void viewReport(int courseid) {
		List<CourseReport> reports = FetchQueries.getReportsForCourse(courseid);
		CourseReport.printHeader();
		for(CourseReport report: reports) {
			report.print();
		}
			
	}
}
