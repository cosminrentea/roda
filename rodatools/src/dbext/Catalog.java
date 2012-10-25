package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the catalog database table.
 * 
 */
@Entity
@Table(name="catalog")
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Timestamp added;

	@Column(nullable=false, length=150)
	private String name;

	@Column(nullable=false)
	private Integer owner;

	@Column(nullable=false)
	private Integer parent;

	//bi-directional many-to-one association to CatalogAcl
	@OneToMany(mappedBy="catalog")
	private List<CatalogAcl> catalogAcls;

	//bi-directional many-to-one association to CatalogStudy
	@OneToMany(mappedBy="catalog")
	private List<CatalogStudy> catalogStudies;

    public Catalog() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getAdded() {
		return this.added;
	}

	public void setAdded(Timestamp added) {
		this.added = added;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public List<CatalogAcl> getCatalogAcls() {
		return this.catalogAcls;
	}

	public void setCatalogAcls(List<CatalogAcl> catalogAcls) {
		this.catalogAcls = catalogAcls;
	}
	
	public List<CatalogStudy> getCatalogStudies() {
		return this.catalogStudies;
	}

	public void setCatalogStudies(List<CatalogStudy> catalogStudies) {
		this.catalogStudies = catalogStudies;
	}
	
}