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
public final class InstanceOrgPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<InstanceOrgPK> fromJsonArrayToInstanceOrgPKs(String json) {
		return new JSONDeserializer<List<InstanceOrgPK>>().use(null, ArrayList.class)
				.use("values", InstanceOrgPK.class).deserialize(json);
	}

	public static InstanceOrgPK fromJsonToInstanceOrgPK(String json) {
		return new JSONDeserializer<InstanceOrgPK>().use(null, InstanceOrgPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceOrgPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "assoc_type_id", columnDefinition = "int4", nullable = false)
	private Integer assocTypeId;

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	public InstanceOrgPK(Integer orgId, Integer instanceId, Integer assocTypeId) {
		super();
		this.orgId = orgId;
		this.instanceId = instanceId;
		this.assocTypeId = assocTypeId;
	}

	private InstanceOrgPK() {
		super();
	}

	public Integer getAssocTypeId() {
		return assocTypeId;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof InstanceOrgPK) {
			final InstanceOrgPK other = (InstanceOrgPK) obj;
			return new EqualsBuilder().append(orgId, other.orgId).append(instanceId, other.instanceId)
					.append(assocTypeId, other.assocTypeId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orgId).append(instanceId).append(assocTypeId).toHashCode();
	}
}
