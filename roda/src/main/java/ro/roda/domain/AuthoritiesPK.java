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
public final class AuthoritiesPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<AuthoritiesPK> fromJsonArrayToAuthoritiesPKs(String json) {
		return new JSONDeserializer<List<AuthoritiesPK>>().use(null, ArrayList.class)
				.use("values", AuthoritiesPK.class).deserialize(json);
	}

	public static AuthoritiesPK fromJsonToAuthoritiesPK(String json) {
		return new JSONDeserializer<AuthoritiesPK>().use(null, AuthoritiesPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<AuthoritiesPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "authority", columnDefinition = "varchar", nullable = false, length = 64)
	private String authority;

	@Column(name = "username", columnDefinition = "varchar", nullable = false, length = 64)
	private String username;

	public AuthoritiesPK(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}

	private AuthoritiesPK() {
		super();
	}

	public String getAuthority() {
		return authority;
	}

	public String getUsername() {
		return username;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
