package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the study_acl database table.
 * 
 */
@Entity
@Table(name = "study_acl")
public class StudyAcl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "aro_id", unique = true, nullable = false)
	private Integer aroId;

	@Column(name = "aro_type", nullable = false)
	private Integer aroType;

	private Boolean delete;

	private Boolean modacl;

	private Boolean read;

	private Boolean update;

	// bi-directional many-to-one association to Study
	@ManyToOne
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	public StudyAcl() {
	}

	public Integer getAroId() {
		return this.aroId;
	}

	public void setAroId(Integer aroId) {
		this.aroId = aroId;
	}

	public Integer getAroType() {
		return this.aroType;
	}

	public void setAroType(Integer aroType) {
		this.aroType = aroType;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getModacl() {
		return this.modacl;
	}

	public void setModacl(Boolean modacl) {
		this.modacl = modacl;
	}

	public Boolean getRead() {
		return this.read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getUpdate() {
		return this.update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

}