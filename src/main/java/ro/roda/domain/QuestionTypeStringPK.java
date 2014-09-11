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
public final class QuestionTypeStringPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<QuestionTypeStringPK> fromJsonArrayToSelectionVariableItemPKs(String json) {
		return new JSONDeserializer<List<QuestionTypeStringPK>>().use(null, ArrayList.class)
				.use("values", QuestionTypeStringPK.class).deserialize(json);
	}

	public static QuestionTypeStringPK fromJsonToSelectionVariableItemPK(String json) {
		return new JSONDeserializer<QuestionTypeStringPK>().use(null, QuestionTypeStringPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<QuestionTypeStringPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "string_id", columnDefinition = "int4", nullable = false)
	private Integer stringId;

	@Column(name = "question_id", columnDefinition = "int8", nullable = false)
	private Long questionId;

	public QuestionTypeStringPK(Long questionId, Integer stringId) {
		super();
		this.questionId = questionId;
		this.stringId = stringId;
	}

	public Integer getStringId() {
		return stringId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof QuestionTypeStringPK) {
			final QuestionTypeStringPK other = (QuestionTypeStringPK) obj;
			return new EqualsBuilder().append(questionId, other.questionId).append(stringId, other.stringId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(questionId).append(stringId).toHashCode();
	}
}
