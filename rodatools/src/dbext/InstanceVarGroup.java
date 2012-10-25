package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the instance_var_group database table.
 * 
 */
@Entity
@Table(name="instance_var_group")
public class InstanceVarGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="group_id", nullable=false)
	private Integer groupId;

	@Column(name="variable_id", nullable=false)
	private Integer variableId;

    public InstanceVarGroup() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getVariableId() {
		return this.variableId;
	}

	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

}