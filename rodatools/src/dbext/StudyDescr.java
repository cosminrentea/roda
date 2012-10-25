package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the study_descr database table.
 * 
 */
@Entity
@Table(name = "study_descr")
public class StudyDescr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "abstract", nullable = false)
	private String abstract_;

	@Column(nullable = false, length = 250)
	private String title;

	// bi-directional many-to-one association to Study
	@ManyToOne
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	public StudyDescr() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

}