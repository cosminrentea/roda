package ro.roda.transformer;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;

@Configurable
public class AuditRevisionsObject extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsObject> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("object");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static Set<AuditRevisionsObject> findAllAuditRevisionsObjects() {
		Set<AuditRevisionsObject> result = new HashSet<AuditRevisionsObject>();

		String[] auditedClassNames = getAuditedClasses("ro.roda.domain");

		// TODO verify if the below code might be optimized
		for (String auditedClassName : auditedClassNames) {

			try {
				Class<?> auditedClass = Class.forName(auditedClassName);

				// the class name without package name
				auditedClassName = auditedClassName.substring(auditedClassName.lastIndexOf(".") + 1);

				if (auditedClass != null) {

					Method countRows = auditedClass.getMethod("count"
							+ (auditedClassName.substring(auditedClassName.length() - 1).equals("s") ? auditedClassName
									+ "es" : auditedClassName + "s"));
					if (Long.parseLong(countRows.invoke(null).toString()) > 0) {

						List<RodaRevisionEntity> rre = RodaRevisionEntity.findAllRodaRevisionEntities();

						for (RodaRevisionEntity revision : rre) {
							if (Integer.parseInt(revision.getAuditReader().createQuery()
									.forEntitiesModifiedAtRevision(auditedClass, revision.getId())
									.addProjection(AuditEntity.revisionNumber().count()).getSingleResult().toString()) > 0) {
								result.add(new AuditRevisionsObject(auditedClassName));
							}

						}

					}
				}
			} catch (Exception e) {
				// TODO
			}
		}

		return result;
	}

	private String object;

	public AuditRevisionsObject(String object) {
		this.object = object;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("object");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
