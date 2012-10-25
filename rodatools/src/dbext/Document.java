package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the documents database table.
 * 
 */
@Entity
@Table(name = "documents")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 200)
	private String filename;

	@Column(nullable = false)
	private Integer filesize;

	@Column(nullable = false, length = 50)
	private String mimetype;

	@Column(nullable = false, length = 250)
	private String title;

	// bi-directional many-to-one association to DocumentType
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false)
	private DocumentType documentType;

	// bi-directional many-to-one association to DocumentsAcl
	@OneToMany(mappedBy = "document")
	private List<DocumentsAcl> documentsAcls;

	// bi-directional many-to-many association to Instance
	@ManyToMany(mappedBy = "documents")
	private List<Instance> instances;

	// bi-directional many-to-many association to Study
	@ManyToMany(mappedBy = "documents")
	private List<Study> studies;

	public Document() {
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

	public String getMimetype() {
		return this.mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public List<DocumentsAcl> getDocumentsAcls() {
		return this.documentsAcls;
	}

	public void setDocumentsAcls(List<DocumentsAcl> documentsAcls) {
		this.documentsAcls = documentsAcls;
	}

	public List<Instance> getInstances() {
		return this.instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public List<Study> getStudies() {
		return this.studies;
	}

	public void setStudies(List<Study> studies) {
		this.studies = studies;
	}

}