package view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.Connection;

import connection.*;
import model.*;
import utils.*;

public class StudentMenu {
	public void showMenu() {
		Scanner sc = InputScanner.getScanner();
		boolean flag = true;
		while (flag) {
			System.out.println("\n\n");
			System.out.println("******* STUDENT MENU *******");
			System.out.println("1. View profile");
			System.out.println("2. Edit profile");
			System.out.println("3. View/Select courses");
			System.out.println("4. Log out");
			
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				viewProfile();
				break;
			case 2:
				editProfile();
				break;
			case 3:
				selectCourses();
				break;
			case 4:
				Session.logOut();
				flag = false;
				break;
			default: 
				System.out.println("Invalid choice, please try again!");
				
			}
			
		}
		
		System.out.println("Goodbye!");
		
	}
	
	private void viewProfile() {
		User user = Session.getUser();
		Student stu = FetchQueries.getStudentDetails(user);
		String level = (stu.getLevel().compareToIgnoreCase("u")==0 )?"Undergraduate":"Graduate";
		System.out.println("************PROFILE************");
		System.out.println("Name:\t\t" + stu.getName());
		System.out.println("Email:\t\t" + stu.getEmail());
		System.out.println("Student ID:\t\t" + stu.getUserId());
		System.out.println("Level:\t\t" + level);
		System.out.println("************END************");
		System.out.println("\n\n");
		
	}
	
	private void editProfile() {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		Student old_stu = FetchQueries.getStudentDetails(user);
		
		boolean flag = true;
		while(flag) {
			System.out.println("\n\n***** EDIT STUDENT PROFILE *****");
			System.out.println("1. Edit Name");
			System.out.println("2. Edit Password");
			System.out.println("3. Go Back");
	
			int choice = Integer.valueOf(sc.nextLine());
			switch (choice) {
			case 0:
				flag=false; 
				break;
			case 1:
				System.out.println("\n\nPlease enter the new name:");
				String name = sc.nextLine();
				// Don't update if blank string is given
				if (name.length() > 0) {
					UpdateQueries.updateUserName(user, name);
				}
				break;
			case 2:
				System.out.println("\n\nPlease enter the new password:");
				String passwd = sc.nextLine();
				// Don't update if blank string is given
				if (passwd.length() > 0) {
					UpdateQueries.updateUserPassword(user, passwd);
				}
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid choice, please try again!");
			}
		}
		
	}
	
	private void selectCourses() {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		List<Course> cList = FetchQueries.getCoursesByStudent(user);
		
		while(true) {
			System.out.println("\n\n***** SELECT COURSES *****");
			for(int i=0; i<cList.size(); i++) {
				Course course = cList.get(i);
				System.out.println(""+(i+1)+". "+course);
			}
			
			if(cList.size()==0) {
				System.out.println("You have not enrolled in any course");
				return;
			}
			
			System.out.println("\nEnter your choice for more information about course:");
			int choice = sc.nextInt();
			
			if(choice <= 0) return;
			else if (choice > cList.size()) {
				System.out.println("Invalid choice. Please try again");
				continue;
			} 
			
			// Retrieve the appropriate course
			Course course = cList.get(choice-1);
			getHomeworksByCourse(course);
		}
		
	}
	
	
	private void getHomeworksByCourse(Course course) {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		while(true) {
			System.out.println("\n\n***** SELECT Homeworks for "+course.getCourseCode()+" *****");
			System.out.println("1. Current Open Homeworks");
			System.out.println("2. Past Homeworks");
			System.out.println("\nEnter your choice:");
			int ch = sc.nextInt();
			
			if(ch==1) {
				getCurrentHomeworksForCourse(course);
			} else if (ch==2) {
				getPastHomeworksForCourse(course);
			} else if (ch == 0) return;
			else {
				System.out.println("\nInvalid choice");
				continue;
			}
		}
		
	}
	
	private void getPastHomeworksForCourse(Course course) {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		List<Homework> hList = FetchQueries.getPastExercisesByCourse( user, course);
		
		if(hList.isEmpty()) {
			System.out.println("\nNo past homeworks found");
			return;
		}
		
		while(true) {
			System.out.println("\n\n***** Your Past Homeworks for "+course.getCourseCode()+" *****");
			
			for(int i=0;i<hList.size();i++) {
				Homework hw = hList.get(i);
				Report report = FetchQueries.getReportByExercise(user, hw);
				System.out.println(""+(i+1)+". "+hw+"\t--\t"+((report!=null)?report:"UNATTEMPTED"));
			}		
			
			System.out.println("\nEnter a number for further details:");
			int ch = sc.nextInt();
			
			if(ch<=0) return;
			else if (ch>hList.size()) System.out.println("\nInvalid choice. Please enter your choice again");
			else {
				Homework hw = hList.get(ch-1);
				getFeedbackForHw(hw);
			}
		}
	}
	
	private void getFeedbackForHw(Homework hw) {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		
		System.out.println("\n\n***** Homework Report of "+hw+" *****");
		
		List<QuestionFeedback> hwFeedback = FetchQueries.getHwFeedback(user, hw);
		if(hwFeedback.isEmpty()) {
			System.out.println("\nThis homework was unattempted");
			return;
		}
		
		int attempt = 0;
		int counter = 1;
		int score = -1;
		for(QuestionFeedback qf:hwFeedback) {
			//Signals start of new attempt
			if(attempt != qf.getAttemptId()) {
				attempt = qf.getAttemptId();
				if(score<0) score = 0;
				else {
					System.out.println("\nScore = "+score);
					score = 0;
				}
				System.out.println("\n\n***** Attempt #"+attempt+" *****\n");
				counter = 1;
			}
			System.out.println("\n"+(counter++)+". "+qf);
			score+=qf.getScore();
		}
		
		System.out.println("\nScore = "+score);
	}
	
	private void getCurrentHomeworksForCourse(Course course) {
		Scanner sc = InputScanner.getScanner();
		User user = Session.getUser();
		List<Homework> hList = FetchQueries.getCurrentExercisesByCourse(user, course);
		
		if(hList.isEmpty()) {
			System.out.println("\nNo current homeworks");
			return;
		}
		
		while (true) {
			System.out.println("\n\n***** Homeworks Due for "+course.getCourseCode()+" *****");
			
			for(int i=0;i<hList.size();i++) {
				Homework hw = hList.get(i);
				System.out.println(""+(i+1)+". "+hw);
			}	
			
			System.out.println("\nSelect the HW which you would like to attempt:");
			int ch =sc.nextInt();
			
			if (ch<=0) return;
			else if (ch>hList.size()) {
				System.out.println("\nInvalid Choice");
				continue;
			} else {
				//Allow the user to attempt the HW
				attemptHw(hList.get(ch-1));
			}
		}
	}
	
	private List<QuizQuestion> randomlyPickOptions(List<QuizQuestion> q){
		Collections.shuffle(q);
		List<QuizQuestion> qs = new ArrayList<>();
		for(int i =0; i < Math.min(q.size(), 4); i++) {
			qs.add(q.get(i));
		}
		return qs;
	}
	
	private void attemptHw(Homework hw) {
		if(hw.isAdaptive()) {
			attemptAdaptiveHw(hw);
		} else {
			attemptNonAdaptiveHw(hw);
		}
	}
	
	private void attemptNonAdaptiveHw(Homework hw) {
		User user = Session.getUser();
		Scanner sc = InputScanner.getScanner();
		
		int score = 0;
		
		//Check for remaining attempts
		int maxAttemptsExhausted = FetchQueries.fetchAttemptsbyHomework(user, hw);
		int attemptId = maxAttemptsExhausted + 1;
		
		if(maxAttemptsExhausted >= hw.getAllowedAttempts() && hw.getAllowedAttempts() > 0) {
			System.out.println("\n\n"+(hw.getAllowedAttempts()-maxAttemptsExhausted)+" attempt(s) remaining for this homework");
		}
		else if(hw.getAllowedAttempts() < 0) {
			System.out.println("\n\nUnlimited attempts remaining for the homework");
		}
		else {
			System.out.println("\n\nYou have exhausted the maximum possible attempts for this homework");
			return;
		}
		
		System.out.println("\n\n***** Attempting "+hw+" *****");
		System.out.println("Max attempts allowed = " + ((hw.getAllowedAttempts()<0)?"Unlimited":hw.getAllowedAttempts()) );
		System.out.println("Points for correct answer = "+hw.getCorrectPoints());
		System.out.println("Points for incorrect answer = "+hw.getIncorrectPoints());
		System.out.println("Enter 0 at any point to cancel the quiz");
		System.out.println("\n\n");
		
		//Get the list of questions
		HashMap<Integer,List<QuizQuestion>> questions = FetchQueries.fetchQuestionsByHomework(user,hw);
		
		List<Integer> attemptedOpts= new ArrayList<>();
		
		
		int counter = 1;
		for(Integer qid : questions.keySet()) {
			List <QuizQuestion> optList = randomlyPickOptions(questions.get(qid));
			
			//Print Question 
			System.out.println("\nQ"+(counter++)+". "+optList.get(0).getQuestionText());
			
			//Print if any params
			if(optList.get(0).isParam()) {
				System.out.print("Params: ");
				System.out.print(optList.get(0).getP1()+", ");
				System.out.print(optList.get(0).getP2()+", ");
				System.out.print(optList.get(0).getP3()+", ");
				System.out.print(optList.get(0).getP4()+", ");
				System.out.println(optList.get(0).getP5());
			}
			
			//Print options
			System.out.println("\nOptions:");
			for(int i = 0; i<optList.size(); i++) {
				QuizQuestion qq = optList.get(i);
				System.out.println(""+(i+1)+". "+qq.getAnswer());
			}
			
			//Get user choice
			int ch = -1;
			while (ch<0 || ch >optList.size()) {
				System.out.println("\nEnter your choice (0 to cancel the quiz):");
				ch = sc.nextInt();
			}
			
			//Exit from quiz
			if(ch==0) {
				return;
			}
			
			//Add the selected option to the attempted list
			attemptedOpts.add(optList.get(ch-1).getQuestionBankId());
			
			if(optList.get(ch-1).isCorrect()) {
				score += hw.getCorrectPoints();
			} else {
				score += hw.getIncorrectPoints();
			}
			
		}
		
		//Print score 
		System.out.println("\n\n Score = "+score);
		
		//Update attempts table
		
	}
	
	
	private void attemptAdaptiveHw(Homework hw) {
		User user = Session.getUser();
		Scanner sc = InputScanner.getScanner();
		
		//Check for remaining attempts
		int maxAttemptsExhausted = FetchQueries.fetchAttemptsbyHomework(user, hw);
		int attemptId = maxAttemptsExhausted + 1;
		int score = 0;
		
		if(maxAttemptsExhausted >= hw.getAllowedAttempts() && hw.getAllowedAttempts() > 0) {
			System.out.println("\n\n"+(hw.getAllowedAttempts()-maxAttemptsExhausted)+" attempt(s) remaining for this homework");
		}
		else if(hw.getAllowedAttempts() < 0) {
			System.out.println("\n\nUnlimited attempts remaining for the homework");
		}
		else {
			System.out.println("\n\nYou have exhausted the maximum possible attempts for this homework");
			return;
		}
		
		System.out.println("\n\n***** Attempting "+hw+" *****");
		System.out.println("Max attempts allowed = " + ((hw.getAllowedAttempts()<0)?"Unlimited":hw.getAllowedAttempts()) );
		System.out.println("Points for correct answer = "+hw.getCorrectPoints());
		System.out.println("Points for incorrect answer = "+hw.getIncorrectPoints());
		System.out.println("Enter 0 at any point to cancel the quiz");
		System.out.println("\n\n");
		
		//Get the list of questions
		HashMap<Integer,List<QuizQuestion>> questions = FetchQueries.fetchQuestionsByHomework(user,hw);
		
		//Order questions based on difficulty
		List<Integer> sortedQs = FetchQueries.fetchDifficultyByQuestions(user,hw);
		
		
		List<Integer> attemptedOpts= new ArrayList<>();
		
		
		int counter = 1;
		
		//Start with the median difficulty question
		int index = sortedQs.size()/2;
		
		while(!sortedQs.isEmpty()) {
			
			int qid = sortedQs.get(index);
			sortedQs.remove(index);
			
			List <QuizQuestion> optList = randomlyPickOptions(questions.get(qid));
			
			//Print Question 
			System.out.println("\nQ"+(counter++)+". "+optList.get(0).getQuestionText());
			
			//Print if any params
			if(optList.get(0).isParam()) {
				System.out.print("Params: ");
				System.out.print(optList.get(0).getP1()+", ");
				System.out.print(optList.get(0).getP2()+", ");
				System.out.print(optList.get(0).getP3()+", ");
				System.out.print(optList.get(0).getP4()+", ");
				System.out.println(optList.get(0).getP5());
			}
			
			//Print options
			System.out.println("\nOptions:");
			for(int i = 0; i<optList.size(); i++) {
				QuizQuestion qq = optList.get(i);
				System.out.println(""+(i+1)+". "+qq.getAnswer());
			}
			
			//Get user choice
			int ch = -1;
			while (ch<0 || ch >optList.size()) {
				System.out.println("\nEnter your choice (0 to cancel the quiz):");
				ch = sc.nextInt();
			}
			
			//Exit from quiz
			if(ch==0) {
				return;
			}
			
			//Add the selected option to the attempted list
			attemptedOpts.add(optList.get(ch-1).getQuestionBankId());
			
			//Check if the attempted question was right
			
			
			boolean isCorrect  = optList.get(ch-1).isCorrect();
			
			if(!isCorrect) {
				// pick question with less difficulty level
				index = Math.max(index-1, 0);
			} else {
				// Pick question with equal or more difficulty level
				index = Math.min(sortedQs.size()-1, index);
			}
			
			if(optList.get(ch-1).isCorrect()) {
				score += hw.getCorrectPoints();
			} else {
				score += hw.getIncorrectPoints();
			}
		}
		
		//Print score 
		System.out.println("\n\nScore = "+score);
				
		//Update attempts table
		
	}
	
	
	
}
