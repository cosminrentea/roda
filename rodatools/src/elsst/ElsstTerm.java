package elsst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class ElsstTerm {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@XmlTransient
	private long id_;

	public long getId_() {
		return id_;
	}

	public void setId_(long id_) {
		this.id_ = id_;
	}

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "ElsstTerm_Translated")
	private Map<ElsstLanguage, ElsstTerm> translatedTerm;

	@Column(columnDefinition = "text")
	private String name;

	@Column(columnDefinition = "text")
	private String scopeNote;

	@OneToOne(cascade = { CascadeType.ALL })
	private ElsstTerm preferredTerm;

	@OneToOne(cascade = { CascadeType.ALL })
	private ElsstTerm broaderTerm;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "ElsstTerm_Related")
	private List<ElsstTerm> relatedTerm;

	public ElsstTerm getPreferredTerm() {
		return preferredTerm;
	}

	public void setPreferredTerm(ElsstTerm preferredTerm) {
		this.preferredTerm = preferredTerm;
	}

	public ElsstTerm getBroaderTerm() {
		return broaderTerm;
	}

	public void setBroaderTerm(ElsstTerm broaderTerm) {
		this.broaderTerm = broaderTerm;
	}

	public List<ElsstTerm> getRelatedTerm() {
		if (relatedTerm == null) {
			relatedTerm = new ArrayList<ElsstTerm>();
		}
		return relatedTerm;
	}

	public void setRelatedTerm(List<ElsstTerm> relatedTerms) {
		this.relatedTerm = relatedTerms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScopeNote() {
		return scopeNote;
	}

	public void setScopeNote(String scopeNote) {
		this.scopeNote = scopeNote;
	}

	public Map<ElsstLanguage, ElsstTerm> getTranslatedTerm() {
		return translatedTerm;
	}

	public void setTranslatedTerm(Map<ElsstLanguage, ElsstTerm> translatedTerm) {
		this.translatedTerm = translatedTerm;
	}
}
