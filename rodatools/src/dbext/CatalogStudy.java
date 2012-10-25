package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catalog_study database table.
 * 
 */
@Entity
@Table(name="catalog_study")
public class CatalogStudy implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CatalogStudyPK id;

	@Column(name="added_by", nullable=false)
	private Integer addedBy;

	//bi-directional many-to-one association to Catalog
    @ManyToOne
	@JoinColumn(name="catalog_id", nullable=false, insertable=false, updatable=false)
	private Catalog catalog;

	//bi-directional many-to-one association to Study
    @ManyToOne
	@JoinColumn(name="study_id", nullable=false, insertable=false, updatable=false)
	private Study study;

    public CatalogStudy() {
    }

	public CatalogStudyPK getId() {
		return this.id;
	}

	public void setId(CatalogStudyPK id) {
		this.id = id;
	}
	
	public Integer getAddedBy() {
		return this.addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}
	
}