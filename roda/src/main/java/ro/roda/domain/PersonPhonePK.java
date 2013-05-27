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
public final class PersonPhonePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static PersonPhonePK fromJsonToPersonPhonePK(String json) {
		return new JSONDeserializer<PersonPhonePK>().use(null, PersonPhonePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonPhonePK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<PersonPhonePK> fromJsonArrayToPersonPhonePKs(String json) {
		return new JSONDeserializer<List<PersonPhonePK>>().use(null, ArrayList.class)
				.use("values", PersonPhonePK.class).deserialize(json);
	}

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	@Column(name = "phone_id", columnDefinition = "int4", nullable = false)
	private Integer phoneId;

	public PersonPhonePK(Integer personId, Integer phoneId) {
		super();
		this.personId = personId;
		this.phoneId = phoneId;
	}

	private PersonPhonePK() {
		super();
	}

	public Integer getPersonId() {
		return personId;
	}

	public Integer getPhoneId() {
		return phoneId;
	}
}
