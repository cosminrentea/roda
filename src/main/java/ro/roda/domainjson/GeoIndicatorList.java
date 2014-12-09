package ro.roda.domainjson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.GeoData;
import ro.roda.domain.Geography;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class GeoIndicatorList extends JsonInfo implements Comparable<GeoIndicatorList> {

	public static String toJsonArray(Collection<GeoIndicatorList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "name", "type", "leaf");
		serializer.include("id", "indicators");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static GeoIndicatorList findGeoIndicatorList(Integer geographyId) {
		GeoIndicatorList result = null;

		Geography geo = Geography.findGeography(geographyId);

		if (geo != null) {

			Set<GeoData> geoData = geo.getGeoData();
			Set<GeoIndicator> indicators = new HashSet<GeoIndicator>();

			if (geoData != null) {
				Iterator<GeoData> geoIterator = geoData.iterator();
				while (geoIterator.hasNext()) {
					GeoData gd = (GeoData) geoIterator.next();

					indicators.add(new GeoIndicator(gd.getDatatypesId()));
				}
			}

			result = new GeoIndicatorList(geographyId, indicators);
		}

		return result;
	}

	private Integer id;

	private Set<GeoIndicator> indicators;

	public GeoIndicatorList(Integer id, Set<GeoIndicator> indicators) {
		this.id = id;
		this.indicators = indicators;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<GeoIndicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(Set<GeoIndicator> indicators) {
		this.indicators = indicators;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "name", "type", "leaf");
		serializer.include("id", "indicators");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(GeoIndicatorList versionList) {
		return (id.compareTo(versionList.getId()));
	}

	@Override
	public boolean equals(Object other) {
		// TODO
		return false;
	}

}
