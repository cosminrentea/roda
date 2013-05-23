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

public final class InstanceDescrPK implements Serializable {

	private static final long serialVersionUID = 1L;

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

	public static InstanceDescrPK fromJsonToInstanceDescrPK(String json) {
        return new JSONDeserializer<InstanceDescrPK>().use(null, InstanceDescrPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<InstanceDescrPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<InstanceDescrPK> fromJsonArrayToInstanceDescrPKs(String json) {
        return new JSONDeserializer<List<InstanceDescrPK>>().use(null, ArrayList.class).use("values", InstanceDescrPK.class).deserialize(json);
    }
}
