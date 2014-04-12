package ro.roda.transformer;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONSerializer;

@Configurable
public class AuditRevision implements Comparable<AuditRevision> {

	public static String toJsonArray(Collection<AuditRevision> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("revision", "timestamp", "username", "userid", "nrrows", "rows");
		serializer.include("rows.auditfields");

		// serializer.include("rows.id", "rows.modtype", "rows.nrfields",
		// "rows.auditfields");

		return serializer.serialize(collection);
	}

	private Integer revision;

	private Date timestamp;

	private String username;

	private Integer userid;

	private Integer nrrows;

	// in practice, an AuditRevision has either a set of AuditRow or a set of
	// AuditObject (exclusive or); this could be implemented via the superclass
	private Set<AuditRow> rows;

	private Set<AuditObject> objects;

	public AuditRevision(Integer revision, Date timestamp, String username, Integer userid, Integer nrrows,
			Set<AuditRow> auditrows, Set<AuditObject> auditobjects) {
		this.revision = revision;
		this.timestamp = timestamp;
		this.username = username;
		this.userid = userid;
		this.nrrows = nrrows;
		this.rows = auditrows;
		this.objects = auditobjects;
	}

	public AuditRevision(AuditRevision auditRevision) {
		this(auditRevision.getRevision(), auditRevision.getTimestamp(), auditRevision.getUsername(), auditRevision
				.getUserid(), auditRevision.getRows().size(), auditRevision.getRows(), auditRevision.getObjects());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNrrows() {
		return nrrows;
	}

	public void setNrrows(Integer nrrows) {
		this.nrrows = nrrows;
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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Set<AuditRow> getRows() {
		return rows;
	}

	public void setRows(Set<AuditRow> rows) {
		this.rows = rows;
	}

	public Set<AuditObject> getObjects() {
		return objects;
	}

	public void setObjects(Set<AuditObject> objects) {
		this.objects = objects;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("revision", "timestamp", "username", "userid", "nrrows", "rows");
		serializer.include("rows.auditfields");

		// serializer.include("rows.id", "rows.modtype", "rows.nrfields",
		// "rows.auditfields");

		return serializer.serialize(this);
	}

	@Override
	public int compareTo(AuditRevision revision) {
		return this.revision.compareTo(revision.getRevision());
	}
	//
	// @Override
	// public int hashCode() {
	// return new HashCodeBuilder().append(itemtype == null ? 0 :
	// (itemtype.equals("layoutgroup") ? 1 : 2))
	// .append(groupid == null ? 0 :
	// groupid.intValue()).append(name).toHashCode();
	// }
	//
	// //
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
