package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the study_org database table.
 * 
 */
@Entity
@Table(name = "study_org")
public class StudyOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	// bi-directional many-to-one association to Org
	@ManyToOne
	@JoinColumn(name = "org_id", nullable = false)
	private Org org;

	// bi-directional many-to-one association to Study
	@ManyToOne
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	// bi-directional many-to-one association to StudyOrgAsoc
	@ManyToOne
	@JoinColumn(name = "assoctype_id", nullable = false)
	private StudyOrgAsoc studyOrgAsoc;

	// bi-directional many-to-one association to StudyOrgAcl
	@OneToMany(mappedBy = "studyOrg")
	private List<StudyOrgAcl> studyOrgAcls;

	public StudyOrg() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Org getOrg() {
		return this.org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public StudyOrgAsoc getStudyOrgAsoc() {
		return this.studyOrgAsoc;
	}

	public void setStudyOrgAsoc(StudyOrgAsoc studyOrgAsoc) {
		this.studyOrgAsoc = studyOrgAsoc;
	}

	public List<StudyOrgAcl> getStudyOrgAcls() {
		return this.studyOrgAcls;
	}

	public void setStudyOrgAcls(List<StudyOrgAcl> studyOrgAcls) {
		this.studyOrgAcls = studyOrgAcls;
	}

}