package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the unit_analysis database table.
 * 
 */
@Entity
@Table(name = "unit_analysis")
public class UnitAnalysi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 100)
	private String name;

	// bi-directional many-to-one association to Instance
	@OneToMany(mappedBy = "unitAnalysi")
	private List<Instance> instances;

	public UnitAnalysi() {
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

	public List<Instance> getInstances() {
		return this.instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

}