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

public final class PersonEmailPK implements Serializable {

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
    private Integer personId;

	@Column(name = "email_id", columnDefinition = "int4", nullable = false)
    private Integer emailId;

	public PersonEmailPK(Integer personId, Integer emailId) {
        super();
        this.personId = personId;
        this.emailId = emailId;
    }

	private PersonEmailPK() {
        super();
    }

	public Integer getPersonId() {
        return personId;
    }

	public Integer getEmailId() {
        return emailId;
    }

	private static final long serialVersionUID = 1L;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static PersonEmailPK fromJsonToPersonEmailPK(String json) {
        return new JSONDeserializer<PersonEmailPK>().use(null, PersonEmailPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<PersonEmailPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<PersonEmailPK> fromJsonArrayToPersonEmailPKs(String json) {
        return new JSONDeserializer<List<PersonEmailPK>>().use(null, ArrayList.class).use("values", PersonEmailPK.class).deserialize(json);
    }
}
