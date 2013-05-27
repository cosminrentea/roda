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
public final class FormEditedNumberVarPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<FormEditedNumberVarPK> fromJsonArrayToFormEditedNumberVarPKs(String json) {
		return new JSONDeserializer<List<FormEditedNumberVarPK>>().use(null, ArrayList.class)
				.use("values", FormEditedNumberVarPK.class).deserialize(json);
	}

	public static FormEditedNumberVarPK fromJsonToFormEditedNumberVarPK(String json) {
		return new JSONDeserializer<FormEditedNumberVarPK>().use(null, FormEditedNumberVarPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<FormEditedNumberVarPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "form_id", columnDefinition = "int8", nullable = false)
	private Long formId;

	@Column(name = "variable_id", columnDefinition = "int8", nullable = false)
	private Long variableId;

	public FormEditedNumberVarPK(Long formId, Long variableId) {
		super();
		this.formId = formId;
		this.variableId = variableId;
	}

	private FormEditedNumberVarPK() {
		super();
	}

	public Long getFormId() {
		return formId;
	}

	public Long getVariableId() {
		return variableId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
