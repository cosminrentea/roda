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
public final class PersonAddressPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<PersonAddressPK> fromJsonArrayToPersonAddressPKs(String json) {
		return new JSONDeserializer<List<PersonAddressPK>>().use(null, ArrayList.class)
				.use("values", PersonAddressPK.class).deserialize(json);
	}

	public static PersonAddressPK fromJsonToPersonAddressPK(String json) {
		return new JSONDeserializer<PersonAddressPK>().use(null, PersonAddressPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonAddressPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "address_id", columnDefinition = "int4", nullable = false)
	private Integer addressId;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	public PersonAddressPK(Integer personId, Integer addressId) {
		super();
		this.personId = personId;
		this.addressId = addressId;
	}

	private PersonAddressPK() {
		super();
	}

	public Integer getAddressId() {
		return addressId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof PersonAddressPK) {
			final PersonAddressPK other = (PersonAddressPK) obj;
			return new EqualsBuilder().append(personId, other.personId).append(addressId, other.addressId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(personId).append(addressId).toHashCode();
	}
}
