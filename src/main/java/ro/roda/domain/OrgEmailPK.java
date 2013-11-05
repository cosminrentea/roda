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
public final class OrgEmailPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<OrgEmailPK> fromJsonArrayToOrgEmailPKs(String json) {
		return new JSONDeserializer<List<OrgEmailPK>>().use(null, ArrayList.class).use("values", OrgEmailPK.class)
				.deserialize(json);
	}

	public static OrgEmailPK fromJsonToOrgEmailPK(String json) {
		return new JSONDeserializer<OrgEmailPK>().use(null, OrgEmailPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgEmailPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "email_id", columnDefinition = "int4", nullable = false)
	private Integer emailId;

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	public OrgEmailPK(Integer orgId, Integer emailId) {
		super();
		this.orgId = orgId;
		this.emailId = emailId;
	}

	private OrgEmailPK() {
		super();
	}

	public Integer getEmailId() {
		return emailId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrgEmailPK) {
			final OrgEmailPK other = (OrgEmailPK) obj;
			return new EqualsBuilder().append(orgId, other.orgId).append(emailId, other.emailId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orgId).append(emailId).toHashCode();
	}
}
