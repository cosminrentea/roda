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
public final class TranslatedTopicPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<TranslatedTopicPK> fromJsonArrayToTranslatedTopicPKs(String json) {
		return new JSONDeserializer<List<TranslatedTopicPK>>().use(null, ArrayList.class)
				.use("values", TranslatedTopicPK.class).deserialize(json);
	}

	public static TranslatedTopicPK fromJsonToTranslatedTopicPK(String json) {
		return new JSONDeserializer<TranslatedTopicPK>().use(null, TranslatedTopicPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<TranslatedTopicPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
	private Integer langId;

	@Column(name = "topic_id", columnDefinition = "int4", nullable = false)
	private Integer topicId;

	public TranslatedTopicPK(Integer langId, Integer topicId) {
		super();
		this.langId = langId;
		this.topicId = topicId;
	}

	private TranslatedTopicPK() {
		super();
	}

	public Integer getLangId() {
		return langId;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TranslatedTopicPK) {
			final TranslatedTopicPK other = (TranslatedTopicPK) obj;
			return new EqualsBuilder().append(langId, other.langId).append(topicId, other.topicId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(topicId).append(langId).toHashCode();
	}
}
