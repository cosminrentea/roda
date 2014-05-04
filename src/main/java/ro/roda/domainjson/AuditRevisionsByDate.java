package ro.roda.domainjson;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class AuditRevisionsByDate extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsByDate> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("data", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.objects", "revisions.objects.rows", "revisions.objects.rows.auditfields");
		serializer.include("revisions.username", "revisions.userid");
		serializer.exclude("revisions.rows");

		serializer.transform(new FieldNameTransformer("nrobj"), "revisions.nrrows");
		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(DATE_TRANSFORMER2, "data");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditRevisionsByDate> findAllRevisionsByDate() {
		List<AuditRevisionsByDate> result = new ArrayList<AuditRevisionsByDate>();

		// TODO: is it necessary?

		return result;
	}

	public static AuditRevisionsByDate findRevisionsByDate(Date date) {
		return new AuditRevisionsByDate(date);
	}

	private Date data;

	private Integer nrrev;

	private Date lastrevision;

	private Set<AuditRevision> revisions;

	public AuditRevisionsByDate(Date data, Integer nrRev, Date lastRevision, Set<AuditRevision> revisions) {
		onConstructRevisionsByDate(data, nrRev, lastRevision, revisions);
	}

	@SuppressWarnings("unchecked")
	public AuditRevisionsByDate(Date date) {

		String[] auditedClasses = findAuditedClasses("ro.roda.domain");
		Map<Integer, Object[]> objectsByRevision = new TreeMap<Integer, Object[]>();

		for (int i = 0; i < auditedClasses.length; i++) {
			String auditedClassName = auditedClasses[i];

			try {
				Class<?> auditedClass = Class.forName(auditedClassName);

				Method getAuditReaderMethod = null;
				try {
					getAuditReaderMethod = auditedClass.getMethod("getClassAuditReader");
					System.out.println("Audit class: " + auditedClassName);
				} catch (Exception e) {
					// TODO catch
				}

				if (getAuditReaderMethod != null) {
					AuditReader auditReader = (AuditReader) getAuditReaderMethod.invoke(null);

					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.DATE, 1);
					Date dateEnd = c.getTime();

					// get the revisions for the object
					AuditQuery queryRevisions = auditReader.createQuery().forRevisionsOfEntity(auditedClass, false,
							true);
					Set<Integer> usedRevisionIds = new HashSet<Integer>();

					List<?> resultRevisions = queryRevisions.getResultList();
					Iterator<?> iteratorRevisions = resultRevisions.iterator();

					while (iteratorRevisions.hasNext()) {

						Object o = iteratorRevisions.next();
						RodaRevisionEntity revision = (RodaRevisionEntity) ((Object[]) o)[1];

						// workaround for the following situation: the query
						// returns the revisions of the related classes, which
						// will also be retrieved subsequently, leading to
						// duplicates in the result
						if (usedRevisionIds.contains(revision.getId())) {
							continue;
						}
						usedRevisionIds.add(revision.getId());

						if (revision.getRevisionDate().equals(date) || revision.getRevisionDate().after(date)
								&& revision.getRevisionDate().before(dateEnd)) {

							Set<AuditRow> auditRows = findModifiedEntities(auditedClass, revision);

							if (!objectsByRevision.containsKey(revision.getId())) {
								objectsByRevision.put(revision.getId(), new Object[] { revision,
										new TreeSet<AuditObject>() });
							}

							((TreeSet<AuditObject>) objectsByRevision.get(revision.getId())[1]).add(new AuditObject(
									auditedClassName, auditRows.size(), auditRows));
						}
					}
				}
			} catch (Exception e) {
				// TODO
				System.out.println("Exception thrown when getting revision info. " + e.getMessage());
				// e.printStackTrace();
			}

		}

		Set<AuditRevision> revisions = new HashSet<AuditRevision>();
		Date lastRevision = null;

		if (objectsByRevision.size() > 0) {

			Set<Integer> keys = objectsByRevision.keySet();
			Iterator<Integer> keysIterator = keys.iterator();

			while (keysIterator.hasNext()) {

				Integer key = keysIterator.next();
				RodaRevisionEntity revision = (RodaRevisionEntity) objectsByRevision.get(key)[0];

				Set<AuditObject> objects = (Set<AuditObject>) objectsByRevision.get(key)[1];

				revisions.add(new AuditRevision(key, revision.getRevisionDate(), null, null, objects.size(), null,
						objects));

				if (lastRevision == null) {
					lastRevision = revision.getRevisionDate();
				} else {
					if (revision.getRevisionDate().after(lastRevision)) {
						lastRevision = revision.getRevisionDate();
					}
				}
			}

			onConstructRevisionsByDate(date, revisions.size(), lastRevision, revisions);
		}
	}

	private void onConstructRevisionsByDate(Date data, Integer nrRev, Date lastRevision, Set<AuditRevision> revisions) {
		this.data = data;
		this.nrrev = nrRev;
		this.lastrevision = lastRevision;
		this.revisions = revisions;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		serializer.include("data", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.objects", "revisions.objects.rows", "revisions.objects.rows.auditfields");
		serializer.include("revisions.username", "revisions.userid");
		serializer.exclude("revisions.rows");

		serializer.transform(new FieldNameTransformer("nrobj"), "revisions.nrrows");
		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(DATE_TRANSFORMER2, "data");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
