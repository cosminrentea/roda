package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the form_edited_number_var database table.
 * 
 */
@Entity
@Table(name = "form_edited_number_var")
public class FormEditedNumberVar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "instance_id")
	private Integer instanceId;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal value;

	// bi-directional many-to-one association to EditedVariable
	@ManyToOne
	@JoinColumn(name = "variable_id", nullable = false)
	private EditedVariable editedVariable;

	public FormEditedNumberVar() {
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

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public EditedVariable getEditedVariable() {
		return this.editedVariable;
	}

	public void setEditedVariable(EditedVariable editedVariable) {
		this.editedVariable = editedVariable;
	}

}