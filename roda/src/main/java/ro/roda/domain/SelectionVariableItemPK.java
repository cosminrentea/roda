package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

@Configurable
@Embeddable
public final class SelectionVariableItemPK implements Serializable {

	@Column(name = "variable_id", columnDefinition = "int8", nullable = false)
	private Long variableId;

	@Column(name = "item_id", columnDefinition = "int8", nullable = false)
	private Long itemId;

	public SelectionVariableItemPK(Long variableId, Long itemId) {
		super();
		this.variableId = variableId;
		this.itemId = itemId;
	}

	private SelectionVariableItemPK() {
		super();
	}

	public Long getVariableId() {
		return variableId;
	}

	public Long getItemId() {
		return itemId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static SelectionVariableItemPK fromJsonToSelectionVariableItemPK(String json) {
		return new JSONDeserializer<SelectionVariableItemPK>().use(null, SelectionVariableItemPK.class).deserialize(
				json);
	}

	public static String toJsonArray(Collection<SelectionVariableItemPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<SelectionVariableItemPK> fromJsonArrayToSelectionVariableItemPKs(String json) {
		return new JSONDeserializer<List<SelectionVariableItemPK>>().use(null, ArrayList.class)
				.use("values", SelectionVariableItemPK.class).deserialize(json);
	}

	private static final long serialVersionUID = 1L;
}
