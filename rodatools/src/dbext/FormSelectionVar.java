package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the form_selection_var database table.
 * 
 */
@Entity
@Table(name = "form_selection_var")
public class FormSelectionVar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "instance_id")
	private Integer instanceId;

	@Column(nullable = false)
	private Integer ordering;

	// bi-directional many-to-one association to SelectionVariableItem
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "item_id", referencedColumnName = "selection_variable_id", nullable = false),
			@JoinColumn(name = "variable_id", referencedColumnName = "item_id", nullable = false) })
	private SelectionVariableItem selectionVariableItem;

	public FormSelectionVar() {
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

	public Integer getOrdering() {
		return this.ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}

	public SelectionVariableItem getSelectionVariableItem() {
		return this.selectionVariableItem;
	}

	public void setSelectionVariableItem(
			SelectionVariableItem selectionVariableItem) {
		this.selectionVariableItem = selectionVariableItem;
	}

}