package ro.roda.domainjson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONSerializer;

@Configurable
public class AuditField {

	public static String toJsonArray(Collection<AuditField> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("auditfield", "auditvalue");

		return serializer.serialize(collection);
	}

	private String auditfield;

	private Object auditvalue;

	public AuditField(String auditfield, Object auditvalue) {
		this.auditfield = auditfield;
		this.auditvalue = auditvalue;
	}

	public AuditField(AuditField auditFields) {
		this(auditFields.getAuditfield(), auditFields.getAuditvalue());
	}

	public String getAuditfield() {
		return auditfield;
	}

	public void setAuditfield(String auditfield) {
		this.auditfield = auditfield;
	}

	public Object getAuditvalue() {
		return auditvalue;
	}

	public void setAuditvalue(Object auditvalue) {
		this.auditvalue = auditvalue;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("auditfield", "auditvalue");

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
