package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the answer database table.
 * 
 */
@Entity
@Table(name="answer")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnswerPK id;

	//bi-directional many-to-one association to Instance
    @ManyToOne
	@JoinColumn(name="instance_id", nullable=false, insertable=false, updatable=false)
	private Instance instance;

    public Answer() {
    }

	public AnswerPK getId() {
		return this.id;
	}

	public void setId(AnswerPK id) {
		this.id = id;
	}
	
	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	
}