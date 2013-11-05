package ro.roda.transformer;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Users;
import flexjson.JSONSerializer;

@Configurable
public class Login {

	public static String toJsonArray(Collection<Login> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(collection);
	}

	public static Login findLogin(String username, String password) {
		if (username == null)
			return null;

		EntityManager em = Users.entityManager();
		TypedQuery<Users> q = em
				.createQuery(
						"SELECT o FROM Users AS o WHERE LOWER(o.username) LIKE LOWER(:username)  AND LOWER(o.password) LIKE LOWER(:password)  AND o.enabled = true",
						Users.class);
		q.setParameter("username", username);
		q.setParameter("password", password);

		List<Users> queryResult = q.getResultList();
		if (queryResult.size() > 0) {
			return new Login(true, "Auth ok");
		} else {
			return new Login(false, "Auth failed");
		}
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private boolean success;

	private String message;

	public Login(boolean success, String message) {
		this.success = success;
		this.message = message;

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(this);
	}
}
