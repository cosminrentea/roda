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

@Embeddable
@Configurable
public final class InstancePersonPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<InstancePersonPK> fromJsonArrayToInstancePersonPKs(String json) {
		return new JSONDeserializer<List<InstancePersonPK>>().use(null, ArrayList.class)
				.use("values", InstancePersonPK.class).deserialize(json);
	}

	public static InstancePersonPK fromJsonToInstancePersonPK(String json) {
		return new JSONDeserializer<InstancePersonPK>().use(null, InstancePersonPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstancePersonPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "assoc_type_id", columnDefinition = "int4", nullable = false)
	private Integer assocTypeId;

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	public InstancePersonPK(Integer personId, Integer instanceId, Integer assocTypeId) {
		super();
		this.personId = personId;
		this.instanceId = instanceId;
		this.assocTypeId = assocTypeId;
	}

	private InstancePersonPK() {
		super();
	}

	public Integer getAssocTypeId() {
		return assocTypeId;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
