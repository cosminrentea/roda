package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the selection_variable_item database table.
 * 
 */
@Embeddable
public class SelectionVariableItemPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "selection_variable_id", unique = true, nullable = false)
	private Integer selectionVariableId;

	@Column(name = "item_id", unique = true, nullable = false)
	private Integer itemId;

	public SelectionVariableItemPK() {
	}

	public Integer getSelectionVariableId() {
		return this.selectionVariableId;
	}

	public void setSelectionVariableId(Integer selectionVariableId) {
		this.selectionVariableId = selectionVariableId;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SelectionVariableItemPK)) {
			return false;
		}
		SelectionVariableItemPK castOther = (SelectionVariableItemPK) other;
		return this.selectionVariableId.equals(castOther.selectionVariableId)
				&& this.itemId.equals(castOther.itemId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.selectionVariableId.hashCode();
		hash = hash * prime + this.itemId.hashCode();

		return hash;
	}
}