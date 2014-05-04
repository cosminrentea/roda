package ro.roda.domainjson;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
import ro.roda.domain.Users;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditRevisionsByUsername extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsByUsername> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("username", "userid", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.objects", "revisions.objects.rows", "revisions.objects.rows.auditfields");
		serializer.exclude("revisions.username", "revisions.userid", "revisions.rows");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(new FieldNameTransformer("nrobj"), "revisions.nrrows");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditRevisionsByUsername> findAllRevisionsByUsername() {
		List<AuditRevisionsByUsername> result = new ArrayList<AuditRevisionsByUsername>();

		// TODO: does it make any sense?

		return result;
	}

	public static AuditRevisionsByUsername findRevisionsByUsername(String username) {

		// TODO: validate the username

		return new AuditRevisionsByUsername(username);
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	private String username;

	private Integer userid;

	private Integer nrrev;

	private Date lastrevision;

	private Set<AuditRevision> revisions;

	public AuditRevisionsByUsername(String username, Integer userid, Integer nrRev, Date lastRevision,
			Set<AuditRevision> revisions) {
		onConstructRevisionsByUsername(username, userid, nrRev, lastRevision, revisions);
	}

	@SuppressWarnings("unchecked")
	public AuditRevisionsByUsername(String userName) {

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

						if (revision.getUsername().equals(userName)) {

							Set<AuditRow> auditRows = findModifiedEntities(auditedClass, revision);

							if (!objectsByRevision.containsKey(revision.getId())) {
								objectsByRevision.put(revision.getId(), new Object[] { revision,
										new HashSet<AuditObject>() });
							}

							((HashSet<AuditObject>) objectsByRevision.get(revision.getId())[1]).add(new AuditObject(
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

			onConstructRevisionsByUsername(userName, Users.findUsersesByUsernameLike(userName).getFirstResult(),
					revisions.size(), lastRevision, revisions);
		}
	}

	private void onConstructRevisionsByUsername(String username, Integer userid, Integer nrRev, Date lastRevision,
			Set<AuditRevision> revisions) {
		this.username = username;
		this.userid = userid;
		this.nrrev = nrRev;
		this.lastrevision = lastRevision;
		this.revisions = revisions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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
		serializer.include("username", "userid", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.objects", "revisions.objects.rows", "revisions.objects.rows.auditfields");
		serializer.exclude("revisions.username", "revisions.userid", "revisions.rows");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(new FieldNameTransformer("nrobj"), "revisions.nrrows");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
