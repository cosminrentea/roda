package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

@Configurable
@Embeddable
public final class StudyPersonPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "person_id", columnDefinition = "int4", nullable = false)
	private Integer personId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	@Column(name = "assoctype_id", columnDefinition = "int4", nullable = false)
	private Integer assoctypeId;

	public StudyPersonPK(Integer personId, Integer studyId, Integer assoctypeId) {
		super();
		this.personId = personId;
		this.studyId = studyId;
		this.assoctypeId = assoctypeId;
	}

	private StudyPersonPK() {
		super();
	}

	public Integer getPersonId() {
		return personId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public Integer getAssoctypeId() {
		return assoctypeId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static StudyPersonPK fromJsonToStudyPersonPK(String json) {
		return new JSONDeserializer<StudyPersonPK>().use(null, StudyPersonPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyPersonPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<StudyPersonPK> fromJsonArrayToStudyPersonPKs(String json) {
		return new JSONDeserializer<List<StudyPersonPK>>().use(null, ArrayList.class)
				.use("values", StudyPersonPK.class).deserialize(json);
	}
}
