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
}
