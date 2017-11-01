package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Question {
	 
	int question_id;
	String text;
	int difficulty_level;
	String hint;
	int topic_id;
	String detailed_explanation;
	boolean is_parametrized;
	
	public Question(ResultSet result) throws SQLException {
		this.question_id = result.getInt("question_id");
		this.text = result.getString("text");
		this.difficulty_level = result.getInt("difficulty_level");
		this.hint = result.getString("hint");
		this.topic_id = result.getInt("topic_id");
		this.detailed_explanation = result.getString("detailed_explanation");
		this.is_parametrized = result.getInt("is_parametrized") == 1;
	}

	public void print() {
		System.out.println(this.question_id +"\t" + this.text + "\t" + this.difficulty_level + "\t" + this.hint + "\t" + this.topic_id + "\t" + this.detailed_explanation + "\t" + this.is_parametrized);
	}

}
