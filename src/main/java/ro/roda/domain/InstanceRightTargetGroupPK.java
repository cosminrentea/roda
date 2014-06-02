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
public final class InstanceRightTargetGroupPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<InstanceRightTargetGroupPK> fromJsonArrayToInstanceRightTargetGroupPKs(String json) {
		return new JSONDeserializer<List<InstanceRightTargetGroupPK>>().use(null, ArrayList.class)
				.use("values", InstanceRightTargetGroupPK.class).deserialize(json);
	}

	public static InstanceRightTargetGroupPK fromJsonToInstanceRightTargetGroupPK(String json) {
		return new JSONDeserializer<InstanceRightTargetGroupPK>().use(null, InstanceRightTargetGroupPK.class)
				.deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceRightTargetGroupPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "instance_right_id", columnDefinition = "int4", nullable = false)
	private Integer instanceRightId;

	@Column(name = "target_group_id", columnDefinition = "int4", nullable = false)
	private Integer targetGroupId;

	public InstanceRightTargetGroupPK(Integer instanceId, Integer instanceRightId, Integer targetGroupId) {
		super();
		this.instanceId = instanceId;
		this.instanceRightId = instanceRightId;
		this.targetGroupId = targetGroupId;
	}

	private InstanceRightTargetGroupPK() {
		super();
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Integer getInstanceRightId() {
		return instanceRightId;
	}

	public Integer getTargetGroupId() {
		return targetGroupId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof InstanceRightTargetGroupPK) {
			final InstanceRightTargetGroupPK other = (InstanceRightTargetGroupPK) obj;
			return new EqualsBuilder().append(instanceRightId, other.instanceRightId)
					.append(instanceId, other.instanceId).append(targetGroupId, other.targetGroupId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(instanceRightId).append(instanceId).append(targetGroupId).toHashCode();
	}
}
