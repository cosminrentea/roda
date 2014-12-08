package ro.roda.domainjson;

import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.GeoDatatype;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class GeoIndicator extends JsonInfo implements Comparable<GeoIndicator> {

	public static String toJsonArray(Collection<GeoIndicator> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "cod");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	private Integer id;

	private String cod;

	private String name;

	public GeoIndicator(Integer id, String cod, String name) {
		this.id = id;
		this.cod = cod;
		this.name = name;
	}

	public GeoIndicator(GeoDatatype geoDatatype) {
		this(geoDatatype.getId(), geoDatatype.getCod(), geoDatatype.getName());
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

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "cod");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(GeoIndicator versionList) {
		return (id.compareTo(versionList.getId()));
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id.intValue()).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof GeoIndicator) {
			return new EqualsBuilder().append(this.getName(), ((GeoIndicator) other).getName())
					.append(this.getCod(), ((GeoIndicator) other).getCod()).isEquals();
		} else {
			return false;
		}
	}

}
