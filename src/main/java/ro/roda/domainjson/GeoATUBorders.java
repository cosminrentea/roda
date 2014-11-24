package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.GeoDraw;
import ro.roda.domain.Geography;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class GeoATUBorders extends JsonInfo implements Comparable<GeoATUBorders> {

	public static String toJsonArray(Collection<GeoATUBorders> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "siruta", "borders");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<GeoATUBorders> findAllGeoCitiesBorders() {
		List<GeoATUBorders> result = new ArrayList<GeoATUBorders>();

		List<Geography> geographies = Geography.findAllGeographys();

		if (geographies != null && geographies.size() > 0) {

			Iterator<Geography> geographiesIt = geographies.iterator();
			while (geographiesIt.hasNext()) {
				Geography geography = (Geography) geographiesIt.next();

				result.add(new GeoATUBorders(geography));
			}
		}

		return result;
	}

	public static GeoATUBorders findGeoCityBorder(Integer id) {
		Geography geography = Geography.findGeography(id);

		if (geography != null) {
			return new GeoATUBorders(geography);
		}
		return null;
	}

	private static String computeFullBorders(Geography geography) {
		List<GeoDraw> geoDraws = GeoDraw.findAllGeoDrawsForGeography(geography);
		Iterator<GeoDraw> itGeoDraws = geoDraws.iterator();

		StringBuilder sb = new StringBuilder();

		while (itGeoDraws.hasNext()) {
			sb.append(itGeoDraws.next().getGeomarginsId().getMpath());
			sb.append(",");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	private Integer id;

	private String name;

	private Integer siruta;

	private String border;

	public GeoATUBorders(Integer id, String name, Integer siruta, String border) {
		this.id = id;
		this.name = name;
		this.siruta = siruta;
		this.border = border;
	}

	public GeoATUBorders(Geography geography) {
		this(geography.getId(), geography.getDenloc(), geography.getSiruta(), computeFullBorders(geography));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSiruta() {
		return siruta;
	}

	public void setSiruta(Integer siruta) {
		this.siruta = siruta;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "siruta", "borders");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(GeoATUBorders geoCityBorder) {
		return (name + " " + siruta).compareTo(geoCityBorder.getName() + " " + geoCityBorder.getSiruta());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(siruta == null ? 0 : siruta.intValue()).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof GeoATUBorders) {
			return new EqualsBuilder().append(this.getName(), ((GeoATUBorders) other).getName())
					.append(this.getSiruta(), ((GeoATUBorders) other).getSiruta())
					.append(this.getBorder(), ((GeoATUBorders) other).getBorder()).isEquals();
		} else {
			return false;
		}
	}

}
