package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the org_relations database table.
 * 
 */
@Embeddable
public class OrgRelationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="org_1_id", unique=true, nullable=false)
	private Integer org1Id;

	@Column(name="org_2_id", unique=true, nullable=false)
	private Integer org2Id;

    public OrgRelationPK() {
    }
	public Integer getOrg1Id() {
		return this.org1Id;
	}
	public void setOrg1Id(Integer org1Id) {
		this.org1Id = org1Id;
	}
	public Integer getOrg2Id() {
		return this.org2Id;
	}
	public void setOrg2Id(Integer org2Id) {
		this.org2Id = org2Id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrgRelationPK)) {
			return false;
		}
		OrgRelationPK castOther = (OrgRelationPK)other;
		return 
			this.org1Id.equals(castOther.org1Id)
			&& this.org2Id.equals(castOther.org2Id);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.org1Id.hashCode();
		hash = hash * prime + this.org2Id.hashCode();
		
		return hash;
    }
}