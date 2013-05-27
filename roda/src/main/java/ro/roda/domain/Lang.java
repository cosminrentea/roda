package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "lang")
@Configurable
@Audited
public class Lang {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Lang fromJsonToLang(String json) {
		return new JSONDeserializer<Lang>().use(null, Lang.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Lang> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Lang> fromJsonArrayToLangs(String json) {
		return new JSONDeserializer<List<Lang>>().use(null, ArrayList.class).use("values", Lang.class)
				.deserialize(json);
	}

	@OneToMany(mappedBy = "langId")
	private Set<InstanceDescr> instanceDescrs;

	@OneToMany(mappedBy = "langId")
	private Set<SeriesDescr> seriesDescrs;

	@OneToMany(mappedBy = "langId")
	private Set<StudyDescr> studyDescrs;

	@OneToMany(mappedBy = "langId")
	private Set<TranslatedTopic> translatedTopics;

	@Column(name = "iso639", columnDefinition = "bpchar", length = 2, unique = true)
	@NotNull
	private String iso639;

	@Column(name = "name_self", columnDefinition = "varchar", length = 50)
	private String nameSelf;

	@Column(name = "name_ro", columnDefinition = "varchar", length = 50)
	private String nameRo;

	@Column(name = "name_en", columnDefinition = "varchar", length = 50)
	private String nameEn;

	public Set<InstanceDescr> getInstanceDescrs() {
		return instanceDescrs;
	}

	public void setInstanceDescrs(Set<InstanceDescr> instanceDescrs) {
		this.instanceDescrs = instanceDescrs;
	}

	public Set<SeriesDescr> getSeriesDescrs() {
		return seriesDescrs;
	}

	public void setSeriesDescrs(Set<SeriesDescr> seriesDescrs) {
		this.seriesDescrs = seriesDescrs;
	}

	public Set<StudyDescr> getStudyDescrs() {
		return studyDescrs;
	}

	public void setStudyDescrs(Set<StudyDescr> studyDescrs) {
		this.studyDescrs = studyDescrs;
	}

	public Set<TranslatedTopic> getTranslatedTopics() {
		return translatedTopics;
	}

	public void setTranslatedTopics(Set<TranslatedTopic> translatedTopics) {
		this.translatedTopics = translatedTopics;
	}

	public String getIso639() {
		return iso639;
	}

	public void setIso639(String iso639) {
		this.iso639 = iso639;
	}

	public String getNameSelf() {
		return nameSelf;
	}

	public void setNameSelf(String nameSelf) {
		this.nameSelf = nameSelf;
	}

	public String getNameRo() {
		return nameRo;
	}

	public void setNameRo(String nameRo) {
		this.nameRo = nameRo;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Lang().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countLangs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Lang o", Long.class).getSingleResult();
	}

	public static List<Lang> findAllLangs() {
		return entityManager().createQuery("SELECT o FROM Lang o", Lang.class).getResultList();
	}

	public static Lang findLang(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Lang.class, id);
	}

	public static List<Lang> findLangEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Lang o", Lang.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Lang attached = Lang.findLang(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public Lang merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Lang merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Lang_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static void indexLang(Lang lang) {
		List<Lang> langs = new ArrayList<Lang>();
		langs.add(lang);
		indexLangs(langs);
	}

	@Async
	public static void indexLangs(Collection<Lang> langs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Lang lang : langs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "lang_" + lang.getId());
			sid.addField("lang.iso639_s", lang.getIso639());
			sid.addField("lang.nameself_s", lang.getNameSelf());
			sid.addField("lang.namero_s", lang.getNameRo());
			sid.addField("lang.nameen_s", lang.getNameEn());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("lang_solrsummary_t",
					new StringBuilder().append(lang.getIso639()).append(" ").append(lang.getNameSelf()).append(" ")
							.append(lang.getNameRo()).append(" ").append(lang.getNameEn()));
			documents.add(sid);
		}
		try {
			SolrServer solrServer = solrServer();
			solrServer.add(documents);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public static void deleteIndex(Lang lang) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("lang_" + lang.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexLang(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Lang().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
