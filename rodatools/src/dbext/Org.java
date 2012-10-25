package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the org database table.
 * 
 */
@Entity
@Table(name="org")
public class Org implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=100)
	private String fullname;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="org")
	private List<Email> emails;

	//bi-directional many-to-one association to InstanceOrg
	@OneToMany(mappedBy="org")
	private List<InstanceOrg> instanceOrgs;

	//bi-directional many-to-one association to Internet
	@OneToMany(mappedBy="org")
	private List<Internet> internets;

	//bi-directional many-to-one association to OrgPrefix
    @ManyToOne
	@JoinColumn(name="org_prefix_id", nullable=false)
	private OrgPrefix orgPrefix;

	//bi-directional many-to-one association to OrgSufix
    @ManyToOne
	@JoinColumn(name="org_sufix_id", nullable=false)
	private OrgSufix orgSufix;

	//bi-directional many-to-one association to OrgAddress
	@OneToMany(mappedBy="org")
	private List<OrgAddress> orgAddresses;

	//bi-directional many-to-one association to OrgRelation
	@OneToMany(mappedBy="org1")
	private List<OrgRelation> orgRelations1;

	//bi-directional many-to-one association to OrgRelation
	@OneToMany(mappedBy="org2")
	private List<OrgRelation> orgRelations2;

	//bi-directional many-to-one association to PersonOrg
	@OneToMany(mappedBy="org")
	private List<PersonOrg> personOrgs;

	//bi-directional many-to-one association to StudyOrg
	@OneToMany(mappedBy="org")
	private List<StudyOrg> studyOrgs;

    public Org() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	
	public List<InstanceOrg> getInstanceOrgs() {
		return this.instanceOrgs;
	}

	public void setInstanceOrgs(List<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
	}
	
	public List<Internet> getInternets() {
		return this.internets;
	}

	public void setInternets(List<Internet> internets) {
		this.internets = internets;
	}
	
	public OrgPrefix getOrgPrefix() {
		return this.orgPrefix;
	}

	public void setOrgPrefix(OrgPrefix orgPrefix) {
		this.orgPrefix = orgPrefix;
	}
	
	public OrgSufix getOrgSufix() {
		return this.orgSufix;
	}

	public void setOrgSufix(OrgSufix orgSufix) {
		this.orgSufix = orgSufix;
	}
	
	public List<OrgAddress> getOrgAddresses() {
		return this.orgAddresses;
	}

	public void setOrgAddresses(List<OrgAddress> orgAddresses) {
		this.orgAddresses = orgAddresses;
	}
	
	public List<OrgRelation> getOrgRelations1() {
		return this.orgRelations1;
	}

	public void setOrgRelations1(List<OrgRelation> orgRelations1) {
		this.orgRelations1 = orgRelations1;
	}
	
	public List<OrgRelation> getOrgRelations2() {
		return this.orgRelations2;
	}

	public void setOrgRelations2(List<OrgRelation> orgRelations2) {
		this.orgRelations2 = orgRelations2;
	}
	
	public List<PersonOrg> getPersonOrgs() {
		return this.personOrgs;
	}

	public void setPersonOrgs(List<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
	}
	
	public List<StudyOrg> getStudyOrgs() {
		return this.studyOrgs;
	}

	public void setStudyOrgs(List<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
	}
	
}