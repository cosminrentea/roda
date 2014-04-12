package ro.roda.transformer;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;

@Configurable
public class AuditRevisionsObject extends JsonInfo implements Comparable<AuditRevisionsObject> {

	public static String toJsonArray(Collection<AuditRevisionsObject> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("object", "nrrev", "lastrevision");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static Set<AuditRevisionsObject> findAllAuditRevisionsObjects() {
		Set<AuditRevisionsObject> result = new TreeSet<AuditRevisionsObject>();

		String[] auditedClassNames = getAuditedClasses("ro.roda.domain");

		Map<String, Integer> objectRevisionsCount = new HashMap<String, Integer>();
		Map<String, Date> objectRevisionsLastDate = new HashMap<String, Date>();

		// TODO verify if the code below might be optimized
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
							Integer rowsModified = Integer.parseInt(revision.getAuditReader().createQuery()
									.forEntitiesModifiedAtRevision(auditedClass, revision.getId())
									.addProjection(AuditEntity.revisionNumber().count()).getSingleResult().toString());
							if (rowsModified > 0) {
								// result.add(new
								// AuditRevisionsObject(auditedClassName));
								if (!objectRevisionsCount.containsKey(auditedClassName)) {
									objectRevisionsCount.put(auditedClassName, 1);
									objectRevisionsLastDate.put(auditedClassName, revision.getRevisionDate());
								} else {
									objectRevisionsCount.put(auditedClassName,
											objectRevisionsCount.get(auditedClassName) + 1);
									if (objectRevisionsLastDate.get(auditedClassName)
											.before(revision.getRevisionDate())) {
										objectRevisionsLastDate.put(auditedClassName, revision.getRevisionDate());
									}
								}

							}

						}

					}
				}
			} catch (Exception e) {
				// TODO
			}
		}

		for (String objectName : objectRevisionsCount.keySet()) {
			result.add(new AuditRevisionsObject(objectName, objectRevisionsCount.get(objectName),
					objectRevisionsLastDate.get(objectName)));
		}

		return result;
	}

	private String object;

	private Integer nrrev;

	private Date lastrevision;

	public AuditRevisionsObject(String object, Integer nrrev, Date lastrevision) {
		this.object = object;
		this.nrrev = nrrev;
		this.lastrevision = lastrevision;
	}

	public Date getLastrevision() {
		return lastrevision;
	}

	public Integer getNrrev() {
		return nrrev;
	}

	public String getObject() {
		return object;
	}

	public void setLastrevision(Date lastrevision) {
		this.lastrevision = lastrevision;
	}

	public void setNrrev(Integer nrrev) {
		this.nrrev = nrrev;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("object", "nrrev", "lastrevision");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(AuditRevisionsObject auditRevisionsObject) {
		return this.object.compareTo(auditRevisionsObject.getObject());
	}
}
