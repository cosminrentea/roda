package ro.roda.domainjson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.envers.AuditReader;
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

		serializer.include("revisions.rows", "revisions.rows.auditfields");
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

				if (getAuditReaderMethod != null) {
					AuditReader auditReader = (AuditReader) getAuditReaderMethod.invoke(null);

					// get the revisions for the object

					// TODO find why the following query returns duplicates for
					// a revision (i.e. it returns a revision as many times as
					// the number of rows affected by that revision);
					// this is no longer a problem, as a TreeSet is used instead
					// of a HashSet, but there might exist a significant
					// processing time in the iterations below
					AuditQuery queryRevisions = auditReader.createQuery().forRevisionsOfEntity(auditedClass, false,
							true);

					List<?> resultRevisions = queryRevisions.getResultList();

					Iterator<?> iteratorRevisions = resultRevisions.iterator();

					Set<AuditRevision> revisions = new TreeSet<AuditRevision>();
					while (iteratorRevisions.hasNext()) {

						Object o = iteratorRevisions.next();

						RodaRevisionEntity revision = (RodaRevisionEntity) ((Object[]) o)[1];

						Set<AuditRow> auditRows = findModifiedEntities(auditedClass, revision);

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

		serializer.include("revisions.rows", "revisions.rows.auditfields");
		serializer.exclude("revisions.objects");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
