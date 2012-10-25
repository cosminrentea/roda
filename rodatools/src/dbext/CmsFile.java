package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the cms_files database table.
 * 
 */
@Entity
@Table(name = "cms_files")
public class CmsFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 200)
	private String filename;

	@Column(nullable = false)
	private Integer filesize;

	@Column(nullable = false, length = 50)
	private String label;

	@Column(nullable = false, length = 32)
	private String md5;

	@Column(nullable = false, length = 50)
	private String mimegroup;

	@Column(nullable = false, length = 50)
	private String mimesubgroup;

	// bi-directional many-to-one association to CmsFolder
	@ManyToOne
	@JoinColumn(name = "folder_id", nullable = false)
	private CmsFolder cmsFolder;

	public CmsFile() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getMimegroup() {
		return this.mimegroup;
	}

	public void setMimegroup(String mimegroup) {
		this.mimegroup = mimegroup;
	}

	public String getMimesubgroup() {
		return this.mimesubgroup;
	}

	public void setMimesubgroup(String mimesubgroup) {
		this.mimesubgroup = mimesubgroup;
	}

	public CmsFolder getCmsFolder() {
		return this.cmsFolder;
	}

	public void setCmsFolder(CmsFolder cmsFolder) {
		this.cmsFolder = cmsFolder;
	}

}