package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cms_snippet database table.
 * 
 */
@Entity
@Table(name = "cms_snippet")
public class CmsSnippet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(name = "snippet_content", nullable = false)
	private String snippetContent;

	// bi-directional many-to-one association to CmsSnippetGroup
	@ManyToOne
	@JoinColumn(name = "snippet_group", nullable = false)
	private CmsSnippetGroup cmsSnippetGroup;

	public CmsSnippet() {
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

	public String getSnippetContent() {
		return this.snippetContent;
	}

	public void setSnippetContent(String snippetContent) {
		this.snippetContent = snippetContent;
	}

	public CmsSnippetGroup getCmsSnippetGroup() {
		return this.cmsSnippetGroup;
	}

	public void setCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup) {
		this.cmsSnippetGroup = cmsSnippetGroup;
	}

}