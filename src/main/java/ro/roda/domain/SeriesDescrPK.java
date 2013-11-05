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

@Configurable
@Embeddable
public final class SeriesDescrPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<SeriesDescrPK> fromJsonArrayToSeriesDescrPKs(String json) {
		return new JSONDeserializer<List<SeriesDescrPK>>().use(null, ArrayList.class)
				.use("values", SeriesDescrPK.class).deserialize(json);
	}

	public static SeriesDescrPK fromJsonToSeriesDescrPK(String json) {
		return new JSONDeserializer<SeriesDescrPK>().use(null, SeriesDescrPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<SeriesDescrPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "catalog_id", columnDefinition = "int4", nullable = false)
	private Integer catalogId;

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
	private Integer langId;

	public SeriesDescrPK(Integer catalogId, Integer langId) {
		super();
		this.catalogId = catalogId;
		this.langId = langId;
	}

	private SeriesDescrPK() {
		super();
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public Integer getLangId() {
		return langId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof SeriesDescrPK) {
			final SeriesDescrPK other = (SeriesDescrPK) obj;
			return new EqualsBuilder().append(catalogId, other.catalogId).append(langId, other.langId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(catalogId).append(langId).toHashCode();
	}
}
