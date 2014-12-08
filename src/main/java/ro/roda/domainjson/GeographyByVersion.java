package ro.roda.domainjson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.GeoVersion;
import ro.roda.domain.Geography;
import flexjson.JSONSerializer;

@Configurable
public class GeographyByVersion extends JsonInfo {

	public static String toJsonArray(Collection<GeographyByVersion> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("geographies.leaf", "geographies.name", "geographies.type");

		serializer.include("id", "geographies");

		return serializer.rootName("data").serialize(collection);
	}

	public static GeographyByVersion findGeographiesByVersion(Integer id) {
		GeoVersion version = GeoVersion.findGeoVersion(id);
		Set<Geography> geographies = version.getGeographies();
		Set<GeographyInfo> geoInfos = new HashSet<GeographyInfo>();

		Iterator<Geography> itGeography = geographies.iterator();

		while (itGeography.hasNext()) {
			geoInfos.add(new GeographyInfo(itGeography.next()));
		}

		return new GeographyByVersion(version.getId(), geoInfos);
	}

	private Integer id;

	private Set<GeographyInfo> geographies;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<GeographyInfo> getGeographies() {
		return geographies;
	}

	public void setGeographies(Set<GeographyInfo> geographies) {
		this.geographies = geographies;
	}

	public GeographyByVersion(Integer id) {
		this.id = id;
		geographies = new HashSet<GeographyInfo>();
	}

	public GeographyByVersion(Integer id, Set<GeographyInfo> geographies) {
		this.id = id;
		this.geographies = geographies;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("geographies.leaf", "geographies.name", "geographies.type");

		serializer.include("id", "geographies");

		return serializer.rootName("data").serialize(this);
	}
}
