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
public final class StudyPersonPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<StudyPersonPK> fromJsonArrayToStudyPersonPKs(String json) {
		return new JSONDeserializer<List<StudyPersonPK>>().use(null, ArrayList.class)
				.use("values", StudyPersonPK.class).deserialize(json);
	}

	public static StudyPersonPK fromJsonToStudyPersonPK(String json) {
		return new JSONDeserializer<StudyPersonPK>().use(null, StudyPersonPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyPersonPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "assoctype_id", columnDefinition = "int4", nullable = false)
	private Integer assoctypeId;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	public StudyPersonPK(Integer personId, Integer studyId, Integer assoctypeId) {
		super();
		this.personId = personId;
		this.studyId = studyId;
		this.assoctypeId = assoctypeId;
	}

	private StudyPersonPK() {
		super();
	}

	public Integer getAssoctypeId() {
		return assoctypeId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof StudyPersonPK) {
			final StudyPersonPK other = (StudyPersonPK) obj;
			return new EqualsBuilder().append(studyId, other.studyId).append(personId, other.personId)
					.append(assoctypeId, other.assoctypeId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(studyId).append(personId).append(assoctypeId).toHashCode();
	}
}
