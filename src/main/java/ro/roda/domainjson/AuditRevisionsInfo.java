package ro.roda.domainjson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
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

		Set<AuditObject> objects = new HashSet<AuditObject>();

		String[] auditedClasses = findAuditedClasses("ro.roda.domain");

		for (int i = 0; i < auditedClasses.length; i++) {
			String auditedClassName = auditedClasses[i];

			try {
				Class<?> auditedClass = Class.forName(auditedClassName);
				Method getid = auditedClass.getMethod("getId");

				AuditQuery query = revision.getAuditReader().createQuery()
						.forEntitiesModifiedAtRevision(auditedClass, revision.getId());

				List<?> results = query.getResultList();

				Iterator<?> iterator = results.iterator();

				Set<AuditRow> rows = new HashSet<AuditRow>();
				while (iterator.hasNext()) {
					Object object = iterator.next();
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
							auditedFields.add(new AuditField(classField.getName(), getAuditedField.invoke(object)
									.toString()));
						} catch (Exception e) {

						}
					}

					AuditQuery queryRev = revision.getAuditReader().createQuery()
							.forRevisionsOfEntity(auditedClass, false, true).add(AuditEntity.id().eq(objectId));
					RevisionType revType = (RevisionType) ((Object[]) queryRev.getResultList().get(0))[2];
					rows.add(new AuditRow(objectId, revType != null ? revType.toString() : "", auditedFields.size(),
							auditedFields));

				}
				if (rows.size() > 0) {
					objects.add(new AuditObject(auditedClassName, rows.size(), rows));
				}
			} catch (Exception e) {
				// TODO
				System.out.println("Exception thrown when getting revision info. " + e.getMessage());
				// e.printStackTrace();
			}

		}

		// TODO: get the userid
		onConstructRevisions(revision.getId(), revision.getRevisionDate(), revision.getUsername(), null,
				objects.size(), objects);
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

	// @Override
	// public int compareTo(Revisions layoutList) {
	// System.out.println("Compare " + ((itemtype.equals("layout") ? "2" : "1")
	// + " " + name + " " + groupid)
	// + (layoutList.getItemtype().equals("layout") ? "2" : "1") + " " +
	// layoutList.getName() + " "
	// + layoutList.getGroupid());
	// return ((itemtype.equals("layout") ? "2" : "1") + " " + name + " " +
	// groupid).compareTo((layoutList
	// .getItemtype().equals("layout") ? "2" : "1")
	// + " "
	// + layoutList.getName()
	// + " "
	// + layoutList.getGroupid());
	// }

	// @Override
	// public int hashCode() {
	// return new HashCodeBuilder().append(itemtype == null ? 0 :
	// (itemtype.equals("layoutgroup") ? 1 : 2))
	// .append(groupid == null ? 0 :
	// groupid.intValue()).append(name).toHashCode();
	// }
	//
	// @Override
	// public boolean equals(Object other) {
	// if (other != null && other instanceof Revisions) {
	// return new EqualsBuilder().append(this.getItemtype(), ((Revisions)
	// other).getItemtype())
	// .append(this.getGroupid(), ((Revisions) other).getGroupid())
	// .append(this.getName(), ((Revisions) other).getName()).isEquals();
	// } else {
	// return false;
	// }
	// }

}
