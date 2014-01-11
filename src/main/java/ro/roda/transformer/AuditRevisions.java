package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Configurable
public class AuditRevisions extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisions> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("revision", "timestamp", "nrobjects", "objects");

		serializer.exclude("objects.nrrows", "objects.rows");
		serializer.include("objects.objectname");

		// serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new FieldNameTransformer("objname"),
		// "object.objname");
		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<AuditRevisions> findAllRevisions() {
		List<AuditRevisions> result = new ArrayList<AuditRevisions>();

		List<RodaRevisionEntity> revisions = RodaRevisionEntity.findAllRodaRevisionEntities();

		if (revisions != null && revisions.size() > 0) {

			Iterator<RodaRevisionEntity> revisionsIterator = revisions.iterator();
			while (revisionsIterator.hasNext()) {
				RodaRevisionEntity revision = (RodaRevisionEntity) revisionsIterator.next();
				result.add(new AuditRevisions(revision));
			}
		}

		return result;
	}

	public static AuditRevisions findRevision(Integer id) {
		RodaRevisionEntity revision = RodaRevisionEntity.findRodaRevisionEntity(id);

		if (revision != null) {
			return new AuditRevisions(revision);
		}
		return null;
	}

	private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	private Integer revision;

	private Date timestamp;

	private Integer nrobjects;

	private Set<AuditObject> objects;

	public AuditRevisions() {
		super();
	}

	public AuditRevisions(Integer revision, Date timestamp, Integer nrobj, Set<AuditObject> objects) {
		onConstructRevisions(revision, timestamp, nrobj, objects);
	}

	public AuditRevisions(RodaRevisionEntity revision) {

		Set<AuditObject> objects = new HashSet<AuditObject>();

		String[] auditedClasses = getAuditedClasses("ro.roda.domain");

		for (int i = 0; i < auditedClasses.length; i++) {
			String auditedClassName = auditedClasses[i];

			try {
				Class<?> auditedClass = Class.forName(auditedClassName);
				// Method getid = auditedClass.getMethod("getId");

				AuditQuery query = revision.getAuditReader().createQuery()
						.forEntitiesModifiedAtRevision(auditedClass, revision.getId());

				List<?> results = query.getResultList();

				if (results.size() > 0) {
					objects.add(new AuditObject(auditedClassName, results.size(), null));
				}
			} catch (Exception e) {
				// TODO
				System.out.println("Exception thrown when getting revision info. " + e.getMessage());
			}

		}

		/*
		 * AuditQuery query = revision.getAuditReader().createQuery()
		 * .forEntitiesModifiedAtRevision(CmsLayout.class, revision.getId());
		 * 
		 * @SuppressWarnings("unchecked") List<CmsLayout> results =
		 * (List<CmsLayout>) query.getResultList(); List<AuditObject> objects =
		 * new ArrayList<AuditObject>();
		 * 
		 * Iterator<CmsLayout> iterator = results.iterator();
		 * 
		 * while (iterator.hasNext()) { CmsLayout object = iterator.next();
		 * objects.add(new AuditObject(object.getClass().getSimpleName(), null,
		 * null)); }
		 */

		onConstructRevisions(revision.getId(), revision.getRevisionDate(), objects.size(), new HashSet<AuditObject>(
				objects));
	}

	private void onConstructRevisions(Integer revision, Date timestamp, Integer nrobj, Set<AuditObject> objects) {
		this.revision = revision;
		this.timestamp = timestamp;
		this.nrobjects = nrobj;
		this.objects = objects;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getNrobjects() {
		return nrobjects;
	}

	public void setNrobjects(Integer nrobj) {
		this.nrobjects = nrobj;
	}

	public Set<AuditObject> getObjects() {
		return objects;
	}

	public void setObjects(Set<AuditObject> objects) {
		this.objects = objects;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("revision", "timestamp", "nrobj", "objects");

		serializer.exclude("objects.nrrows", "objects.rows");
		serializer.include("objects.objname");

		// serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new FieldNameTransformer("objname"),
		// "object.objname");
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
