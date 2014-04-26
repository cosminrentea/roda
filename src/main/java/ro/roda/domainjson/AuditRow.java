package ro.roda.domainjson;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class AuditRow {

	public static String toJsonArray(Collection<AuditRow> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "modtype", "nrfields", "auditfields");
		// serializer.include("auditfields.auditfield",
		// "auditfields.auditvalue"));

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return serializer.serialize(collection);
	}

	private Integer id;

	private String modtype;

	private Integer nrfields;

	private Set<AuditField> auditfields;

	public AuditRow(Integer id, String modtype, Integer nrfields, Set<AuditField> auditfields) {
		this.id = id;
		this.modtype = modtype;
		this.nrfields = nrfields;
		this.auditfields = auditfields;
	}

	public AuditRow(AuditRow auditRow) {
		this(auditRow.getId(), auditRow.getModtype(), auditRow.getAuditfields().size(), auditRow.getAuditfields());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModtype() {
		return modtype;
	}

	public void setModtype(String modtype) {
		this.modtype = modtype;
	}

	public Integer getNrfields() {
		return nrfields;
	}

	public void setNrfields(Integer nrfields) {
		this.nrfields = nrfields;
	}

	public Set<AuditField> getAuditfields() {
		return auditfields;
	}

	public void setAuditfields(Set<AuditField> auditfields) {
		this.auditfields = auditfields;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "modtype", "nrfields", "auditfields");
		// serializer.include("auditfields.auditfield",
		// "auditfields.auditvalue"));

		serializer.transform(new FieldNameTransformer("indice"), "id");

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
