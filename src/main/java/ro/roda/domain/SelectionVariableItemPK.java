package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Embeddable
public final class SelectionVariableItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<SelectionVariableItemPK> fromJsonArrayToSelectionVariableItemPKs(String json) {
		return new JSONDeserializer<List<SelectionVariableItemPK>>().use(null, ArrayList.class)
				.use("values", SelectionVariableItemPK.class).deserialize(json);
	}

	public static SelectionVariableItemPK fromJsonToSelectionVariableItemPK(String json) {
		return new JSONDeserializer<SelectionVariableItemPK>().use(null, SelectionVariableItemPK.class).deserialize(
				json);
	}

	public static String toJsonArray(Collection<SelectionVariableItemPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "item_id", columnDefinition = "int8", nullable = false)
	private Long itemId;

	@Column(name = "variable_id", columnDefinition = "int8", nullable = false)
	private Long variableId;

	public SelectionVariableItemPK(Long variableId, Long itemId) {
		super();
		this.variableId = variableId;
		this.itemId = itemId;
	}

	private SelectionVariableItemPK() {
		super();
	}

	public Long getItemId() {
		return itemId;
	}

	public Long getVariableId() {
		return variableId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof SelectionVariableItemPK) {
			final SelectionVariableItemPK other = (SelectionVariableItemPK) obj;
			return new EqualsBuilder().append(variableId, other.variableId).append(itemId, other.itemId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(variableId).append(itemId).toHashCode();
	}
}
