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
public final class QuestionTypeCodePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<QuestionTypeCodePK> fromJsonArrayToSelectionVariableItemPKs(String json) {
		return new JSONDeserializer<List<QuestionTypeCodePK>>().use(null, ArrayList.class)
				.use("values", QuestionTypeCodePK.class).deserialize(json);
	}

	public static QuestionTypeCodePK fromJsonToSelectionVariableItemPK(String json) {
		return new JSONDeserializer<QuestionTypeCodePK>().use(null, QuestionTypeCodePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<QuestionTypeCodePK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "code_id", columnDefinition = "int8", nullable = false)
	private Long codeId;

	@Column(name = "question_id", columnDefinition = "int8", nullable = false)
	private Long questionId;

	public QuestionTypeCodePK(Long questionId, Long codeId) {
		super();
		this.questionId = questionId;
		this.codeId = codeId;
	}

	private QuestionTypeCodePK() {
		super();
	}

	public Long getCodeId() {
		return codeId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof QuestionTypeCodePK) {
			final QuestionTypeCodePK other = (QuestionTypeCodePK) obj;
			return new EqualsBuilder().append(questionId, other.questionId).append(codeId, other.codeId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(questionId).append(codeId).toHashCode();
	}
}
