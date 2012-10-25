package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the study_person database table.
 * 
 */
@Entity
@Table(name = "study_person")
public class StudyPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	// bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	// bi-directional many-to-one association to StudiuPersonAsoc
	@ManyToOne
	@JoinColumn(name = "asoctype_id", nullable = false)
	private StudiuPersonAsoc studiuPersonAsoc;

	// bi-directional many-to-one association to Study
	@ManyToOne
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	// bi-directional many-to-one association to StudyPersonAcl
	@OneToMany(mappedBy = "studyPerson")
	private List<StudyPersonAcl> studyPersonAcls;

	public StudyPerson() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public StudiuPersonAsoc getStudiuPersonAsoc() {
		return this.studiuPersonAsoc;
	}

	public void setStudiuPersonAsoc(StudiuPersonAsoc studiuPersonAsoc) {
		this.studiuPersonAsoc = studiuPersonAsoc;
	}

	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public List<StudyPersonAcl> getStudyPersonAcls() {
		return this.studyPersonAcls;
	}

	public void setStudyPersonAcls(List<StudyPersonAcl> studyPersonAcls) {
		this.studyPersonAcls = studyPersonAcls;
	}

}