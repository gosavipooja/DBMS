package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizQuestion {
	private int questionId;
	private int questionBankId;
	private String questionText;
	private String explanation;
	private String questionHint;
	private int difficultyLevel;
	private String answer;
	private boolean isCorrect;
	private String p1,p2,p3,p4,p5;
	private boolean isParametrized;
	
	public QuizQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuizQuestion(int questionId, int questionBankId, String questionText, String shortExplanation,
			String detailedExplanation, String questionHint, int difficultyLevel, String answer, boolean isCorrect,
			String p1, String p2, String p3, String p4, String p5) {
		super();
		this.questionId = questionId;
		this.questionBankId = questionBankId;
		this.questionText = questionText;
		this.explanation = detailedExplanation;
		this.questionHint = questionHint;
		this.difficultyLevel = difficultyLevel;
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
	}
	
	public QuizQuestion(ResultSet result) throws SQLException {
		questionId = result.getInt("qid");
		questionBankId= result.getInt("qbid");
		questionText = result.getString("quesText");
		explanation = result.getString("explanation");
		questionHint = result.getString("quesHint");
		difficultyLevel = result.getInt("difficultyLevel");
		answer = result.getString("answer");
		isCorrect = result.getInt("isCorrect")==1;
		isParametrized = result.getInt("isParameterized")==1;
		if(isParametrized) {
			p1 = result.getString("P1");
			p2 = result.getString("P2");
			p3 = result.getString("P3");
			p4 = result.getString("P4");
			p5 = result.getString("P5");
		}
	}
	
	public boolean isParam(){
		return isParametrized;
	}
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionBankId() {
		return questionBankId;
	}
	public void setQuestionBankId(int questionBankId) {
		this.questionBankId = questionBankId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getDetailedExplanation() {
		return explanation;
	}
	public void setDetailedExplanation(String detailedExplanation) {
		this.explanation = detailedExplanation;
	}
	public String getQuestionHint() {
		return questionHint;
	}
	public void setQuestionHint(String questionHint) {
		this.questionHint = questionHint;
	}
	public int getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getP4() {
		return p4;
	}
	public void setP4(String p4) {
		this.p4 = p4;
	}
	public String getP5() {
		return p5;
	}
	public void setP5(String p5) {
		this.p5 = p5;
	}

	
}
