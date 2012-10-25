package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the selection_variable_card database table.
 * 
 */
@Entity
@Table(name="selection_variable_card")
public class SelectionVariableCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SelectionVariableCardPK id;

	//bi-directional many-to-one association to InstanceDocument
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="instance_id", referencedColumnName="document_id", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="response_card", referencedColumnName="instance_id", nullable=false, insertable=false, updatable=false)
		})
	private InstanceDocument instanceDocument;

    public SelectionVariableCard() {
    }

	public SelectionVariableCardPK getId() {
		return this.id;
	}

	public void setId(SelectionVariableCardPK id) {
		this.id = id;
	}
	
	public InstanceDocument getInstanceDocument() {
		return this.instanceDocument;
	}

	public void setInstanceDocument(InstanceDocument instanceDocument) {
		this.instanceDocument = instanceDocument;
	}
	
}