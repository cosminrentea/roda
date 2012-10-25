package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the concept_variable database table.
 * 
 */
@Embeddable
public class ConceptVariablePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="variable_id", unique=true, nullable=false)
	private Integer variableId;

	@Column(name="concept_id", unique=true, nullable=false)
	private Integer conceptId;

    public ConceptVariablePK() {
    }
	public Integer getVariableId() {
		return this.variableId;
	}
	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}
	public Integer getConceptId() {
		return this.conceptId;
	}
	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConceptVariablePK)) {
			return false;
		}
		ConceptVariablePK castOther = (ConceptVariablePK)other;
		return 
			this.variableId.equals(castOther.variableId)
			&& this.conceptId.equals(castOther.conceptId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.variableId.hashCode();
		hash = hash * prime + this.conceptId.hashCode();
		
		return hash;
    }
}