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
public final class InstanceOrgPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static InstanceOrgPK fromJsonToInstanceOrgPK(String json) {
		return new JSONDeserializer<InstanceOrgPK>().use(null, InstanceOrgPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceOrgPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<InstanceOrgPK> fromJsonArrayToInstanceOrgPKs(String json) {
		return new JSONDeserializer<List<InstanceOrgPK>>().use(null, ArrayList.class)
				.use("values", InstanceOrgPK.class).deserialize(json);
	}

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "assoc_type_id", columnDefinition = "int4", nullable = false)
	private Integer assocTypeId;

	public InstanceOrgPK(Integer orgId, Integer instanceId, Integer assocTypeId) {
		super();
		this.orgId = orgId;
		this.instanceId = instanceId;
		this.assocTypeId = assocTypeId;
	}

	private InstanceOrgPK() {
		super();
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Integer getAssocTypeId() {
		return assocTypeId;
	}
}
