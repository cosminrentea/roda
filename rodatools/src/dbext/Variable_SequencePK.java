package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "Variable_Sequences" database table.
 * 
 */
@Embeddable
public class Variable_SequencePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "\"VariableId\"", unique = true, nullable = false)
	private Integer variableId;

	@Column(name = "\"Order\"", unique = true, nullable = false)
	private Integer order;

	public Variable_SequencePK() {
	}

	public Integer getVariableId() {
		return this.variableId;
	}

	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Variable_SequencePK)) {
			return false;
		}
		Variable_SequencePK castOther = (Variable_SequencePK) other;
		return this.variableId.equals(castOther.variableId)
				&& this.order.equals(castOther.order);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.variableId.hashCode();
		hash = hash * prime + this.order.hashCode();

		return hash;
	}
}