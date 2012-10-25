package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the instance_org database table.
 * 
 */
@Embeddable
public class InstanceOrgPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "org_id", unique = true, nullable = false)
	private Integer orgId;

	@Column(name = "instance_id", unique = true, nullable = false)
	private Integer instanceId;

	public InstanceOrgPK() {
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InstanceOrgPK)) {
			return false;
		}
		InstanceOrgPK castOther = (InstanceOrgPK) other;
		return this.orgId.equals(castOther.orgId)
				&& this.instanceId.equals(castOther.instanceId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orgId.hashCode();
		hash = hash * prime + this.instanceId.hashCode();

		return hash;
	}
}