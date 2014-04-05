package ro.roda.transformer;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;

@Configurable
public class AuditRevisionsDate extends JsonInfo {

	public static String toJsonArray(Collection<AuditRevisionsDate> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("date");

		serializer.transform(DATE_TRANSFORMER2, "date");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static Set<AuditRevisionsDate> findAllAuditRevisionsDates() {
		Set<AuditRevisionsDate> result = new HashSet<AuditRevisionsDate>();

		List<RodaRevisionEntity> rre = RodaRevisionEntity.findAllRodaRevisionEntities();

		for (RodaRevisionEntity revision : rre) {
			result.add(new AuditRevisionsDate(revision.getRevisionDate()));
		}

		return result;
	}

	private Date date;

	public AuditRevisionsDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("date");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
