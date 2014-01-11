package ro.roda.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditRevisionsByObject extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsByObject> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("object", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.rows");
		serializer.exclude("revisions.objects");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditRevisionsByObject> findAllRevisionsByObject() {
		List<AuditRevisionsByObject> result = new ArrayList<AuditRevisionsByObject>();

		// TODO: cycle through all object types and get their corresponding
		// revisions
		/*
		 * List<RodaRevisionEntity> revisions =
		 * RodaRevisionEntity.findAllRodaRevisionEntities();
		 * 
		 * if (revisions != null && revisions.size() > 0) {
		 * 
		 * Iterator<RodaRevisionEntity> revisionsIterator =
		 * revisions.iterator(); while (revisionsIterator.hasNext()) {
		 * RodaRevisionEntity revision = (RodaRevisionEntity)
		 * revisionsIterator.next(); result.add(new
		 * AuditRevisionsByObject(revision)); } }
		 */

		return result;
	}

	public static AuditRevisionsByObject findRevisionsByObject(String object) {

		/*
		 * RodaRevisionEntity revision =
		 * RodaRevisionEntity.findRodaRevisionEntity(id);
		 * 
		 * if (revision != null) { return new AuditRevisionsByObject(revision);
		 * } return null;
		 */

		return new AuditRevisionsByObject(object);
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	private String object;

	private Integer nrrev;

	private Date lastrevision;

	private Set<AuditRevision> revisions;

	public AuditRevisionsByObject(String object, Integer nrRev, Date lastRevision, Set<AuditRevision> revisions) {
		onConstructRevisionsByObject(object, nrRev, lastRevision, revisions);
	}

	public AuditRevisionsByObject(String objectName) {

		try {
			Class<?> auditedClass = Class.forName("ro.roda.domain." + objectName);

			Integer nrRev = null;
			Date lastRevision = null;

			if (auditedClass != null) {
				Method getAuditReaderMethod = auditedClass.getMethod("getClassAuditReader");
				Method getid = auditedClass.getMethod("getId");

				if (getAuditReaderMethod != null) {
					AuditReader auditReader = (AuditReader) getAuditReaderMethod.invoke(null);

					// get the revisions for the object
					AuditQuery queryRevisions = auditReader.createQuery().forRevisionsOfEntity(auditedClass, false,
							true);

					List<?> resultRevisions = queryRevisions.getResultList();

					Iterator<?> iteratorRevisions = resultRevisions.iterator();

					Set<AuditRevision> revisions = new HashSet<AuditRevision>();
					while (iteratorRevisions.hasNext()) {

						Object o = iteratorRevisions.next();

						RodaRevisionEntity revision = (RodaRevisionEntity) ((Object[]) o)[1];

						// get the entities modified at the revision, for the
						// given class
						AuditQuery queryEntities = revision.getAuditReader().createQuery()
								.forEntitiesModifiedAtRevision(auditedClass, revision.getId());
						List<?> resultEntities = queryEntities.getResultList();
						Iterator<?> iteratorEntities = resultEntities.iterator();

						Set<AuditRow> auditRows = new HashSet<AuditRow>();
						while (iteratorEntities.hasNext()) {
							Object object = iteratorEntities.next();

							Integer objectId = Integer.parseInt(getid.invoke(object).toString());

							Set<AuditField> auditedFields = new HashSet<AuditField>();
							Field[] classFields = auditedClass.getDeclaredFields();
							for (int j = 0; j < classFields.length; j++) {
								Field classField = classFields[j];
								// TODO get the fields correctly
								try {
									Method getAuditedField = auditedClass.getMethod("get"
											+ classField.getName().substring(0, 1).toUpperCase()
											+ classField.getName().substring(1));
									auditedFields.add(new AuditField(classField.getName(), getAuditedField.invoke(
											object).toString()));
								} catch (Exception e) {
									// TODO
								}
							}

							// get the revision type (insert, update or delete)
							AuditQuery queryRev = revision.getAuditReader().createQuery()
									.forRevisionsOfEntity(auditedClass, false, true).add(AuditEntity.id().eq(objectId));
							RevisionType revType = (RevisionType) ((Object[]) queryRev.getResultList().get(0))[2];
							auditRows.add(new AuditRow(objectId, revType != null ? revType.toString() : "",
									auditedFields.size(), auditedFields));

						}
						if (auditRows.size() > 0) {
							// TODO: get the correct userid
							revisions.add(new AuditRevision(revision.getId(), revision.getRevisionDate(), revision
									.getUsername(), null, auditRows.size(), auditRows, null));

							if (lastRevision == null) {
								lastRevision = revision.getRevisionDate();
							} else {
								if (revision.getRevisionDate().after(lastRevision)) {
									lastRevision = revision.getRevisionDate();
								}
							}
						}

					}

					nrRev = revisions.size();

					// TODO: get the userid
					onConstructRevisionsByObject(objectName, nrRev, lastRevision, revisions);
				}
			}
		} catch (Exception e) {
			// TODO
			System.out.println("Exception thrown when getting revision info. " + e.getMessage());
			// e.printStackTrace();
		}
	}

	private void onConstructRevisionsByObject(String object, Integer nrRev, Date lastRevision,
			Set<AuditRevision> revisions) {
		this.object = object;
		this.nrrev = nrRev;
		this.lastrevision = lastRevision;
		this.revisions = revisions;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Integer getNrrev() {
		return nrrev;
	}

	public void setNrrev(Integer nrrev) {
		this.nrrev = nrrev;
	}

	public Date getLastrevision() {
		return lastrevision;
	}

	public void setLastrevision(Date lastrevision) {
		this.lastrevision = lastrevision;
	}

	public Set<AuditRevision> getRevisions() {
		return revisions;
	}

	public void setRevisions(Set<AuditRevision> revisions) {
		this.revisions = revisions;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("object", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.rows");
		serializer.exclude("revisions.objects");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
