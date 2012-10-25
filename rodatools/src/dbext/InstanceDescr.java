package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the instance_descr database table.
 * 
 */
@Entity
@Table(name = "instance_descr")
public class InstanceDescr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "abstract", nullable = false)
	private String abstract_;

	@Column(nullable = false, length = 100)
	private String title;

	// bi-directional many-to-one association to Instance
	@ManyToOne
	@JoinColumn(name = "instance_id", nullable = false)
	private Instance instance;

	public InstanceDescr() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

}