package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the variable database table.
 * 
 */
@Entity
@Table(name = "variable")
public class Variable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 200)
	private String label;

	@Column(name = "operator_instructions", nullable = false)
	private String operatorInstructions;

	@Column(nullable = false)
	private Integer ordering;

	@Column(name = "response_type", nullable = false)
	private Integer responseType;

	@Column(nullable = false)
	private Integer type;

	// bi-directional many-to-many association to Concept
	@ManyToMany(mappedBy = "variables")
	private List<Concept> concepts;

	// bi-directional one-to-one association to EditedVariable
	@OneToOne(mappedBy = "variable")
	private EditedVariable editedVariable;

	// bi-directional one-to-one association to InstanceVarGroup
	@OneToOne(mappedBy = "variable")
	private InstanceVarGroup instanceVarGroup;

	// bi-directional one-to-one association to SelectionVariable
	@OneToOne(mappedBy = "variable")
	private SelectionVariable selectionVariable;

	// bi-directional many-to-one association to Skip
	@OneToMany(mappedBy = "variable1")
	private List<Skip> skips1;

	// bi-directional many-to-one association to Skip
	@OneToMany(mappedBy = "variable2")
	private List<Skip> skips2;

	// bi-directional many-to-one association to Instance
	@ManyToOne
	@JoinColumn(name = "instance_id")
	private Instance instance;

	public Variable() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOperatorInstructions() {
		return this.operatorInstructions;
	}

	public void setOperatorInstructions(String operatorInstructions) {
		this.operatorInstructions = operatorInstructions;
	}

	public Integer getOrdering() {
		return this.ordering;
	}

	public void setOrdering(Integer order) {
		this.ordering = order;
	}

	public Integer getResponseType() {
		return this.responseType;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Concept> getConcepts() {
		return this.concepts;
	}

	public void setConcepts(List<Concept> concepts) {
		this.concepts = concepts;
	}

	public EditedVariable getEditedVariable() {
		return this.editedVariable;
	}

	public void setEditedVariable(EditedVariable editedVariable) {
		this.editedVariable = editedVariable;
	}

	public InstanceVarGroup getInstanceVarGroup() {
		return this.instanceVarGroup;
	}

	public void setInstanceVarGroup(InstanceVarGroup instanceVarGroup) {
		this.instanceVarGroup = instanceVarGroup;
	}

	public SelectionVariable getSelectionVariable() {
		return this.selectionVariable;
	}

	public void setSelectionVariable(SelectionVariable selectionVariable) {
		this.selectionVariable = selectionVariable;
	}

	public List<Skip> getSkips1() {
		return this.skips1;
	}

	public void setSkips1(List<Skip> skips1) {
		this.skips1 = skips1;
	}

	public List<Skip> getSkips2() {
		return this.skips2;
	}

	public void setSkips2(List<Skip> skips2) {
		this.skips2 = skips2;
	}

	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

}