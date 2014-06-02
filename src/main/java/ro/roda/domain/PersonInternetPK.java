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
public final class PersonInternetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<PersonInternetPK> fromJsonArrayToPersonInternetPKs(String json) {
		return new JSONDeserializer<List<PersonInternetPK>>().use(null, ArrayList.class)
				.use("values", PersonInternetPK.class).deserialize(json);
	}

	public static PersonInternetPK fromJsonToPersonInternetPK(String json) {
		return new JSONDeserializer<PersonInternetPK>().use(null, PersonInternetPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonInternetPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "internet_id", columnDefinition = "int4", nullable = false)
	private Integer internetId;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	public PersonInternetPK(Integer personId, Integer internetId) {
		super();
		this.personId = personId;
		this.internetId = internetId;
	}

	private PersonInternetPK() {
		super();
	}

	public Integer getInternetId() {
		return internetId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof PersonInternetPK) {
			final PersonInternetPK other = (PersonInternetPK) obj;
			return new EqualsBuilder().append(personId, other.personId).append(internetId, other.internetId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(personId).append(internetId).toHashCode();
	}
}
