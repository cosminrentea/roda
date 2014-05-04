package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditRevisionsInfo extends AuditRevisions {

	public static String toJsonArr(Collection<AuditRevisionsInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("revision", "timestamp", "username", "userid", "nrobjects", "objects");

		// serializer.exclude("objects.id", "objects.type");
		serializer.include("objects.objname", "objects.nrrows", "objects.rows", "objects.rows.auditfields");

		// serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new FieldNameTransformer("objname"),
		// "objects.name");
		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditRevisionsInfo> findAllRevisionsInfo() {
		List<AuditRevisionsInfo> result = new ArrayList<AuditRevisionsInfo>();

		List<RodaRevisionEntity> revisions = RodaRevisionEntity.findAllRodaRevisionEntities();

		if (revisions != null && revisions.size() > 0) {

			Iterator<RodaRevisionEntity> revisionsIterator = revisions.iterator();
			while (revisionsIterator.hasNext()) {
				RodaRevisionEntity revision = (RodaRevisionEntity) revisionsIterator.next();
				result.add(new AuditRevisionsInfo(revision));
			}
		}

		return result;
	}

	public static AuditRevisionsInfo findRevisionInfo(Integer id) {
		RodaRevisionEntity revision = RodaRevisionEntity.findRodaRevisionEntity(id);

		if (revision != null) {
			return new AuditRevisionsInfo(revision);
		}
		return null;
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	private String username;

	private Integer userid;

	public AuditRevisionsInfo(Integer revision, Date timestamp, String username, Integer userid, Integer nrobj,
			Set<AuditObject> objects) {
		super(revision, timestamp, nrobj, objects);
		this.username = username;
		this.userid = userid;

	}

	public AuditRevisionsInfo(RodaRevisionEntity revision) {

		Set<AuditObject> objects = new TreeSet<AuditObject>();

		String[] auditedClasses = findAuditedClasses("ro.roda.domain");

		for (int i = 0; i < auditedClasses.length; i++) {
			String auditedClassName = auditedClasses[i];

			try {
				Class<?> auditedClass = Class.forName(auditedClassName);

				Set<AuditRow> rows = findModifiedEntities(auditedClass, revision);

				if (rows.size() > 0) {
					objects.add(new AuditObject(auditedClassName, rows.size(), rows));
				}
			} catch (Exception e) {
				// TODO
				System.out.println("Exception thrown when getting revision info. " + e.getMessage());
				// e.printStackTrace();
			}

		}

		onConstructRevisions(revision.getId(), revision.getRevisionDate(), revision.getUsername(),
				revision.getUserid(), objects.size(), objects);
	}

	private void onConstructRevisions(Integer revision, Date timestamp, String username, Integer userid, Integer nrobj,
			Set<AuditObject> objects) {

		setRevision(revision);
		setTimestamp(timestamp);
		setNrobjects(nrobj);
		setObjects(objects);

		this.username = username;
		this.userid = userid;
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

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("revision", "timestamp", "username", "userid", "nrobjects", "objects");

		// serializer.exclude("objects.id", "objects.type");
		// serializer.include("objects.objname");
		serializer.include("objects.objname", "objects.nrrows", "objects.rows", "objects.rows.auditfields");

		// serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new FieldNameTransformer("objname"),
		// "objects.name");
		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
