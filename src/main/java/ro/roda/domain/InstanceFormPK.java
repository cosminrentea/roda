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

@Embeddable
@Configurable
public final class InstanceFormPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<InstanceFormPK> fromJsonArrayToInstanceFormPKs(String json) {
		return new JSONDeserializer<List<InstanceFormPK>>().use(null, ArrayList.class)
				.use("values", InstanceFormPK.class).deserialize(json);
	}

	public static InstanceFormPK fromJsonToInstanceFormPK(String json) {
		return new JSONDeserializer<InstanceFormPK>().use(null, InstanceFormPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceFormPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "form_id", columnDefinition = "int8", nullable = false)
	private Long formId;

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	public InstanceFormPK(Integer instanceId, Long formId) {
		super();
		this.instanceId = instanceId;
		this.formId = formId;
	}

	private InstanceFormPK() {
		super();
	}

	public Long getFormId() {
		return formId;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof InstanceFormPK) {
			final InstanceFormPK other = (InstanceFormPK) obj;
			return new EqualsBuilder().append(instanceId, other.instanceId).append(formId, other.formId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(instanceId).append(formId).toHashCode();
	}
}
