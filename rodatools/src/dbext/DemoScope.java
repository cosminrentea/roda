package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the demo_scope database table.
 * 
 */
@Entity
@Table(name = "demo_scope")
public class DemoScope implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String description;

	// bi-directional many-to-one association to DemoSlice
	@OneToMany(mappedBy = "demoScope")
	private List<DemoSlice> demoSlices;

	// bi-directional many-to-one association to Instance
	@OneToMany(mappedBy = "demoScope")
	private List<Instance> instances;

	public DemoScope() {
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

	public List<DemoSlice> getDemoSlices() {
		return this.demoSlices;
	}

	public void setDemoSlices(List<DemoSlice> demoSlices) {
		this.demoSlices = demoSlices;
	}

	public List<Instance> getInstances() {
		return this.instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

}