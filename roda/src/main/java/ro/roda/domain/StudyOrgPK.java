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

@Embeddable
@Configurable
public final class StudyOrgPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<StudyOrgPK> fromJsonArrayToStudyOrgPKs(String json) {
		return new JSONDeserializer<List<StudyOrgPK>>().use(null, ArrayList.class).use("values", StudyOrgPK.class)
				.deserialize(json);
	}

	public static StudyOrgPK fromJsonToStudyOrgPK(String json) {
		return new JSONDeserializer<StudyOrgPK>().use(null, StudyOrgPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyOrgPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "assoctype_id", columnDefinition = "int4", nullable = false)
	private Integer assoctypeId;

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
	private Integer orgId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	public StudyOrgPK(Integer orgId, Integer studyId, Integer assoctypeId) {
		super();
		this.orgId = orgId;
		this.studyId = studyId;
		this.assoctypeId = assoctypeId;
	}

	private StudyOrgPK() {
		super();
	}

	public Integer getAssoctypeId() {
		return assoctypeId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
