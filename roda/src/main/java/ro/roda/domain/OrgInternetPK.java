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
public final class OrgInternetPK implements Serializable {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static OrgInternetPK fromJsonToOrgInternetPK(String json) {
		return new JSONDeserializer<OrgInternetPK>().use(null, OrgInternetPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgInternetPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<OrgInternetPK> fromJsonArrayToOrgInternetPKs(String json) {
		return new JSONDeserializer<List<OrgInternetPK>>().use(null, ArrayList.class)
				.use("values", OrgInternetPK.class).deserialize(json);
	}

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	@Column(name = "internet_id", columnDefinition = "int4", nullable = false)
	private Integer internetId;

	public OrgInternetPK(Integer orgId, Integer internetId) {
		super();
		this.orgId = orgId;
		this.internetId = internetId;
	}

	private OrgInternetPK() {
		super();
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getInternetId() {
		return internetId;
	}

	private static final long serialVersionUID = 1L;
}
