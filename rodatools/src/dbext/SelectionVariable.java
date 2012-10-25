package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the selection_variable database table.
 * 
 */
@Entity
@Table(name = "selection_variable")
public class SelectionVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "instance_id")
	private Integer instanceId;

	@Column(name = "max_count", nullable = false)
	private Integer maxCount;

	@Column(name = "min_count", nullable = false)
	private Integer minCount;

	// bi-directional one-to-one association to Variable
	@OneToOne
	@JoinColumn(name = "variable_id", nullable = false)
	private Variable variable;

	// bi-directional many-to-one association to SelectionVariableItem
	@OneToMany(mappedBy = "selectionVariable")
	private List<SelectionVariableItem> selectionVariableItems;

	public SelectionVariable() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getMaxCount() {
		return this.maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Integer getMinCount() {
		return this.minCount;
	}

	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public List<SelectionVariableItem> getSelectionVariableItems() {
		return this.selectionVariableItems;
	}

	public void setSelectionVariableItems(
			List<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

}