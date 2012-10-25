package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the studiu_person_asoc database table.
 * 
 */
@Entity
@Table(name = "studiu_person_asoc")
public class StudiuPersonAsoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "asoc_description", nullable = false)
	private String asocDescription;

	@Column(name = "asoc_name", nullable = false, length = 100)
	private String asocName;

	// bi-directional many-to-one association to StudyPerson
	@OneToMany(mappedBy = "studiuPersonAsoc")
	private List<StudyPerson> studyPersons;

	public StudiuPersonAsoc() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsocDescription() {
		return this.asocDescription;
	}

	public void setAsocDescription(String asocDescription) {
		this.asocDescription = asocDescription;
	}

	public String getAsocName() {
		return this.asocName;
	}

	public void setAsocName(String asocName) {
		this.asocName = asocName;
	}

	public List<StudyPerson> getStudyPersons() {
		return this.studyPersons;
	}

	public void setStudyPersons(List<StudyPerson> studyPersons) {
		this.studyPersons = studyPersons;
	}

}