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
public final class QuestionTypeCategoryPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<QuestionTypeCategoryPK> fromJsonArrayToSelectionVariableItemPKs(String json) {
		return new JSONDeserializer<List<QuestionTypeCategoryPK>>().use(null, ArrayList.class)
				.use("values", QuestionTypeCategoryPK.class).deserialize(json);
	}

	public static QuestionTypeCategoryPK fromJsonToSelectionVariableItemPK(String json) {
		return new JSONDeserializer<QuestionTypeCategoryPK>().use(null, QuestionTypeCategoryPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<QuestionTypeCategoryPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "category_id", columnDefinition = "int8", nullable = false)
	private Long categoryId;

	@Column(name = "question_id", columnDefinition = "int8", nullable = false)
	private Long questionId;

	public QuestionTypeCategoryPK(Long questionId, Long categoryId) {
		super();
		this.questionId = questionId;
		this.categoryId = categoryId;
	}

	private QuestionTypeCategoryPK() {
		super();
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof QuestionTypeCategoryPK) {
			final QuestionTypeCategoryPK other = (QuestionTypeCategoryPK) obj;
			return new EqualsBuilder().append(questionId, other.questionId).append(categoryId, other.categoryId)
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(questionId).append(categoryId).toHashCode();
	}
}
