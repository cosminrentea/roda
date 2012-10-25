package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the edited_variable database table.
 * 
 */
@Entity
@Table(name="edited_variable")
public class EditedVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="variable_id", unique=true, nullable=false)
	private Integer variableId;

	@Column(name="instance_id", nullable=false)
	private Integer instanceId;

	@Column(nullable=false)
	private Integer type;

	//bi-directional many-to-one association to FormEditedNumberVar
	@OneToMany(mappedBy="editedVariable")
	private List<FormEditedNumberVar> formEditedNumberVars;

	//bi-directional many-to-one association to FormEditedTextVar
	@OneToMany(mappedBy="editedVariable")
	private List<FormEditedTextVar> formEditedTextVars;

	//bi-directional many-to-one association to OtherStatistic
	@OneToMany(mappedBy="editedVariable")
	private List<OtherStatistic> otherStatistics;

    public EditedVariable() {
    }

	public Integer getVariableId() {
		return this.variableId;
	}

	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

	public Integer getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<FormEditedNumberVar> getFormEditedNumberVars() {
		return this.formEditedNumberVars;
	}

	public void setFormEditedNumberVars(List<FormEditedNumberVar> formEditedNumberVars) {
		this.formEditedNumberVars = formEditedNumberVars;
	}
	
	public List<FormEditedTextVar> getFormEditedTextVars() {
		return this.formEditedTextVars;
	}

	public void setFormEditedTextVars(List<FormEditedTextVar> formEditedTextVars) {
		this.formEditedTextVars = formEditedTextVars;
	}
	
	public List<OtherStatistic> getOtherStatistics() {
		return this.otherStatistics;
	}

	public void setOtherStatistics(List<OtherStatistic> otherStatistics) {
		this.otherStatistics = otherStatistics;
	}
	
}