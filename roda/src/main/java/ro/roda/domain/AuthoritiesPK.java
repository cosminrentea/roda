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

public final class AuthoritiesPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static AuthoritiesPK fromJsonToAuthoritiesPK(String json) {
        return new JSONDeserializer<AuthoritiesPK>().use(null, AuthoritiesPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<AuthoritiesPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<AuthoritiesPK> fromJsonArrayToAuthoritiesPKs(String json) {
        return new JSONDeserializer<List<AuthoritiesPK>>().use(null, ArrayList.class).use("values", AuthoritiesPK.class).deserialize(json);
    }

	@Column(name = "username", columnDefinition = "varchar", nullable = false, length = 64)
    private String username;

	@Column(name = "authority", columnDefinition = "varchar", nullable = false, length = 64)
    private String authority;

	public AuthoritiesPK(String username, String authority) {
        super();
        this.username = username;
        this.authority = authority;
    }

	private AuthoritiesPK() {
        super();
    }

	public String getUsername() {
        return username;
    }

	public String getAuthority() {
        return authority;
    }
}
