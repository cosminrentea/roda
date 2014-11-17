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
public final class GeoDrawPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<GeoDrawPK> fromJsonArrayToGeoDrawPKs(String json) {
		return new JSONDeserializer<List<GeoDrawPK>>().use(null, ArrayList.class).use("values", GeoDrawPK.class)
				.deserialize(json);
	}

	public static GeoDrawPK fromJsonToGeoDrawPK(String json) {
		return new JSONDeserializer<GeoDrawPK>().use(null, GeoDrawPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<GeoDrawPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "geography_id", columnDefinition = "int4", nullable = false)
	private Integer geographyId;

	@Column(name = "geomargins_id", columnDefinition = "int4", nullable = false)
	private Integer geomarginsId;

	public GeoDrawPK(Integer geographyId, Integer geomarginsId) {
		super();
		this.geomarginsId = geomarginsId;
		this.geographyId = geographyId;
	}

	private GeoDrawPK() {
		super();
	}

	public Integer getGeomarginsId() {
		return geomarginsId;
	}

	public Integer getGeographyId() {
		return geographyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof GeoDrawPK) {
			final GeoDrawPK other = (GeoDrawPK) obj;
			return new EqualsBuilder().append(geomarginsId, other.geomarginsId).append(geographyId, other.geographyId)
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(geomarginsId).append(geographyId).toHashCode();
	}
}
