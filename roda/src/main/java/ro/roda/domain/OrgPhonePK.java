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

@Configurable
@Embeddable
public final class OrgPhonePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<OrgPhonePK> fromJsonArrayToOrgPhonePKs(String json) {
		return new JSONDeserializer<List<OrgPhonePK>>().use(null, ArrayList.class).use("values", OrgPhonePK.class)
				.deserialize(json);
	}

	public static OrgPhonePK fromJsonToOrgPhonePK(String json) {
		return new JSONDeserializer<OrgPhonePK>().use(null, OrgPhonePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgPhonePK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	@Column(name = "phone_id", columnDefinition = "int4", nullable = false)
	private Integer phoneId;

	public OrgPhonePK(Integer orgId, Integer phoneId) {
		super();
		this.orgId = orgId;
		this.phoneId = phoneId;
	}

	private OrgPhonePK() {
		super();
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getPhoneId() {
		return phoneId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
