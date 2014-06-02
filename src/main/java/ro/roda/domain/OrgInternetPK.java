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
public final class OrgInternetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<OrgInternetPK> fromJsonArrayToOrgInternetPKs(String json) {
		return new JSONDeserializer<List<OrgInternetPK>>().use(null, ArrayList.class)
				.use("values", OrgInternetPK.class).deserialize(json);
	}

	public static OrgInternetPK fromJsonToOrgInternetPK(String json) {
		return new JSONDeserializer<OrgInternetPK>().use(null, OrgInternetPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgInternetPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "internet_id", columnDefinition = "int4", nullable = false)
	private Integer internetId;

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	public OrgInternetPK(Integer orgId, Integer internetId) {
		super();
		this.orgId = orgId;
		this.internetId = internetId;
	}

	private OrgInternetPK() {
		super();
	}

	public Integer getInternetId() {
		return internetId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrgInternetPK) {
			final OrgInternetPK other = (OrgInternetPK) obj;
			return new EqualsBuilder().append(orgId, other.orgId).append(internetId, other.internetId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orgId).append(internetId).toHashCode();
	}
}
