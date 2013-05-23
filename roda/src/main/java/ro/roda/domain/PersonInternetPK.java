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

@Embeddable
@Configurable
public final class PersonInternetPK implements Serializable {

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	@Column(name = "internet_id", columnDefinition = "int4", nullable = false)
	private Integer internetId;

	public PersonInternetPK(Integer personId, Integer internetId) {
		super();
		this.personId = personId;
		this.internetId = internetId;
	}

	private PersonInternetPK() {
		super();
	}

	public Integer getPersonId() {
		return personId;
	}

	public Integer getInternetId() {
		return internetId;
	}

	private static final long serialVersionUID = 1L;

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static PersonInternetPK fromJsonToPersonInternetPK(String json) {
		return new JSONDeserializer<PersonInternetPK>().use(null, PersonInternetPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonInternetPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<PersonInternetPK> fromJsonArrayToPersonInternetPKs(String json) {
		return new JSONDeserializer<List<PersonInternetPK>>().use(null, ArrayList.class)
				.use("values", PersonInternetPK.class).deserialize(json);
	}
}
