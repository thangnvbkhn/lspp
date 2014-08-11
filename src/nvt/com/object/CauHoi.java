package nvt.com.object;

import java.io.Serializable;

public class CauHoi implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String nameQuestion;
	String choice1;
	String choice2;
	String choice3;
	String choice4;
	String answer = "0";
	String trueAnswer;

	public CauHoi() {
	}
	
	public CauHoi(String nameQuestion,String Choice1,String Choice2,String Choice3,String Choice4){
		this.nameQuestion = nameQuestion;
		this.choice1 = Choice1;
		this.choice2 = Choice2;
		this.choice3 = Choice3;
		this.choice4 = Choice4;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameQuestion() {
		return nameQuestion;
	}

	public void setNameQuestion(String nameQuestion) {
		this.nameQuestion = nameQuestion;
	}

	public String getChoice1() {
		return choice1;
	}

	public void setChoice1(String Choice1) {
		this.choice1 = Choice1;
	}

	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String Choice2) {
		this.choice2 = Choice2;
	}

	public String getChoice3() {
		return choice3;
	}

	public void setChoice3(String Choice3) {
		this.choice3 = Choice3;
	}

	public String getChoice4() {
		return choice4;
	}

	public void setChoice4(String Choice4) {
		this.choice4 = Choice4;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTrueAnswer() {
		return trueAnswer;
	}

	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}
}
