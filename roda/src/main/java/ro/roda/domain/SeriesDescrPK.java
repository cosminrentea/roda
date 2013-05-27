package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Embeddable
public final class SeriesDescrPK implements Serializable {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static SeriesDescrPK fromJsonToSeriesDescrPK(String json) {
		return new JSONDeserializer<SeriesDescrPK>().use(null, SeriesDescrPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<SeriesDescrPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<SeriesDescrPK> fromJsonArrayToSeriesDescrPKs(String json) {
		return new JSONDeserializer<List<SeriesDescrPK>>().use(null, ArrayList.class)
				.use("values", SeriesDescrPK.class).deserialize(json);
	}

	private static final long serialVersionUID = 1L;

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
}
