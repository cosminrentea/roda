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

public final class StudyKeywordPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
    private Integer studyId;

	@Column(name = "keyword_id", columnDefinition = "int4", nullable = false)
    private Integer keywordId;

	@Column(name = "added_by", columnDefinition = "int4", nullable = false)
    private Integer addedBy;

	public StudyKeywordPK(Integer studyId, Integer keywordId, Integer addedBy) {
        super();
        this.studyId = studyId;
        this.keywordId = keywordId;
        this.addedBy = addedBy;
    }

	private StudyKeywordPK() {
        super();
    }

	public Integer getStudyId() {
        return studyId;
    }

	public Integer getKeywordId() {
        return keywordId;
    }

	public Integer getAddedBy() {
        return addedBy;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static StudyKeywordPK fromJsonToStudyKeywordPK(String json) {
        return new JSONDeserializer<StudyKeywordPK>().use(null, StudyKeywordPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StudyKeywordPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StudyKeywordPK> fromJsonArrayToStudyKeywordPKs(String json) {
        return new JSONDeserializer<List<StudyKeywordPK>>().use(null, ArrayList.class).use("values", StudyKeywordPK.class).deserialize(json);
    }
}
