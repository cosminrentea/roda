package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the org_relations database table.
 * 
 */
@Entity
@Table(name="org_relations")
public class OrgRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrgRelationPK id;

	@Column(nullable=false)
	private Timestamp dateend;

	@Column(nullable=false)
	private Timestamp datestart;

	@Column(nullable=false, length=255)
	private String details;

	//bi-directional many-to-one association to Org
    @ManyToOne
	@JoinColumn(name="org_1_id", nullable=false, insertable=false, updatable=false)
	private Org org1;

	//bi-directional many-to-one association to Org
    @ManyToOne
	@JoinColumn(name="org_2_id", nullable=false, insertable=false, updatable=false)
	private Org org2;

	//bi-directional many-to-one association to OrgRelationType
    @ManyToOne
	@JoinColumn(name="org_relation_type_id", nullable=false)
	private OrgRelationType orgRelationType;

    public OrgRelation() {
    }

	public OrgRelationPK getId() {
		return this.id;
	}

	public void setId(OrgRelationPK id) {
		this.id = id;
	}
	
	public Timestamp getDateend() {
		return this.dateend;
	}

	public void setDateend(Timestamp dateend) {
		this.dateend = dateend;
	}

	public Timestamp getDatestart() {
		return this.datestart;
	}

	public void setDatestart(Timestamp datestart) {
		this.datestart = datestart;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Org getOrg1() {
		return this.org1;
	}

	public void setOrg1(Org org1) {
		this.org1 = org1;
	}
	
	public Org getOrg2() {
		return this.org2;
	}

	public void setOrg2(Org org2) {
		this.org2 = org2;
	}
	
	public OrgRelationType getOrgRelationType() {
		return this.orgRelationType;
	}

	public void setOrgRelationType(OrgRelationType orgRelationType) {
		this.orgRelationType = orgRelationType;
	}
	
}