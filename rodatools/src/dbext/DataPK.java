package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "Data" database table.
 * 
 */
@Embeddable
public class DataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="\"InstanceId\"", unique=true, nullable=false)
	private Integer instanceId;

	@Column(name="\"ValueId\"", unique=true, nullable=false)
	private Long valueId;

	@Column(name="\"VariableId\"", unique=true, nullable=false)
	private Integer variableId;

    public DataPK() {
    }
	public Integer getInstanceId() {
		return this.instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}
	public Long getValueId() {
		return this.valueId;
	}
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}
	public Integer getVariableId() {
		return this.variableId;
	}
	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DataPK)) {
			return false;
		}
		DataPK castOther = (DataPK)other;
		return 
			this.instanceId.equals(castOther.instanceId)
			&& this.valueId.equals(castOther.valueId)
			&& this.variableId.equals(castOther.variableId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.instanceId.hashCode();
		hash = hash * prime + this.valueId.hashCode();
		hash = hash * prime + this.variableId.hashCode();
		
		return hash;
    }
}