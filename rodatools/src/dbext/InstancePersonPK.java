package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the instance_person database table.
 * 
 */
@Embeddable
public class InstancePersonPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "person_id", unique = true, nullable = false)
	private Integer personId;

	@Column(name = "instance_id", unique = true, nullable = false)
	private Integer instanceId;

	@Column(name = "assoc_type_id", unique = true, nullable = false)
	private Integer assocTypeId;

	public InstancePersonPK() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public Integer getAssocTypeId() {
		return this.assocTypeId;
	}

	public void setAssocTypeId(Integer assocTypeId) {
		this.assocTypeId = assocTypeId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InstancePersonPK)) {
			return false;
		}
		InstancePersonPK castOther = (InstancePersonPK) other;
		return this.personId.equals(castOther.personId)
				&& this.instanceId.equals(castOther.instanceId)
				&& this.assocTypeId.equals(castOther.assocTypeId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.personId.hashCode();
		hash = hash * prime + this.instanceId.hashCode();
		hash = hash * prime + this.assocTypeId.hashCode();

		return hash;
	}
}