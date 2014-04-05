package ro.roda.transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;

@Configurable
public class AuditRevisionsUser extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsUser> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("user");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static Set<AuditRevisionsUser> findAllAuditRevisionsObjects() {
		Set<AuditRevisionsUser> result = new HashSet<AuditRevisionsUser>();

		for (String username : RodaRevisionEntity.entityManager()
				.createQuery("SELECT username FROM RodaRevisionEntity o", String.class).getResultList()) {
			result.add(new AuditRevisionsUser(username));
		}

		return result;
	}

	private String user;

	public AuditRevisionsUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String object) {
		this.user = object;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("user");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
