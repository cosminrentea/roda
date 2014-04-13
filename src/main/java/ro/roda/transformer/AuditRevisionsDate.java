package ro.roda.transformer;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.JSONSerializer;

@Configurable
public class AuditRevisionsDate extends JsonInfo implements Comparable<AuditRevisionsDate> {

	public static String toJsonArray(Collection<AuditRevisionsDate> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "id", "name");
		serializer.include("date");

		serializer.transform(DATE_TRANSFORMER2, "date");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static Set<AuditRevisionsDate> findAllAuditRevisionsDates() {
		Set<AuditRevisionsDate> result = new TreeSet<AuditRevisionsDate>();

		List<RodaRevisionEntity> rre = RodaRevisionEntity.findAllRodaRevisionEntities();

		for (RodaRevisionEntity revision : rre) {
			result.add(new AuditRevisionsDate(revision.getRevisionDate()));
		}

		return result;
	}

	private Date date;

	public AuditRevisionsDate(Date date) {
		this.date = DateUtils.truncate(date, Calendar.DATE);
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

	@Override
	public int compareTo(AuditRevisionsDate o) {
		return this.date.compareTo(o.date);
	}
}
