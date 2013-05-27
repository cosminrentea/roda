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
public final class StudyDocumentsPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static StudyDocumentsPK fromJsonToStudyDocumentsPK(String json) {
		return new JSONDeserializer<StudyDocumentsPK>().use(null, StudyDocumentsPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyDocumentsPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<StudyDocumentsPK> fromJsonArrayToStudyDocumentsPKs(String json) {
		return new JSONDeserializer<List<StudyDocumentsPK>>().use(null, ArrayList.class)
				.use("values", StudyDocumentsPK.class).deserialize(json);
	}

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
	private Integer studyId;

	@Column(name = "document_id", columnDefinition = "int4", nullable = false)
	private Integer documentId;

	public StudyDocumentsPK(Integer studyId, Integer documentId) {
		super();
		this.studyId = studyId;
		this.documentId = documentId;
	}

	private StudyDocumentsPK() {
		super();
	}

	public Integer getStudyId() {
		return studyId;
	}

	public Integer getDocumentId() {
		return documentId;
	}
}
