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
public final class InstanceVariablePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<InstanceVariablePK> fromJsonArrayToInstanceVariablePKs(String json) {
		return new JSONDeserializer<List<InstanceVariablePK>>().use(null, ArrayList.class)
				.use("values", InstanceVariablePK.class).deserialize(json);
	}

	public static InstanceVariablePK fromJsonToInstanceVariablePK(String json) {
		return new JSONDeserializer<InstanceVariablePK>().use(null, InstanceVariablePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceVariablePK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "variable_id", columnDefinition = "int8", nullable = false)
	private Long variableId;

	public InstanceVariablePK(Integer instanceId, Long variableId) {
		super();
		this.instanceId = instanceId;
		this.variableId = variableId;
	}

	private InstanceVariablePK() {
		super();
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Long getVariableId() {
		return variableId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof InstanceVariablePK) {
			final InstanceVariablePK other = (InstanceVariablePK) obj;
			return new EqualsBuilder().append(instanceId, other.instanceId).append(variableId, other.variableId)
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(instanceId).append(variableId).toHashCode();
	}
}
