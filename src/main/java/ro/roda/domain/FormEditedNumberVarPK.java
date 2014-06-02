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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof FormEditedNumberVarPK) {
			final FormEditedNumberVarPK other = (FormEditedNumberVarPK) obj;
			return new EqualsBuilder().append(formId, other.formId).append(variableId, other.variableId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(formId).append(formId).toHashCode();
	}
}
