package ro.roda.transformer;

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
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditSimplifiedRevisionsByUsername extends JsonInfo {

	public static String toJsonArray(Collection<AuditSimplifiedRevisionsByUsername> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("username", "userid", "nrrev", "lastrevision", "revisions");

		serializer.include("revisions.revision", "revisions.timestamp");
		serializer.exclude("revisions.nrrows", "revisions.objects", "revisions.username", "revisions.userid",
				"revisions.rows");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditSimplifiedRevisionsByUsername> findAllAuditSimplifiedRevisionsByUsername() {
		List<AuditSimplifiedRevisionsByUsername> result = new ArrayList<AuditSimplifiedRevisionsByUsername>();

		List<Users> users = Users.findAllUserses();
		Iterator<Users> usersIterator = users.iterator();

		while (usersIterator.hasNext()) {
			result.add(new AuditSimplifiedRevisionsByUsername(usersIterator.next().getUsername()));
		}

		result.add(new AuditSimplifiedRevisionsByUsername("anonymousUser"));

		return result;
	}

	public static AuditSimplifiedRevisionsByUsername findAuditSimplifiedRevisionsByUsername(String username) {

		// TODO: validate the username

		return new AuditSimplifiedRevisionsByUsername(username);
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	private String username;

	private Integer userid;

	private Integer nrrev;

	private Date lastrevision;

	private Set<AuditRevision> revisions;

	public AuditSimplifiedRevisionsByUsername(String username, Integer userid, Integer nrRev, Date lastRevision,
			Set<AuditRevision> revisions) {
		onConstructRevisionsByUsername(username, userid, nrRev, lastRevision, revisions);
	}

	@SuppressWarnings("unchecked")
	public AuditSimplifiedRevisionsByUsername(String userName) {

		String[] auditedClasses = getAuditedClasses("ro.roda.domain");
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

					Method getId = auditedClass.getMethod("getId");

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
							if (!objectsByRevision.containsKey(revision.getId())) {
								objectsByRevision.put(revision.getId(), new Object[] { revision,
										new HashSet<AuditObject>() });
							}

							((HashSet<AuditObject>) objectsByRevision.get(revision.getId())[1]).add(new AuditObject(
									auditedClassName, null, null));

						}

					}
					System.out.println();

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

				revisions.add(new AuditRevision(key, revision.getRevisionDate(), null, null, null, null, null));

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
		onConstructRevisionsByUsername(userName, null, revisions.size(), lastRevision, revisions);
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

		serializer.include("revisions.revision", "revisions.timestamp");
		serializer.exclude("revisions.nrrows", "revisions.objects", "revisions.username", "revisions.userid",
				"revisions.rows");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
