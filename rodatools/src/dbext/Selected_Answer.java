package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the "Selected_Answers" database table.
 * 
 */
@Entity
@Table(name = "\"Selected_Answers\"")
public class Selected_Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Selected_AnswerPK id;

	@Column(name = "\"editedAnswer\"", nullable = false)
	private String editedAnswer;

	public Selected_Answer() {
	}

	public Selected_AnswerPK getId() {
		return this.id;
	}

	public void setId(Selected_AnswerPK id) {
		this.id = id;
	}

	public String getEditedAnswer() {
		return this.editedAnswer;
	}

	public void setEditedAnswer(String editedAnswer) {
		this.editedAnswer = editedAnswer;
	}

}