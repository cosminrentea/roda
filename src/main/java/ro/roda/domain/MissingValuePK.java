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
public final class MissingValuePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<MissingValuePK> fromJsonArrayToStudyDescrPKs(String json) {
		return new JSONDeserializer<List<MissingValuePK>>().use(null, ArrayList.class).use("values", MissingValuePK.class)
				.deserialize(json);
	}

	public static MissingValuePK fromJsonToStudyDescrPK(String json) {
		return new JSONDeserializer<MissingValuePK>().use(null, MissingValuePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<MissingValuePK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
	private Integer langId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	public MissingValuePK(Integer langId, Integer studyId) {
		super();
		this.langId = langId;
		this.studyId = studyId;
	}

	private MissingValuePK() {
		super();
	}

	public Integer getLangId() {
		return langId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MissingValuePK) {
			final MissingValuePK other = (MissingValuePK) obj;
			return new EqualsBuilder().append(langId, other.langId).append(studyId, other.studyId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(studyId).append(langId).toHashCode();
	}
}
