package ro.roda.domainjson;

import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONSerializer;

@Configurable
public class AuditField implements Comparable<AuditField> {

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

	@Override
	public int compareTo(AuditField otherAuditField) {
		// TODO
		return auditfield.compareTo(otherAuditField.getAuditfield());
	}

	@Override
	public boolean equals(Object other) {
		// TODO
		if (other != null && other instanceof AuditField) {
			return new EqualsBuilder().append(this.getAuditfield(), ((AuditField) other).getAuditfield()).isEquals();
		} else {
			return false;
		}
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("auditfield", "auditvalue");

		return serializer.serialize(this);
	}
}
