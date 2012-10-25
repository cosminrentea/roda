package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the instance_documents database table.
 * 
 */
@Entity
@Table(name = "instance_documents")
public class InstanceDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InstanceDocumentPK id;

	// bi-directional many-to-many association to SelectionVariableItem
	@ManyToMany(mappedBy = "instanceDocuments")
	private List<SelectionVariableItem> selectionVariableItems;

	public InstanceDocument() {
	}

	public InstanceDocumentPK getId() {
		return this.id;
	}

	public void setId(InstanceDocumentPK id) {
		this.id = id;
	}

	public List<SelectionVariableItem> getSelectionVariableItems() {
		return this.selectionVariableItems;
	}

	public void setSelectionVariableItems(
			List<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

}