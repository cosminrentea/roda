package ro.roda.domainjson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.GeoData;
import ro.roda.domain.GeoDataPK;
import ro.roda.domain.GeoDatatype;
import ro.roda.domain.Geography;
import flexjson.JSONSerializer;

@Configurable
public class GeoIndicatorValue extends JsonInfo implements Comparable<GeoIndicatorValue> {

	public static String toJsonArray(Collection<GeoIndicatorValue> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type", "leaf");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static GeoIndicatorValue findGeoIndicatorValue(Integer geographyId, Integer dataTypeId) {
		GeoIndicatorValue result = null;

		Geography geo = Geography.findGeography(geographyId);
		GeoDatatype indicator = GeoDatatype.findGeoDatatype(dataTypeId);

		if (geo != null && indicator != null) {

			GeoData gd = GeoData.findGeoData(new GeoDataPK(dataTypeId, geographyId));

			if (gd != null) {
				result = new GeoIndicatorValue(geographyId, indicator.getCod(), gd.getValue());
			}
		}

		return result;
	}

	private Integer geographyId;

	private String indicatorCode;

	private Double value;

	public GeoIndicatorValue(Integer geographyId, String indicatorCode, Double value) {
		this.geographyId = geographyId;
		this.indicatorCode = indicatorCode;
		this.value = value;
	}

	public Integer getGeographyId() {
		return geographyId;
	}

	public void setGeographyId(Integer geographyId) {
		this.geographyId = geographyId;
	}

	public String getIndicatorCode() {
		return indicatorCode;
	}

	public void setIndicatorCode(String indicatorCode) {
		this.indicatorCode = indicatorCode;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type", "leaf");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(GeoIndicatorValue geoIndicatorValue) {
		if (geographyId != geoIndicatorValue.getGeographyId()) {
			return (geographyId.compareTo(geoIndicatorValue.getGeographyId()));
		} else {
			return (indicatorCode.compareTo(geoIndicatorValue.getIndicatorCode()));
		}
	}

	@Override
	public boolean equals(Object other) {
		// TODO
		return false;
	}

}
