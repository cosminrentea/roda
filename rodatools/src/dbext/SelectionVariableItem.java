package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the selection_variable_item database table.
 * 
 */
@Entity
@Table(name = "selection_variable_item")
public class SelectionVariableItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SelectionVariableItemPK id;

	@Column(name = "instance_id")
	private Integer instanceId;

	@Column(nullable = false)
	private Integer ordering;

	// bi-directional many-to-one association to FormSelectionVar
	@OneToMany(mappedBy = "selectionVariableItem")
	private List<FormSelectionVar> formSelectionVars;

	// bi-directional many-to-one association to Frequency
	@ManyToOne
	@JoinColumn(name = "frequency_id", nullable = false)
	private Frequency frequency;

	// bi-directional many-to-many association to InstanceDocument
	@ManyToMany
	@JoinTable(name = "selection_variable_card", joinColumns = {
			@JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false),
			@JoinColumn(name = "variable_id", referencedColumnName = "selection_variable_id", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "instance_id", referencedColumnName = "document_id", nullable = false),
			@JoinColumn(name = "response_card", referencedColumnName = "instance_id", nullable = false) })
	private List<InstanceDocument> instanceDocuments;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	private Item item;

	// bi-directional many-to-one association to SelectionVariable
	@ManyToOne
	@JoinColumn(name = "selection_variable_id", nullable = false, insertable = false, updatable = false)
	private SelectionVariable selectionVariable;

	public SelectionVariableItem() {
	}

	public SelectionVariableItemPK getId() {
		return this.id;
	}

	public void setId(SelectionVariableItemPK id) {
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

	public List<FormSelectionVar> getFormSelectionVars() {
		return this.formSelectionVars;
	}

	public void setFormSelectionVars(List<FormSelectionVar> formSelectionVars) {
		this.formSelectionVars = formSelectionVars;
	}

	public Frequency getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public List<InstanceDocument> getInstanceDocuments() {
		return this.instanceDocuments;
	}

	public void setInstanceDocuments(List<InstanceDocument> instanceDocuments) {
		this.instanceDocuments = instanceDocuments;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public SelectionVariable getSelectionVariable() {
		return this.selectionVariable;
	}

	public void setSelectionVariable(SelectionVariable selectionVariable) {
		this.selectionVariable = selectionVariable;
	}

}