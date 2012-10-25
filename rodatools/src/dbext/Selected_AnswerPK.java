package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "Selected_Answers" database table.
 * 
 */
@Embeddable
public class Selected_AnswerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"QuestionnaireId\"", unique=true, nullable=false)
	private Integer questionnaireId;

	@Column(name="\"VarInStudyId\"", unique=true, nullable=false)
	private Integer varInStudyId;

	@Column(name="\"ValueId\"", unique=true, nullable=false)
	private Integer valueId;

    public Selected_AnswerPK() {
    }
	public Integer getQuestionnaireId() {
		return this.questionnaireId;
	}
	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	public Integer getVarInStudyId() {
		return this.varInStudyId;
	}
	public void setVarInStudyId(Integer varInStudyId) {
		this.varInStudyId = varInStudyId;
	}
	public Integer getValueId() {
		return this.valueId;
	}
	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Selected_AnswerPK)) {
			return false;
		}
		Selected_AnswerPK castOther = (Selected_AnswerPK)other;
		return 
			this.questionnaireId.equals(castOther.questionnaireId)
			&& this.varInStudyId.equals(castOther.varInStudyId)
			&& this.valueId.equals(castOther.valueId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.questionnaireId.hashCode();
		hash = hash * prime + this.varInStudyId.hashCode();
		hash = hash * prime + this.valueId.hashCode();
		
		return hash;
    }
}