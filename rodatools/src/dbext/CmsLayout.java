package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cms_layout database table.
 * 
 */
@Entity
@Table(name="cms_layout")
public class CmsLayout implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="layout_content", nullable=false, length=255)
	private String layoutContent;

	@Column(nullable=false, length=150)
	private String name;

	//bi-directional many-to-one association to CmsLayoutGroup
    @ManyToOne
	@JoinColumn(name="layout_group", nullable=false)
	private CmsLayoutGroup cmsLayoutGroup;

	//bi-directional many-to-one association to CmsPage
	@OneToMany(mappedBy="cmsLayout")
	private List<CmsPage> cmsPages;

    public CmsLayout() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLayoutContent() {
		return this.layoutContent;
	}

	public void setLayoutContent(String layoutContent) {
		this.layoutContent = layoutContent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CmsLayoutGroup getCmsLayoutGroup() {
		return this.cmsLayoutGroup;
	}

	public void setCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup) {
		this.cmsLayoutGroup = cmsLayoutGroup;
	}
	
	public List<CmsPage> getCmsPages() {
		return this.cmsPages;
	}

	public void setCmsPages(List<CmsPage> cmsPages) {
		this.cmsPages = cmsPages;
	}
	
}