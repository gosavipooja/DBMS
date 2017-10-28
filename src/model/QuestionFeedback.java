package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionFeedback {
	private String question;
	private String response;
	private boolean isCorrect;
	private int score;
	private int attemptId;

	public QuestionFeedback(ResultSet result) throws SQLException {
		question = result.getString("question");
		response = result.getString("response");
		isCorrect = result.getInt("isCorrect")==1;
		score = result.getInt("score");
		attemptId = result.getInt("attempt_id");
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(int attemptId) {
		this.attemptId = attemptId;
	}
	
	@Override
	public String toString() {
		return "\tQuestion : "+question+
				"\n\tResponse : "+response+
				"\n\tThis answer was marked "+((isCorrect)?"CORRECTLY":"INCORRECTLY")+
				"\n\tPoints : "+score;
	}

}
