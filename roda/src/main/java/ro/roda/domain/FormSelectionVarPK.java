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

@Embeddable
@Configurable

public final class FormSelectionVarPK implements Serializable {

	@Column(name = "form_id", columnDefinition = "int8", nullable = false)
    private Long formId;

	@Column(name = "variable_id", columnDefinition = "int8", nullable = false)
    private Long variableId;

	@Column(name = "item_id", columnDefinition = "int8", nullable = false)
    private Long itemId;

	public FormSelectionVarPK(Long formId, Long variableId, Long itemId) {
        super();
        this.formId = formId;
        this.variableId = variableId;
        this.itemId = itemId;
    }

	private FormSelectionVarPK() {
        super();
    }

	public Long getFormId() {
        return formId;
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

	public static FormSelectionVarPK fromJsonToFormSelectionVarPK(String json) {
        return new JSONDeserializer<FormSelectionVarPK>().use(null, FormSelectionVarPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<FormSelectionVarPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<FormSelectionVarPK> fromJsonArrayToFormSelectionVarPKs(String json) {
        return new JSONDeserializer<List<FormSelectionVarPK>>().use(null, ArrayList.class).use("values", FormSelectionVarPK.class).deserialize(json);
    }

	private static final long serialVersionUID = 1L;
}
