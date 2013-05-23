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

public final class StudyOrgPK implements Serializable {

	@Column(name = "org_id", columnDefinition = "int4", nullable = false)
    private Integer orgId;

	@Column(name = "study_id", columnDefinition = "int4", nullable = false)
    private Integer studyId;

	@Column(name = "assoctype_id", columnDefinition = "int4", nullable = false)
    private Integer assoctypeId;

	public StudyOrgPK(Integer orgId, Integer studyId, Integer assoctypeId) {
        super();
        this.orgId = orgId;
        this.studyId = studyId;
        this.assoctypeId = assoctypeId;
    }

	private StudyOrgPK() {
        super();
    }

	public Integer getOrgId() {
        return orgId;
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

	public static StudyOrgPK fromJsonToStudyOrgPK(String json) {
        return new JSONDeserializer<StudyOrgPK>().use(null, StudyOrgPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StudyOrgPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StudyOrgPK> fromJsonArrayToStudyOrgPKs(String json) {
        return new JSONDeserializer<List<StudyOrgPK>>().use(null, ArrayList.class).use("values", StudyOrgPK.class).deserialize(json);
    }

	private static final long serialVersionUID = 1L;
}
