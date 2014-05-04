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
}
