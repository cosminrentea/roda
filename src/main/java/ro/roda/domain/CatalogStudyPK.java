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
public final class CatalogStudyPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<CatalogStudyPK> fromJsonArrayToCatalogStudyPKs(String json) {
		return new JSONDeserializer<List<CatalogStudyPK>>().use(null, ArrayList.class)
				.use("values", CatalogStudyPK.class).deserialize(json);
	}

	public static CatalogStudyPK fromJsonToCatalogStudyPK(String json) {
		return new JSONDeserializer<CatalogStudyPK>().use(null, CatalogStudyPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CatalogStudyPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "catalog_id", columnDefinition = "int4", nullable = false)
	private Integer catalogId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	public CatalogStudyPK(Integer catalogId, Integer studyId) {
		super();
		this.catalogId = catalogId;
		this.studyId = studyId;
	}

	private CatalogStudyPK() {
		super();
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CatalogStudyPK) {
			final CatalogStudyPK other = (CatalogStudyPK) obj;
			return new EqualsBuilder().append(catalogId, other.catalogId).append(studyId, other.studyId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(catalogId).append(studyId).toHashCode();
	}
}
