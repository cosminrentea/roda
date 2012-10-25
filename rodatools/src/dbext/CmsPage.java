package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the cms_page database table.
 * 
 */
@Entity
@Table(name = "cms_page")
public class CmsPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false)
	private Boolean navigable;

	@Column(nullable = false)
	private Integer owner;

	@Column(name = "page_type", nullable = false)
	private Integer pageType;

	@Column(nullable = false, length = 200)
	private String url;

	@Column(nullable = false)
	private Boolean visible;

	// bi-directional many-to-one association to CmsLayout
	@ManyToOne
	@JoinColumn(name = "layout", nullable = false)
	private CmsLayout cmsLayout;

	// bi-directional many-to-one association to CmsPageContent
	@OneToMany(mappedBy = "cmsPage")
	private List<CmsPageContent> cmsPageContents;

	public CmsPage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNavigable() {
		return this.navigable;
	}

	public void setNavigable(Boolean navigable) {
		this.navigable = navigable;
	}

	public Integer getOwner() {
		return this.owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getPageType() {
		return this.pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public CmsLayout getCmsLayout() {
		return this.cmsLayout;
	}

	public void setCmsLayout(CmsLayout cmsLayout) {
		this.cmsLayout = cmsLayout;
	}

	public List<CmsPageContent> getCmsPageContents() {
		return this.cmsPageContents;
	}

	public void setCmsPageContents(List<CmsPageContent> cmsPageContents) {
		this.cmsPageContents = cmsPageContents;
	}

}