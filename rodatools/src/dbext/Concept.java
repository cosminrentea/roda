package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the concept database table.
 * 
 */
@Entity
@Table(name = "concept")
public class Concept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 300)
	private String description;

	@Column(nullable = false, length = 100)
	private String name;

	// bi-directional many-to-many association to Variable
	@ManyToMany
	@JoinTable(name = "concept_variable", joinColumns = { @JoinColumn(name = "concept_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "variable_id", nullable = false) })
	private List<Variable> variables;

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

	public List<Variable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

}