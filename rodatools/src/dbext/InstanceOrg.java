package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the instance_org database table.
 * 
 */
@Entity
@Table(name = "instance_org")
public class InstanceOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InstanceOrgPK id;

	@Column(name = "assoc_details", nullable = false)
	private String assocDetails;

	// bi-directional many-to-one association to Instance
	@ManyToOne
	@JoinColumn(name = "instance_id", nullable = false, insertable = false, updatable = false)
	private Instance instance;

	// bi-directional many-to-one association to InstanceOrgAssoc
	@ManyToOne
	@JoinColumn(name = "assoc_type_id", nullable = false)
	private InstanceOrgAssoc instanceOrgAssoc;

	// bi-directional many-to-one association to Org
	@ManyToOne
	@JoinColumn(name = "org_id", nullable = false, insertable = false, updatable = false)
	private Org org;

	public InstanceOrg() {
	}

	public InstanceOrgPK getId() {
		return this.id;
	}

	public void setId(InstanceOrgPK id) {
		this.id = id;
	}

	public String getAssocDetails() {
		return this.assocDetails;
	}

	public void setAssocDetails(String assocDetails) {
		this.assocDetails = assocDetails;
	}

	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public InstanceOrgAssoc getInstanceOrgAssoc() {
		return this.instanceOrgAssoc;
	}

	public void setInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc) {
		this.instanceOrgAssoc = instanceOrgAssoc;
	}

	public Org getOrg() {
		return this.org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

}