package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "Variable_Sequences" database table.
 * 
 */
@Entity
@Table(name="\"Variable_Sequences\"")
public class Variable_Sequence implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Variable_SequencePK id;

    public Variable_Sequence() {
    }

	public Variable_SequencePK getId() {
		return this.id;
	}

	public void setId(Variable_SequencePK id) {
		this.id = id;
	}
	
}