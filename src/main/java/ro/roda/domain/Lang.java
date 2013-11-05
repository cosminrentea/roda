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
import javax.persistence.TypedQuery;
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
public class Lang {

	public static long countLangs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Lang o",
				Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new Lang().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Lang> findAllLangs() {
		return entityManager().createQuery("SELECT o FROM Lang o", Lang.class)
				.getResultList();
	}

	public static Lang findLang(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Lang.class, id);
	}

	public static List<Lang> findLangEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Lang o", Lang.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Lang> fromJsonArrayToLangs(String json) {
		return new JSONDeserializer<List<Lang>>().use(null, ArrayList.class)
				.use("values", Lang.class).deserialize(json);
	}

	public static Lang fromJsonToLang(String json) {
		return new JSONDeserializer<Lang>().use(null, Lang.class).deserialize(
				json);
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
			sid.addField(
					"lang_solrsummary_t",
					new StringBuilder().append(lang.getIso639()).append(" ")
							.append(lang.getNameSelf()).append(" ")
							.append(lang.getNameRo()).append(" ")
							.append(lang.getNameEn()));
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "Lang_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Lang().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Lang> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Lang</code> (limba) in baza
	 * de date; in caz afirmativ il returneaza, altfel, metoda il introduce in
	 * baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>iso639
	 * <li>nameSelf
	 * <li>nameRo
	 * <li>nameEn
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul limbii.
	 * @param iso639
	 *            - codul limbii in ISO 639-1.
	 * @param nameSelf
	 *            - numele limbii (in limba respectiva).
	 * @param nameRo
	 *            - numele limbii (in limba romana).
	 * @param nameEn
	 *            - numele limbii (in limba engleza).
	 * @return
	 */
	public static Lang checkLang(Integer id, String iso639, String nameSelf,
			String nameRo, String nameEn) {
		Lang object;

		if (id != null) {
			object = findLang(id);

			if (object != null) {
				return object;
			}
		}

		List<Lang> queryResult;

		if (iso639 != null) {
			TypedQuery<Lang> query = entityManager()
					.createQuery(
							"SELECT o FROM Lang o WHERE lower(o.iso639) = lower(:iso639)",
							Lang.class);
			query.setParameter("iso639", iso639);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		if (nameSelf != null) {
			TypedQuery<Lang> query = entityManager()
					.createQuery(
							"SELECT o FROM Lang o WHERE lower(o.nameSelf) = lower(:nameSelf)",
							Lang.class);
			query.setParameter("nameSelf", nameSelf);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		if (nameRo != null) {
			TypedQuery<Lang> query = entityManager()
					.createQuery(
							"SELECT o FROM Lang o WHERE lower(o.nameRo) = lower(:nameRo)",
							Lang.class);
			query.setParameter("nameRo", nameRo);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		if (nameEn != null) {
			TypedQuery<Lang> query = entityManager()
					.createQuery(
							"SELECT o FROM Lang o WHERE lower(o.nameEn) = lower(:nameEn)",
							Lang.class);
			query.setParameter("nameEn", nameEn);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Lang();
		object.nameSelf = nameSelf;
		object.nameRo = nameRo;
		object.nameEn = nameEn;
		object.iso639 = iso639;
		object.persist();

		return object;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "langId")
	private Set<InstanceDescr> instanceDescrs;

	@Column(name = "iso639", columnDefinition = "bpchar", length = 2, unique = true)
	@NotNull
	private String iso639;

	@Column(name = "name_en", columnDefinition = "varchar", length = 50)
	private String nameEn;

	@Column(name = "name_ro", columnDefinition = "varchar", length = 50)
	private String nameRo;

	@Column(name = "name_self", columnDefinition = "varchar", length = 50)
	private String nameSelf;

	@OneToMany(mappedBy = "langId")
	private Set<SeriesDescr> seriesDescrs;

	@OneToMany(mappedBy = "langId")
	private Set<StudyDescr> studyDescrs;

	@OneToMany(mappedBy = "langId")
	private Set<TranslatedTopic> translatedTopics;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
	transient SolrServer solrServer;

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstanceDescr> getInstanceDescrs() {
		return instanceDescrs;
	}

	public String getIso639() {
		return iso639;
	}

	public String getNameEn() {
		return nameEn;
	}

	public String getNameRo() {
		return nameRo;
	}

	public String getNameSelf() {
		return nameSelf;
	}

	public Set<SeriesDescr> getSeriesDescrs() {
		return seriesDescrs;
	}

	public Set<StudyDescr> getStudyDescrs() {
		return studyDescrs;
	}

	public Set<TranslatedTopic> getTranslatedTopics() {
		return translatedTopics;
	}

	@Transactional
	public Lang merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Lang merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstanceDescrs(Set<InstanceDescr> instanceDescrs) {
		this.instanceDescrs = instanceDescrs;
	}

	public void setIso639(String iso639) {
		this.iso639 = iso639;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setNameRo(String nameRo) {
		this.nameRo = nameRo;
	}

	public void setNameSelf(String nameSelf) {
		this.nameSelf = nameSelf;
	}

	public void setSeriesDescrs(Set<SeriesDescr> seriesDescrs) {
		this.seriesDescrs = seriesDescrs;
	}

	public void setStudyDescrs(Set<StudyDescr> studyDescrs) {
		this.studyDescrs = studyDescrs;
	}

	public void setTranslatedTopics(Set<TranslatedTopic> translatedTopics) {
		this.translatedTopics = translatedTopics;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Lang) obj).id))
				|| (nameSelf != null && nameSelf
						.equalsIgnoreCase(((Lang) obj).nameSelf))
				|| (nameRo != null && nameRo
						.equalsIgnoreCase(((Lang) obj).nameRo))
				|| (nameEn != null && nameEn
						.equalsIgnoreCase(((Lang) obj).nameEn))
				|| (iso639 != null && iso639
						.equalsIgnoreCase(((Lang) obj).iso639));
	}
}
