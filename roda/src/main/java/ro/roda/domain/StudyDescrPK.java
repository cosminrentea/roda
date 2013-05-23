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

@Embeddable
@Configurable

public final class StudyDescrPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
    private Integer langId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
    private Integer studyId;

	public StudyDescrPK(Integer langId, Integer studyId) {
        super();
        this.langId = langId;
        this.studyId = studyId;
    }

	private StudyDescrPK() {
        super();
    }

	public Integer getLangId() {
        return langId;
    }

	public Integer getStudyId() {
        return studyId;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static StudyDescrPK fromJsonToStudyDescrPK(String json) {
        return new JSONDeserializer<StudyDescrPK>().use(null, StudyDescrPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StudyDescrPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StudyDescrPK> fromJsonArrayToStudyDescrPKs(String json) {
        return new JSONDeserializer<List<StudyDescrPK>>().use(null, ArrayList.class).use("values", StudyDescrPK.class).deserialize(json);
    }
}
