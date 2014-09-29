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

@Embeddable
@Configurable
public final class TranslatedQuestionPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<TranslatedQuestionPK> fromJsonArrayToTranslatedQuestionPKs(String json) {
		return new JSONDeserializer<List<TranslatedQuestionPK>>().use(null, ArrayList.class)
				.use("values", TranslatedQuestionPK.class).deserialize(json);
	}

	public static TranslatedQuestionPK fromJsonToTranslatedQuestionPK(String json) {
		return new JSONDeserializer<TranslatedQuestionPK>().use(null, TranslatedQuestionPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<TranslatedQuestionPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
	private Integer langId;

	@Column(name = "question_id", columnDefinition = "int4", nullable = false)
	private Integer questionId;

	public TranslatedQuestionPK(Integer langId, Integer questionId) {
		super();
		this.langId = langId;
		this.questionId = questionId;
	}

	private TranslatedQuestionPK() {
		super();
	}

	public Integer getLangId() {
		return langId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TranslatedQuestionPK) {
			final TranslatedQuestionPK other = (TranslatedQuestionPK) obj;
			return new EqualsBuilder().append(langId, other.langId).append(questionId, other.questionId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(questionId).append(langId).toHashCode();
	}
}
