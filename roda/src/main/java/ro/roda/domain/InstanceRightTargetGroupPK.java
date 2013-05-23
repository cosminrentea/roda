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

public final class InstanceRightTargetGroupPK implements Serializable {

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

	private static final long serialVersionUID = 1L;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static InstanceRightTargetGroupPK fromJsonToInstanceRightTargetGroupPK(String json) {
        return new JSONDeserializer<InstanceRightTargetGroupPK>().use(null, InstanceRightTargetGroupPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<InstanceRightTargetGroupPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<InstanceRightTargetGroupPK> fromJsonArrayToInstanceRightTargetGroupPKs(String json) {
        return new JSONDeserializer<List<InstanceRightTargetGroupPK>>().use(null, ArrayList.class).use("values", InstanceRightTargetGroupPK.class).deserialize(json);
    }
}
