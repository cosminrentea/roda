package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the instance_org_assoc database table.
 * 
 */
@Entity
@Table(name = "instance_org_assoc")
public class InstanceOrgAssoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "assoc_description", nullable = false)
	private String assocDescription;

	@Column(name = "assoc_name", nullable = false, length = 100)
	private String assocName;

	// bi-directional many-to-one association to InstanceOrg
	@OneToMany(mappedBy = "instanceOrgAssoc")
	private List<InstanceOrg> instanceOrgs;

	public InstanceOrgAssoc() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssocDescription() {
		return this.assocDescription;
	}

	public void setAssocDescription(String assocDescription) {
		this.assocDescription = assocDescription;
	}

	public String getAssocName() {
		return this.assocName;
	}

	public void setAssocName(String assocName) {
		this.assocName = assocName;
	}

	public List<InstanceOrg> getInstanceOrgs() {
		return this.instanceOrgs;
	}

	public void setInstanceOrgs(List<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
	}

}