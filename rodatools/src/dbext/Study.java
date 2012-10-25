package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the study database table.
 * 
 */
@Entity
@Table(name="study")
public class Study implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp dateend;

	@Column(nullable=false)
	private Timestamp datestart;

	//bi-directional many-to-one association to CatalogStudy
	@OneToMany(mappedBy="study")
	private List<CatalogStudy> catalogStudies;

	//bi-directional many-to-one association to Instance
	@OneToMany(mappedBy="study")
	private List<Instance> instances;

	//bi-directional many-to-one association to StudyAcl
	@OneToMany(mappedBy="study")
	private List<StudyAcl> studyAcls;

	//bi-directional many-to-one association to StudyDescr
	@OneToMany(mappedBy="study")
	private List<StudyDescr> studyDescrs;

	//bi-directional many-to-many association to Document
    @ManyToMany
	@JoinTable(
		name="study_documents"
		, joinColumns={
			@JoinColumn(name="study_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="document_id", nullable=false)
			}
		)
	private List<Document> documents;

	//bi-directional many-to-one association to StudyOrg
	@OneToMany(mappedBy="study")
	private List<StudyOrg> studyOrgs;

	//bi-directional many-to-one association to StudyPerson
	@OneToMany(mappedBy="study")
	private List<StudyPerson> studyPersons;

    public Study() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public List<CatalogStudy> getCatalogStudies() {
		return this.catalogStudies;
	}

	public void setCatalogStudies(List<CatalogStudy> catalogStudies) {
		this.catalogStudies = catalogStudies;
	}
	
	public List<Instance> getInstances() {
		return this.instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	
	public List<StudyAcl> getStudyAcls() {
		return this.studyAcls;
	}

	public void setStudyAcls(List<StudyAcl> studyAcls) {
		this.studyAcls = studyAcls;
	}
	
	public List<StudyDescr> getStudyDescrs() {
		return this.studyDescrs;
	}

	public void setStudyDescrs(List<StudyDescr> studyDescrs) {
		this.studyDescrs = studyDescrs;
	}
	
	public List<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public List<StudyOrg> getStudyOrgs() {
		return this.studyOrgs;
	}

	public void setStudyOrgs(List<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
	}
	
	public List<StudyPerson> getStudyPersons() {
		return this.studyPersons;
	}

	public void setStudyPersons(List<StudyPerson> studyPersons) {
		this.studyPersons = studyPersons;
	}
	
}