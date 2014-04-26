package ro.roda.domainjson;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayoutGroup;
import flexjson.JSONSerializer;

@Configurable
public class GroupSave {

	public static String toJsonArray(Collection<GroupSave> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(collection);
	}

	public static GroupSave findGroupSave(String groupname, Integer parent, String description) {
		if (groupname == null)
			return null;

		EntityManager em = CmsLayoutGroup.entityManager();
		TypedQuery<CmsLayoutGroup> q = em
				.createQuery(
						"SELECT o FROM CmsLayoutGroup AS o WHERE LOWER(o.name) LIKE LOWER(:groupname) AND o.parentId = :parent  AND o.description = description",
						CmsLayoutGroup.class);
		q.setParameter("groupname", groupname);
		q.setParameter("parent", parent);
		q.setParameter("description", description);

		List<CmsLayoutGroup> queryResult = q.getResultList();
		if (queryResult.size() > 0) {
			return new GroupSave(true, "Grup creat cu succes");
		} else {
			return new GroupSave(false, "Grupul nu a fost creat");
		}
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private boolean succes;

	private String message;

	private String type;

	public GroupSave(boolean succes, String message) {
		this.succes = succes;
		this.message = message;
	}

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(this);
	}
}
