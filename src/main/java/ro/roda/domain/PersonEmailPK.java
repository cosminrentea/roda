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
public final class PersonEmailPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<PersonEmailPK> fromJsonArrayToPersonEmailPKs(String json) {
		return new JSONDeserializer<List<PersonEmailPK>>().use(null, ArrayList.class)
				.use("values", PersonEmailPK.class).deserialize(json);
	}

	public static PersonEmailPK fromJsonToPersonEmailPK(String json) {
		return new JSONDeserializer<PersonEmailPK>().use(null, PersonEmailPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonEmailPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "email_id", columnDefinition = "int4", nullable = false)
	private Integer emailId;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	public PersonEmailPK(Integer personId, Integer emailId) {
		super();
		this.personId = personId;
		this.emailId = emailId;
	}

	private PersonEmailPK() {
		super();
	}

	public Integer getEmailId() {
		return emailId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof PersonEmailPK) {
			final PersonEmailPK other = (PersonEmailPK) obj;
			return new EqualsBuilder().append(personId, other.personId).append(emailId, other.emailId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(personId).append(emailId).toHashCode();
	}
}
