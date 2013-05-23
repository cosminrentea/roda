package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

@Configurable
@Embeddable

public final class PersonAddressPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static PersonAddressPK fromJsonToPersonAddressPK(String json) {
        return new JSONDeserializer<PersonAddressPK>().use(null, PersonAddressPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<PersonAddressPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<PersonAddressPK> fromJsonArrayToPersonAddressPKs(String json) {
        return new JSONDeserializer<List<PersonAddressPK>>().use(null, ArrayList.class).use("values", PersonAddressPK.class).deserialize(json);
    }

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
    private Integer personId;

	@Column(name = "address_id", columnDefinition = "int4", nullable = false)
    private Integer addressId;

	public PersonAddressPK(Integer personId, Integer addressId) {
        super();
        this.personId = personId;
        this.addressId = addressId;
    }

	private PersonAddressPK() {
        super();
    }

	public Integer getPersonId() {
        return personId;
    }

	public Integer getAddressId() {
        return addressId;
    }
}
