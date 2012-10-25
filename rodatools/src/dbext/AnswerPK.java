package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the answer database table.
 * 
 */
@Embeddable
public class AnswerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="form_id", unique=true, nullable=false)
	private Integer formId;

	@Column(name="instance_id", unique=true, nullable=false)
	private Integer instanceId;

	@Column(name="variable_id", unique=true, nullable=false)
	private Integer variableId;

    public AnswerPK() {
    }
	public Integer getFormId() {
		return this.formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public Integer getInstanceId() {
		return this.instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getVariableId() {
		return this.variableId;
	}
	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnswerPK)) {
			return false;
		}
		AnswerPK castOther = (AnswerPK)other;
		return 
			this.formId.equals(castOther.formId)
			&& this.instanceId.equals(castOther.instanceId)
			&& this.variableId.equals(castOther.variableId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode();
		hash = hash * prime + this.instanceId.hashCode();
		hash = hash * prime + this.variableId.hashCode();
		
		return hash;
    }
}