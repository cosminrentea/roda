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

public final class TranslatedTopicPK implements Serializable {

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static TranslatedTopicPK fromJsonToTranslatedTopicPK(String json) {
        return new JSONDeserializer<TranslatedTopicPK>().use(null, TranslatedTopicPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<TranslatedTopicPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<TranslatedTopicPK> fromJsonArrayToTranslatedTopicPKs(String json) {
        return new JSONDeserializer<List<TranslatedTopicPK>>().use(null, ArrayList.class).use("values", TranslatedTopicPK.class).deserialize(json);
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

	private static final long serialVersionUID = 1L;
}
