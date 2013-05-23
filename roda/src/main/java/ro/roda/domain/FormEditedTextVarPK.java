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

public final class FormEditedTextVarPK implements Serializable {

	@Column(name = "variable_id", columnDefinition = "int8", nullable = false)
    private Long variableId;

	@Column(name = "form_id", columnDefinition = "int8", nullable = false)
    private Long formId;

	public FormEditedTextVarPK(Long variableId, Long formId) {
        super();
        this.variableId = variableId;
        this.formId = formId;
    }

	private FormEditedTextVarPK() {
        super();
    }

	public Long getVariableId() {
        return variableId;
    }

	public Long getFormId() {
        return formId;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static FormEditedTextVarPK fromJsonToFormEditedTextVarPK(String json) {
        return new JSONDeserializer<FormEditedTextVarPK>().use(null, FormEditedTextVarPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<FormEditedTextVarPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<FormEditedTextVarPK> fromJsonArrayToFormEditedTextVarPKs(String json) {
        return new JSONDeserializer<List<FormEditedTextVarPK>>().use(null, ArrayList.class).use("values", FormEditedTextVarPK.class).deserialize(json);
    }

	private static final long serialVersionUID = 1L;
}
