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
public final class InstanceDescrPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<InstanceDescrPK> fromJsonArrayToInstanceDescrPKs(String json) {
		return new JSONDeserializer<List<InstanceDescrPK>>().use(null, ArrayList.class)
				.use("values", InstanceDescrPK.class).deserialize(json);
	}

	public static InstanceDescrPK fromJsonToInstanceDescrPK(String json) {
		return new JSONDeserializer<InstanceDescrPK>().use(null, InstanceDescrPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceDescrPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "instance_id", columnDefinition = "int4", nullable = false)
	private Integer instanceId;

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
	private Integer langId;

	public InstanceDescrPK(Integer instanceId, Integer langId) {
		super();
		this.instanceId = instanceId;
		this.langId = langId;
	}

	private InstanceDescrPK() {
		super();
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public Integer getLangId() {
		return langId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof InstanceDescrPK) {
			final InstanceDescrPK other = (InstanceDescrPK) obj;
			return new EqualsBuilder().append(instanceId, other.instanceId).append(langId, other.langId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(instanceId).append(langId).toHashCode();
	}
}
