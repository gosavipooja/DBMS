package model;

/**
 * 
 * @author pooja
 *
 */

public class TeachingAssistant extends Student {
	private String taId;

	public TeachingAssistant(String taId) {
		super();
		this.taId = taId;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}
	
}
