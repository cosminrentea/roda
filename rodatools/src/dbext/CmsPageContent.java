package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cms_page_content database table.
 * 
 */
@Entity
@Table(name = "cms_page_content")
public class CmsPageContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "content_text")
	private String contentText;

	@Column(name = "content_title", nullable = false, length = 250)
	private String contentTitle;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false)
	private Integer sqnumber;

	// bi-directional many-to-one association to CmsPage
	@ManyToOne
	@JoinColumn(name = "page")
	private CmsPage cmsPage;

	public CmsPageContent() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContentText() {
		return this.contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getContentTitle() {
		return this.contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSqnumber() {
		return this.sqnumber;
	}

	public void setSqnumber(Integer sqnumber) {
		this.sqnumber = sqnumber;
	}

	public CmsPage getCmsPage() {
		return this.cmsPage;
	}

	public void setCmsPage(CmsPage cmsPage) {
		this.cmsPage = cmsPage;
	}

}