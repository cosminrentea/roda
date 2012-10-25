package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the skip database table.
 * 
 */
@Entity
@Table(name = "skip")
public class Skip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String condition;

	@Column(name = "instance_id")
	private Integer instanceId;

	// bi-directional many-to-one association to Variable
	@ManyToOne
	@JoinColumn(name = "next_variable_id", nullable = false)
	private Variable variable1;

	// bi-directional many-to-one association to Variable
	@ManyToOne
	@JoinColumn(name = "variable_id", nullable = false)
	private Variable variable2;

	public Skip() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public Variable getVariable1() {
		return this.variable1;
	}

	public void setVariable1(Variable variable1) {
		this.variable1 = variable1;
	}

	public Variable getVariable2() {
		return this.variable2;
	}

	public void setVariable2(Variable variable2) {
		this.variable2 = variable2;
	}

}