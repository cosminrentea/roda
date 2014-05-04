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

}
