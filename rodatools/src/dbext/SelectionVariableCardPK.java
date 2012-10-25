package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the selection_variable_card database table.
 * 
 */
@Embeddable
public class SelectionVariableCardPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="item_id", unique=true, nullable=false)
	private Integer itemId;

	@Column(name="variable_id", unique=true, nullable=false)
	private Integer variableId;

	@Column(name="instance_id", unique=true, nullable=false)
	private Integer instanceId;

	@Column(name="response_card", unique=true, nullable=false)
	private Integer responseCard;

    public SelectionVariableCardPK() {
    }
	public Integer getItemId() {
		return this.itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getVariableId() {
		return this.variableId;
	}
	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}
	public Integer getInstanceId() {
		return this.instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getResponseCard() {
		return this.responseCard;
	}
	public void setResponseCard(Integer responseCard) {
		this.responseCard = responseCard;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SelectionVariableCardPK)) {
			return false;
		}
		SelectionVariableCardPK castOther = (SelectionVariableCardPK)other;
		return 
			this.itemId.equals(castOther.itemId)
			&& this.variableId.equals(castOther.variableId)
			&& this.instanceId.equals(castOther.instanceId)
			&& this.responseCard.equals(castOther.responseCard);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.itemId.hashCode();
		hash = hash * prime + this.variableId.hashCode();
		hash = hash * prime + this.instanceId.hashCode();
		hash = hash * prime + this.responseCard.hashCode();
		
		return hash;
    }
}