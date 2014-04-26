package ro.roda.domainjson;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONSerializer;

@Configurable
public class AuditObject {

	public static String toJsonArray(Collection<AuditObject> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("objname", "nrrows", "rows", "rows.auditfields");
		// serializer.include("rows.id", "rows.modtype", "rows.nrfields",
		// "rows.auditfields");

		return serializer.serialize(collection);
	}

	private String objname;

	private Integer nrrows;

	private Set<AuditRow> rows;

	public AuditObject(String objname, Integer nrrows, Set<AuditRow> auditrows) {
		this.objname = objname;
		this.nrrows = nrrows;
		this.rows = auditrows;
	}

	public AuditObject(AuditObject auditObject) {
		this(auditObject.getObjname(), auditObject.getRows().size(), auditObject.getRows());
	}

	public String getObjname() {
		return objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	public Integer getNrrows() {
		return nrrows;
	}

	public void setNrrows(Integer nrrows) {
		this.nrrows = nrrows;
	}

	public Set<AuditRow> getRows() {
		return rows;
	}

	public void setRows(Set<AuditRow> rows) {
		this.rows = rows;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("objname", "nrrows", "rows", "rows.auditfields");
		// serializer.include("rows.id", "rows.modtype", "rows.nrfields",
		// "rows.auditfields");

		return serializer.serialize(this);
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
