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
public final class OrgAddressPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<OrgAddressPK> fromJsonArrayToOrgAddressPKs(String json) {
		return new JSONDeserializer<List<OrgAddressPK>>().use(null, ArrayList.class).use("values", OrgAddressPK.class)
				.deserialize(json);
	}

	public static OrgAddressPK fromJsonToOrgAddressPK(String json) {
		return new JSONDeserializer<OrgAddressPK>().use(null, OrgAddressPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgAddressPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "address_id", columnDefinition = "int4", nullable = false)
	private Integer addressId;

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	public OrgAddressPK(Integer orgId, Integer addressId) {
		super();
		this.orgId = orgId;
		this.addressId = addressId;
	}

	private OrgAddressPK() {
		super();
	}

	public Integer getAddressId() {
		return addressId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrgAddressPK) {
			final OrgAddressPK other = (OrgAddressPK) obj;
			return new EqualsBuilder().append(orgId, other.orgId).append(addressId, other.addressId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orgId).append(addressId).toHashCode();
	}
}
