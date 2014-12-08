package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.GeoVersion;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class GeoVersionList extends JsonInfo implements Comparable<GeoVersionList> {

	public static String toJsonArray(Collection<GeoVersionList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "startdate", "enddate", "notes");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<GeoVersionList> findAllGeoVersionLists() {
		List<GeoVersionList> result = new ArrayList<GeoVersionList>();

		List<GeoVersion> versions = GeoVersion.findAllGeoVersions();

		if (versions != null && versions.size() > 0) {

			Iterator<GeoVersion> versionsIterator = versions.iterator();
			while (versionsIterator.hasNext()) {
				GeoVersion version = (GeoVersion) versionsIterator.next();

				result.add(new GeoVersionList(version));
			}
		}

		return result;
	}

	public static GeoVersionList findGeoVersionList(Integer id) {
		GeoVersion version = GeoVersion.findGeoVersion(id);

		if (version != null) {
			return new GeoVersionList(version);
		}

		return null;
	}

	private Integer id;

	private Calendar startdate;

	private Calendar enddate;

	private String notes;

	public GeoVersionList(Integer id, Calendar startDate, Calendar endDate, String notes) {
		this.id = id;
		this.startdate = startDate;
		this.enddate = endDate;
		this.notes = notes;
	}

	public GeoVersionList(GeoVersion version) {
		this(version.getId(), version.getStartdate(), version.getEnddate(), version.getNotes());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getStartdate() {
		return startdate;
	}

	public void setStardate(Calendar startdate) {
		this.startdate = startdate;
	}

	public Calendar getEnddate() {
		return enddate;
	}

	public void setEnddate(Calendar enddate) {
		this.enddate = enddate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(GeoVersionList versionList) {
		return (id.compareTo(versionList.getId()));
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id.intValue()).append(startdate.toString()).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof GeoVersionList) {
			return new EqualsBuilder().append(this.getStartdate(), ((GeoVersionList) other).getStartdate())
					.append(this.getEnddate(), ((GeoVersionList) other).getEnddate())
					.append(this.getNotes(), ((GeoVersionList) other).getNotes()).isEquals();
		} else {
			return false;
		}
	}

}
