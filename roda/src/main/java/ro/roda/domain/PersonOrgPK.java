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

@Embeddable
@Configurable
public final class PersonOrgPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<PersonOrgPK> fromJsonArrayToPersonOrgPKs(String json) {
		return new JSONDeserializer<List<PersonOrgPK>>().use(null, ArrayList.class).use("values", PersonOrgPK.class)
				.deserialize(json);
	}

	public static PersonOrgPK fromJsonToPersonOrgPK(String json) {
		return new JSONDeserializer<PersonOrgPK>().use(null, PersonOrgPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonOrgPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	@Column(name = "role_id", columnDefinition = "int4", nullable = false)
	private Integer roleId;

	public PersonOrgPK(Integer personId, Integer orgId, Integer roleId) {
		super();
		this.personId = personId;
		this.orgId = orgId;
		this.roleId = roleId;
	}

	private PersonOrgPK() {
		super();
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
