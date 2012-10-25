package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the person_org database table.
 * 
 */
@Embeddable
public class PersonOrgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="org_id", unique=true, nullable=false)
	private Integer orgId;

	@Column(name="person_id", unique=true, nullable=false)
	private Integer personId;

    public PersonOrgPK() {
    }
	public Integer getOrgId() {
		return this.orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getPersonId() {
		return this.personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonOrgPK)) {
			return false;
		}
		PersonOrgPK castOther = (PersonOrgPK)other;
		return 
			this.orgId.equals(castOther.orgId)
			&& this.personId.equals(castOther.personId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orgId.hashCode();
		hash = hash * prime + this.personId.hashCode();
		
		return hash;
    }
}