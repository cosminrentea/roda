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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof AuthoritiesPK) {
			final AuthoritiesPK other = (AuthoritiesPK) obj;
			return new EqualsBuilder().append(username, other.username).append(authority, other.authority).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(username).append(authority).toHashCode();
	}
}
