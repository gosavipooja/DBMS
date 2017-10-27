package model;

/**
 * 
 * @author pooja
 *
 */

public class TeachingAssistant extends Student {
	private String taId;

	public TeachingAssistant(Student std, String taID) {
		// TODO Auto-generated constructor stub
		super(std);
		this.taId = taID;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}
	
}
