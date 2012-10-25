package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the concept_variable database table.
 * 
 */
@Entity
@Table(name="concept_variable")
public class ConceptVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConceptVariablePK id;

	//bi-directional many-to-one association to Concept
    @ManyToOne
	@JoinColumn(name="concept_id", nullable=false, insertable=false, updatable=false)
	private Concept concept;

    public ConceptVariable() {
    }

	public ConceptVariablePK getId() {
		return this.id;
	}

	public void setId(ConceptVariablePK id) {
		this.id = id;
	}
	
	public Concept getConcept() {
		return this.concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
}