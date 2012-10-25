package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the concept database table.
 * 
 */
@Entity
@Table(name="concept")
public class Concept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=300)
	private String description;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to ConceptVariable
	@OneToMany(mappedBy="concept")
	private List<ConceptVariable> conceptVariables;

    public Concept() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ConceptVariable> getConceptVariables() {
		return this.conceptVariables;
	}

	public void setConceptVariables(List<ConceptVariable> conceptVariables) {
		this.conceptVariables = conceptVariables;
	}
	
}