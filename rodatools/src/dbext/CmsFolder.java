package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cms_folders database table.
 * 
 */
@Entity
@Table(name="cms_folders")
public class CmsFolder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=255)
	private String description;

	@Column(nullable=false, length=150)
	private String name;

	@Column(nullable=false)
	private Integer parent;

	//bi-directional many-to-one association to CmsFile
	@OneToMany(mappedBy="cmsFolder")
	private List<CmsFile> cmsFiles;

    public CmsFolder() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public List<CmsFile> getCmsFiles() {
		return this.cmsFiles;
	}

	public void setCmsFiles(List<CmsFile> cmsFiles) {
		this.cmsFiles = cmsFiles;
	}
	
}