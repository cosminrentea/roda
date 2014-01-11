package ro.roda.transformer;

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

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditRevisionsByDate extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsByDate> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("data", "nrrev", "lastrevision", "revisions");

		serializer.exclude("revisions.nrrows", "revisions.rows");

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

		/*
		 * RodaRevisionEntity revision =
		 * RodaRevisionEntity.findRodaRevisionEntity(id);
		 * 
		 * if (revision != null) { return new AuditRevisionsByObject(revision);
		 * } return null;
		 */

		return new AuditRevisionsByDate(date);
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");
	private static final DateTransformer DATE_TRANSFORMER2 = new DateTransformer("MM/dd/yyyy");

	private Date data;

	private Integer nrrev;

	private Date lastrevision;

	private Set<AuditRevision> revisions;

	public AuditRevisionsByDate(Date data, Integer nrRev, Date lastRevision, Set<AuditRevision> revisions) {
		onConstructRevisionsByDate(data, nrRev, lastRevision, revisions);
	}

	public AuditRevisionsByDate(Date date) {

		String[] auditedClasses = getAuditedClasses("ro.roda.domain");

		for (int i = 0; i < auditedClasses.length; i++) {
			String auditedClassName = auditedClasses[i];

			try {
				Class<?> auditedClass = Class.forName(auditedClassName);

				Method getAuditReaderMethod = null;

				try {
					getAuditReaderMethod = auditedClass.getMethod("getClassAuditReader");
				} catch (Exception e) {
					// TODO catch
				}

				Date lastRevision = null;

				if (getAuditReaderMethod != null) {
					AuditReader auditReader = (AuditReader) getAuditReaderMethod.invoke(null);

					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.DATE, 1);
					Date dateEnd = c.getTime();

					// get the revisions for the date
					AuditQuery queryRevisions = auditReader.createQuery().forRevisionsOfEntity(auditedClass, false,
							true);

					List<?> resultRevisions = queryRevisions.getResultList();

					Iterator<?> iteratorRevisions = resultRevisions.iterator();

					Map<Integer, AuditRevision> revisions = new TreeMap<Integer, AuditRevision>();
					while (iteratorRevisions.hasNext()) {

						Object o = iteratorRevisions.next();

						RodaRevisionEntity revision = (RodaRevisionEntity) ((Object[]) o)[1];

						// TODO: another solution would be to make a join query
						// (not supported by Hibernate Envers)
						if (revision.getRevisionDate().after(date) && revision.getRevisionDate().before(dateEnd)) {
							// add new revision, without any information about
							// the
							// revision rows or objects
							revisions.put(
									revision.getId(),
									new AuditRevision(revision.getId(), revision.getRevisionDate(), revision
											.getUsername(), null, null, null, null));

							if (lastRevision == null) {
								lastRevision = revision.getRevisionDate();
							} else {
								if (revision.getRevisionDate().after(lastRevision)) {
									lastRevision = revision.getRevisionDate();
								}
							}
						}
					}
					// TODO: get the userid
					onConstructRevisionsByDate(date, revisions.values().size(), lastRevision,
							new HashSet<AuditRevision>(revisions.values()));

				}

			} catch (Exception e) {
				// TODO
				System.out.println("Exception thrown when getting revision info. " + e.getMessage());
				// e.printStackTrace();
			}

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

		serializer.exclude("revisions.nrrows", "revisions.rows");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(DATE_TRANSFORMER2, "data");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
