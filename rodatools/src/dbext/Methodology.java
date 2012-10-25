package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the methodology database table.
 * 
 */
@Entity
@Table(name = "methodology")
public class Methodology implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "mode_collection", nullable = false, length = 200)
	private String modeCollection;

	@Column(name = "sampling_procedure", nullable = false, length = 200)
	private String samplingProcedure;

	@Column(name = "time_method", nullable = false, length = 200)
	private String timeMethod;

	@Column(nullable = false, length = 200)
	private String weighting;

	// bi-directional many-to-one association to Instance
	@ManyToOne
	@JoinColumn(name = "instance_id", nullable = false)
	private Instance instance;

	public Methodology() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModeCollection() {
		return this.modeCollection;
	}

	public void setModeCollection(String modeCollection) {
		this.modeCollection = modeCollection;
	}

	public String getSamplingProcedure() {
		return this.samplingProcedure;
	}

	public void setSamplingProcedure(String samplingProcedure) {
		this.samplingProcedure = samplingProcedure;
	}

	public String getTimeMethod() {
		return this.timeMethod;
	}

	public void setTimeMethod(String timeMethod) {
		this.timeMethod = timeMethod;
	}

	public String getWeighting() {
		return this.weighting;
	}

	public void setWeighting(String weighting) {
		this.weighting = weighting;
	}

	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

}