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
public final class OrgAddressPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	@Column(name = "address_id", columnDefinition = "int4", nullable = false)
	private Integer addressId;

	public OrgAddressPK(Integer orgId, Integer addressId) {
		super();
		this.orgId = orgId;
		this.addressId = addressId;
	}

	private OrgAddressPK() {
		super();
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static OrgAddressPK fromJsonToOrgAddressPK(String json) {
		return new JSONDeserializer<OrgAddressPK>().use(null, OrgAddressPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgAddressPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<OrgAddressPK> fromJsonArrayToOrgAddressPKs(String json) {
		return new JSONDeserializer<List<OrgAddressPK>>().use(null, ArrayList.class).use("values", OrgAddressPK.class)
				.deserialize(json);
	}
}
