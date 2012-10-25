package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the skip database table.
 * 
 */
@Entity
@Table(name="skip")
public class Skip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=255)
	private String condition;

	@Column(name="instance_id")
	private Integer instanceId;

	@Column(name="next_variable_id", nullable=false)
	private Integer nextVariableId;

	@Column(name="variable_id", nullable=false)
	private Integer variableId;

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

	public Integer getNextVariableId() {
		return this.nextVariableId;
	}

	public void setNextVariableId(Integer nextVariableId) {
		this.nextVariableId = nextVariableId;
	}

	public Integer getVariableId() {
		return this.variableId;
	}

	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

}