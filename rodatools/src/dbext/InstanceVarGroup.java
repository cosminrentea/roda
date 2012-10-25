package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the instance_var_group database table.
 * 
 */
@Entity
@Table(name = "instance_var_group")
public class InstanceVarGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	// bi-directional one-to-one association to Variable
	@OneToOne
	@JoinColumn(name = "variable_id", nullable = false)
	private Variable variable;

	// bi-directional many-to-one association to VariableGroup
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private VariableGroup variableGroup;

	public InstanceVarGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public VariableGroup getVariableGroup() {
		return this.variableGroup;
	}

	public void setVariableGroup(VariableGroup variableGroup) {
		this.variableGroup = variableGroup;
	}

}