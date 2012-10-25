package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the instance_documents database table.
 * 
 */
@Entity
@Table(name="instance_documents")
public class InstanceDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InstanceDocumentPK id;

	//bi-directional many-to-one association to SelectionVariableCard
	@OneToMany(mappedBy="instanceDocument")
	private List<SelectionVariableCard> selectionVariableCards;

    public InstanceDocument() {
    }

	public InstanceDocumentPK getId() {
		return this.id;
	}

	public void setId(InstanceDocumentPK id) {
		this.id = id;
	}
	
	public List<SelectionVariableCard> getSelectionVariableCards() {
		return this.selectionVariableCards;
	}

	public void setSelectionVariableCards(List<SelectionVariableCard> selectionVariableCards) {
		this.selectionVariableCards = selectionVariableCards;
	}
	
}