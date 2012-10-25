package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the documents_acl database table.
 * 
 */
@Entity
@Table(name="documents_acl")
public class DocumentsAcl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="aro_id", unique=true, nullable=false)
	private Integer aroId;

	@Column(name="aro_type", nullable=false)
	private Integer aroType;

	@Column(nullable=false)
	private Boolean delete;

	@Column(nullable=false)
	private Boolean read;

	@Column(nullable=false)
	private Boolean update;

	//bi-directional many-to-one association to Document
    @ManyToOne
	@JoinColumn(name="document_id", nullable=false)
	private Document document;

    public DocumentsAcl() {
    }

	public Integer getAroId() {
		return this.aroId;
	}

	public void setAroId(Integer aroId) {
		this.aroId = aroId;
	}

	public Integer getAroType() {
		return this.aroType;
	}

	public void setAroType(Integer aroType) {
		this.aroType = aroType;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getRead() {
		return this.read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getUpdate() {
		return this.update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}