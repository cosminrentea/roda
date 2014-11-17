package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Embeddable
@Configurable
public final class GeoDataPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<GeoDataPK> fromJsonArrayToGeoDataPKs(String json) {
		return new JSONDeserializer<List<GeoDataPK>>().use(null, ArrayList.class).use("values", GeoDataPK.class)
				.deserialize(json);
	}

	public static GeoDataPK fromJsonToGeoDataPK(String json) {
		return new JSONDeserializer<GeoDataPK>().use(null, GeoDataPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<GeoDataPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "datatypes_id", columnDefinition = "int4", nullable = false)
	private Integer datatypesId;

	@Column(name = "geography_id", columnDefinition = "int4", nullable = false)
	private Integer geographyId;

	public GeoDataPK(Integer datatypesId, Integer geographyId) {
		super();
		this.datatypesId = datatypesId;
		this.geographyId = geographyId;
	}

	private GeoDataPK() {
		super();
	}

	public Integer getDatatypesId() {
		return datatypesId;
	}

	public Integer getGeographyId() {
		return geographyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof GeoDataPK) {
			final GeoDataPK other = (GeoDataPK) obj;
			return new EqualsBuilder().append(datatypesId, other.datatypesId).append(geographyId, other.geographyId)
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(datatypesId).append(geographyId).toHashCode();
	}
}
