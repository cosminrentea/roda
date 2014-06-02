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
public final class CmsPageLangPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<CmsPageLangPK> fromJsonArrayToCmsPageLangPKs(String json) {
		return new JSONDeserializer<List<CmsPageLangPK>>().use(null, ArrayList.class)
				.use("values", CmsPageLangPK.class).deserialize(json);
	}

	public static CmsPageLangPK fromJsonToCmsPageLangPK(String json) {
		return new JSONDeserializer<CmsPageLangPK>().use(null, CmsPageLangPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CmsPageLangPK> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Column(name = "lang_id", columnDefinition = "int4", nullable = false)
	private Integer langId;

	@Column(name = "cms_page_id", columnDefinition = "int4", nullable = false)
	private Integer cmsPageId;

	public CmsPageLangPK(Integer langId, Integer cmsPageId) {
		super();
		this.langId = langId;
		this.cmsPageId = cmsPageId;
	}

	private CmsPageLangPK() {
		super();
	}

	public Integer getLangId() {
		return langId;
	}

	public Integer getCmsPageId() {
		return cmsPageId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof CmsPageLangPK) {
			final CmsPageLangPK other = (CmsPageLangPK) obj;
			return new EqualsBuilder().append(langId, other.langId).append(cmsPageId, other.cmsPageId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(langId).append(cmsPageId).toHashCode();
	}
}
