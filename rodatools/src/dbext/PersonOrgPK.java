package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the person_org database table.
 * 
 */
@Embeddable
public class PersonOrgPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "person_id", unique = true, nullable = false)
	private Integer personId;

	@Column(name = "org_id", unique = true, nullable = false)
	private Integer orgId;

	public PersonOrgPK() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonOrgPK)) {
			return false;
		}
		PersonOrgPK castOther = (PersonOrgPK) other;
		return this.personId.equals(castOther.personId)
				&& this.orgId.equals(castOther.orgId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.personId.hashCode();
		hash = hash * prime + this.orgId.hashCode();

		return hash;
	}
}