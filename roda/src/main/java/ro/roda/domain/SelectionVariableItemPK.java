package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
}
