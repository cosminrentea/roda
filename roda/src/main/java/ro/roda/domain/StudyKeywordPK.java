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
public final class StudyKeywordPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<StudyKeywordPK> fromJsonArrayToStudyKeywordPKs(String json) {
		return new JSONDeserializer<List<StudyKeywordPK>>().use(null, ArrayList.class)
				.use("values", StudyKeywordPK.class).deserialize(json);
	}

	public static StudyKeywordPK fromJsonToStudyKeywordPK(String json) {
		return new JSONDeserializer<StudyKeywordPK>().use(null, StudyKeywordPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyKeywordPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "added_by", columnDefinition = "int4", nullable = false)
	private Integer addedBy;

	@Column(name = "keyword_id", columnDefinition = "int4", nullable = false)
	private Integer keywordId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	public StudyKeywordPK(Integer studyId, Integer keywordId, Integer addedBy) {
		super();
		this.studyId = studyId;
		this.keywordId = keywordId;
		this.addedBy = addedBy;
	}

	private StudyKeywordPK() {
		super();
	}

	public Integer getAddedBy() {
		return addedBy;
	}

	public Integer getKeywordId() {
		return keywordId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof StudyKeywordPK) {
			final StudyKeywordPK other = (StudyKeywordPK) obj;
			return new EqualsBuilder().append(studyId, other.studyId).append(keywordId, other.keywordId)
					.append(addedBy, other.addedBy).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(studyId).append(keywordId).append(addedBy).toHashCode();
	}
}
