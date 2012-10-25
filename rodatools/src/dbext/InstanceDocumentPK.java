package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the instance_documents database table.
 * 
 */
@Embeddable
public class InstanceDocumentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="instance_id", unique=true, nullable=false)
	private Integer instanceId;

	@Column(name="document_id", unique=true, nullable=false)
	private Integer documentId;

    public InstanceDocumentPK() {
    }
	public Integer getInstanceId() {
		return this.instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getDocumentId() {
		return this.documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InstanceDocumentPK)) {
			return false;
		}
		InstanceDocumentPK castOther = (InstanceDocumentPK)other;
		return 
			this.instanceId.equals(castOther.instanceId)
			&& this.documentId.equals(castOther.documentId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.instanceId.hashCode();
		hash = hash * prime + this.documentId.hashCode();
		
		return hash;
    }
}