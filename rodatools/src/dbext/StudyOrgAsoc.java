package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the study_org_asoc database table.
 * 
 */
@Entity
@Table(name="study_org_asoc")
public class StudyOrgAsoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="asoc_description", nullable=false)
	private Long asocDescription;

	@Column(name="asoc_name", nullable=false, length=100)
	private String asocName;

	@Column(name="assoc_details", nullable=false, length=255)
	private String assocDetails;

	//bi-directional many-to-one association to StudyOrg
	@OneToMany(mappedBy="studyOrgAsoc")
	private List<StudyOrg> studyOrgs;

    public StudyOrgAsoc() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAsocDescription() {
		return this.asocDescription;
	}

	public void setAsocDescription(Long asocDescription) {
		this.asocDescription = asocDescription;
	}

	public String getAsocName() {
		return this.asocName;
	}

	public void setAsocName(String asocName) {
		this.asocName = asocName;
	}

	public String getAssocDetails() {
		return this.assocDetails;
	}

	public void setAssocDetails(String assocDetails) {
		this.assocDetails = assocDetails;
	}

	public List<StudyOrg> getStudyOrgs() {
		return this.studyOrgs;
	}

	public void setStudyOrgs(List<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
	}
	
}