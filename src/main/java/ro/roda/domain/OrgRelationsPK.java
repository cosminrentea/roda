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
public final class OrgRelationsPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<OrgRelationsPK> fromJsonArrayToOrgRelationsPKs(String json) {
		return new JSONDeserializer<List<OrgRelationsPK>>().use(null, ArrayList.class)
				.use("values", OrgRelationsPK.class).deserialize(json);
	}

	public static OrgRelationsPK fromJsonToOrgRelationsPK(String json) {
		return new JSONDeserializer<OrgRelationsPK>().use(null, OrgRelationsPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgRelationsPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "org_1_id", columnDefinition = "int4", nullable = false)
	private Integer org1Id;

	@Column(name = "org_2_id", columnDefinition = "int4", nullable = false)
	private Integer org2Id;

	@Column(name = "org_relation_type_id", columnDefinition = "int4", nullable = false)
	private Integer orgRelationTypeId;

	public OrgRelationsPK(Integer org1Id, Integer org2Id, Integer orgRelationTypeId) {
		super();
		this.org1Id = org1Id;
		this.org2Id = org2Id;
		this.orgRelationTypeId = orgRelationTypeId;
	}

	private OrgRelationsPK() {
		super();
	}

	public Integer getOrg1Id() {
		return org1Id;
	}

	public Integer getOrg2Id() {
		return org2Id;
	}

	public Integer getOrgRelationTypeId() {
		return orgRelationTypeId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof OrgRelationsPK) {
			final OrgRelationsPK other = (OrgRelationsPK) obj;
			return new EqualsBuilder().append(org1Id, other.org1Id).append(org2Id, other.org2Id)
					.append(orgRelationTypeId, other.orgRelationTypeId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(org1Id).append(org2Id).append(orgRelationTypeId).toHashCode();
	}
}
