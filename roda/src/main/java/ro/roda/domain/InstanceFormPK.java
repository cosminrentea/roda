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
public final class InstanceFormPK implements Serializable {

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "form_id", columnDefinition = "int8", nullable = false)
	private Long formId;

	public InstanceFormPK(Integer instanceId, Long formId) {
		super();
		this.instanceId = instanceId;
		this.formId = formId;
	}

	private InstanceFormPK() {
		super();
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Long getFormId() {
		return formId;
	}

	private static final long serialVersionUID = 1L;

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static InstanceFormPK fromJsonToInstanceFormPK(String json) {
		return new JSONDeserializer<InstanceFormPK>().use(null, InstanceFormPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceFormPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<InstanceFormPK> fromJsonArrayToInstanceFormPKs(String json) {
		return new JSONDeserializer<List<InstanceFormPK>>().use(null, ArrayList.class)
				.use("values", InstanceFormPK.class).deserialize(json);
	}
}
