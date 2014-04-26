package ro.roda.domainjson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditSimplifiedRevisionsByObject extends JsonInfo {

	public static String toJsonArray(Collection<AuditSimplifiedRevisionsByObject> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("object", "id", "nrrev", "lastrevision", "revisions");

		serializer.exclude("revisions.nrrows", "revisions.rows");
		serializer.exclude("revisions.objects");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditSimplifiedRevisionsByObject> findAllAuditSimplifiedRevisionsByObject() {
		List<AuditSimplifiedRevisionsByObject> result = new ArrayList<AuditSimplifiedRevisionsByObject>();

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

	public static List<AuditSimplifiedRevisionsByObject> findAllAuditSimplifiedRevisionsByObject(String object) {
		List<AuditSimplifiedRevisionsByObject> result = new ArrayList<AuditSimplifiedRevisionsByObject>();

		try {
			Class<?> auditedClass = Class.forName("ro.roda.domain." + object);

			Method findAllMethod = null;

			findAllMethod = auditedClass.getMethod("findAll" + object
					+ (object.charAt(object.length() - 1) == 's' ? "es" : "s"));

			List<?> allObjects = (List<?>) findAllMethod.invoke(null);
			Iterator<?> allObjectsIterator = allObjects.iterator();

			Method getId = auditedClass.getMethod("getId");

			while (allObjectsIterator.hasNext()) {
				Object o = allObjectsIterator.next();
				Integer id = Integer.parseInt(getId.invoke(o).toString());
				AuditSimplifiedRevisionsByObject auditSimplifiedRevisionsByObject = findAuditSimplifiedRevisionsByObject(
						object, id);
				if (auditSimplifiedRevisionsByObject != null && auditSimplifiedRevisionsByObject.getRevisions() != null
						&& auditSimplifiedRevisionsByObject.getRevisions().size() > 0) {
					result.add(auditSimplifiedRevisionsByObject);
				}
			}

		} catch (Exception e) {
			// TODO catch
			System.out.println("Error in findAllAuditSimplifiedRevisionsByObject(String object)");
			// e.printStackTrace();
		}

		return result;
	}

	public static AuditSimplifiedRevisionsByObject findAuditSimplifiedRevisionsByObject(String object, Integer id) {

		return new AuditSimplifiedRevisionsByObject(object, id);
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	private String object;

	private Integer id;

	private Integer nrrev;

	private Date lastrevision;

	private Set<AuditRevision> revisions;

	public AuditSimplifiedRevisionsByObject(String object, Integer nrRev, Date lastRevision,
			Set<AuditRevision> revisions) {
		onConstructRevisionsByObject(object, nrRev, lastRevision, revisions);
	}

	public AuditSimplifiedRevisionsByObject(String objectName, Integer id) {

		try {
			Class<?> auditedClass = Class.forName("ro.roda.domain." + objectName);

			Integer nrRev = null;
			Date lastRevision = null;

			if (auditedClass != null) {
				Method getAuditReaderMethod = auditedClass.getMethod("getClassAuditReader");

				if (getAuditReaderMethod != null) {
					AuditReader auditReader = (AuditReader) getAuditReaderMethod.invoke(null);

					// get the revisions for the object
					AuditQuery queryRevisions = auditReader.createQuery().forRevisionsOfEntity(auditedClass, false,
							true);

					if (id != null) {
						queryRevisions = queryRevisions.add(AuditEntity.id().eq(id));
						this.setId(id);
					}

					List<?> resultRevisions = queryRevisions.getResultList();

					if (resultRevisions != null && resultRevisions.size() > 0) {
						Iterator<?> iteratorRevisions = resultRevisions.iterator();

						Set<AuditRevision> revisions = new HashSet<AuditRevision>();
						while (iteratorRevisions.hasNext()) {

							Object o = iteratorRevisions.next();

							RodaRevisionEntity revision = (RodaRevisionEntity) ((Object[]) o)[1];

							// TODO: get the correct userid
							revisions.add(new AuditRevision(revision.getId(), revision.getRevisionDate(), revision
									.getUsername(), null, null, null, null));

							if (lastRevision == null) {
								lastRevision = revision.getRevisionDate();
							} else {
								if (revision.getRevisionDate().after(lastRevision)) {
									lastRevision = revision.getRevisionDate();
								}
							}
							// }

						}

						nrRev = revisions.size();

						onConstructRevisionsByObject(objectName, nrRev, lastRevision, revisions);
					}
				}
			}
		} catch (Exception e) {
			// TODO
			System.out.println("Exception thrown when getting revision info. " + e.getMessage());
			e.printStackTrace();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("object", "id", "nrrev", "lastrevision", "revisions");

		serializer.exclude("revisions.nrrows", "revisions.rows");
		serializer.exclude("revisions.objects");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
